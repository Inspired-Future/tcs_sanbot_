package com.app.tcs.sanbot.model;

import android.content.Context;

import com.app.tcs.sanbot.bean.BotMsgResponse;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.app.tcs.sanbot.restfull.NoConnectivityException;

import retrofit2.Call;
import retrofit2.Response;

public class GetConversationModelImpl implements IGetConversationModel {
    private Context context;
    private ApiInterface apiService;

    public GetConversationModelImpl(Context context, ApiInterface apiService) {
        this.context = context;
        this.apiService = apiService;
    }

    @Override
    public void getConversationDetails(String conversation, String token,  final Callback callback) {

        Call<BotMsgResponse> call = apiService.getConversationMessage(conversation, "Bearer " +token);
        call.enqueue(new retrofit2.Callback<BotMsgResponse>() {
            @Override
            public void onResponse(Call<BotMsgResponse> call, Response<BotMsgResponse> response) {
                if (response.body() != null) {
                    BotMsgResponse sendConversationResponse = response.body();
                    callback.onSuccess(sendConversationResponse);
                } else {
                    callback.onError(500, "Server Error");
                }
            }

            @Override
            public void onFailure(Call<BotMsgResponse> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    callback.onError(599, t.getMessage());
                } else {
                    callback.onError(598, "TimeOut Error");
                }
            }
        });
    }



}



