package com.app.tcs.sanbot.model;

import android.content.Context;

import com.app.tcs.sanbot.bean.ConversationChannelDataRequest;
import com.app.tcs.sanbot.bean.ConversationFromRequest;
import com.app.tcs.sanbot.bean.ConversationRequest;
import com.app.tcs.sanbot.bean.SendConversationResponse;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.app.tcs.sanbot.restfull.NoConnectivityException;

import retrofit2.Call;
import retrofit2.Response;

public class SendConversationModelImpl implements ISendConversationModel {
    private Context context;
    private ApiInterface apiService;

    public SendConversationModelImpl(Context context, ApiInterface apiService) {
        this.context = context;
        this.apiService = apiService;
    }

    @Override
    public void sendConversationDetails(String conversation, String type, String text, String userid,
                                        String token, boolean flag,
                                        final Callback callback) {
        ConversationFromRequest conversationFromRequest = new ConversationFromRequest(
                userid);
        ConversationRequest conversationRequest = null;
        if(flag){

            String[] msgTxt = text.split("\\$");
            ConversationChannelDataRequest conversationChannelDataRequest = new ConversationChannelDataRequest(
                    msgTxt[0]);
            conversationRequest = new ConversationRequest(
                    type,
                    msgTxt[1],
                    conversationFromRequest,conversationChannelDataRequest);
        }else {
            conversationRequest = new ConversationRequest(
                    type,
                    text,
                    conversationFromRequest);
        }
        Call<SendConversationResponse> call = apiService.sendMessage(conversation, "Bearer "+token,
                conversationRequest);
        call.enqueue(new retrofit2.Callback<SendConversationResponse>() {
            @Override
            public void onResponse(Call<SendConversationResponse> call, Response<SendConversationResponse> response) {
                if (response.body() != null) {
                    SendConversationResponse sendConversationResponse = response.body();
                    callback.onSuccess(sendConversationResponse);
                } else {
                    callback.onError(500, "Server Error");
                }
            }

            @Override
            public void onFailure(Call<SendConversationResponse> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    callback.onError(599, t.getMessage());
                } else {
                    callback.onError(598, "TimeOut Error");
                }
            }
        });
    }



}



