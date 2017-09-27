package com.app.tcs.sanbot.model;

import android.content.Context;

import com.app.tcs.sanbot.appconstant.AppConstant;
import com.app.tcs.sanbot.bean.ConversationChannelDataRequest;
import com.app.tcs.sanbot.bean.ConversationFromRequest;
import com.app.tcs.sanbot.bean.ConversationRequest;
import com.app.tcs.sanbot.bean.SendConversationResponse;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.app.tcs.sanbot.restfull.NoConnectivityException;

import retrofit2.Call;
import retrofit2.Response;

public class INetModelImpl implements IINetModel {
    private Context context;
    private ApiInterface apiService;

    public INetModelImpl(Context context, ApiInterface apiService) {
        this.context = context;
        this.apiService = apiService;
    }



    @Override
    public void callINet(String url, final Callback callback) {
        Call<String> call = apiService.callINet(url);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    String ss = response.body().toString();
                    callback.onSuccess();
                } else {
                    callback.onError(500, "Server Error");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    callback.onError(599, t.getMessage());
                } else {
                    callback.onError(598, "TimeOut Error");
                }
            }
        });
    }
}



