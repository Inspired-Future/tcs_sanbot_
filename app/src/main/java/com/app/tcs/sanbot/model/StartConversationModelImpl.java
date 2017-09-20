package com.app.tcs.sanbot.model;

import android.content.Context;

import com.app.tcs.sanbot.bean.StartConversation;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.app.tcs.sanbot.restfull.NoConnectivityException;

import retrofit2.Call;
import retrofit2.Response;

public class StartConversationModelImpl implements IStartConversationModel {
    private Context context;
    private ApiInterface apiService;

    public StartConversationModelImpl(Context context, ApiInterface apiService) {
        this.context = context;
        this.apiService = apiService;
    }

    @Override
    public void startConversationDetails(String token, final Callback callback) {
        Call<StartConversation> call = apiService.startConversation(token);
        call.enqueue(new retrofit2.Callback<StartConversation>() {
            @Override
            public void onResponse(Call<StartConversation> call, Response<StartConversation> response) {
                if (response.body() != null) {
                    StartConversation startConversation = response.body();
                    callback.onSuccess(startConversation);
                } else {
                    callback.onError(500, "Server Error");
                }
            }

            @Override
            public void onFailure(Call<StartConversation> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    callback.onError(599, t.getMessage());
                } else {
                    callback.onError(598, "TimeOut Error");
                }
            }
        });
    }


}



