package com.app.tcs.sanbot.model;

import android.content.Context;


import com.app.tcs.sanbot.bean.GenerateToken;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.app.tcs.sanbot.restfull.NoConnectivityException;

import retrofit2.Call;
import retrofit2.Response;

public class GetBotTokenModelImpl implements IGetBotTokenModel {
    private Context context;
    private ApiInterface apiService;

    public GetBotTokenModelImpl(Context context, ApiInterface apiService) {
        this.context = context;
        this.apiService = apiService;
    }

    @Override
    public void getBotTokenDetails(String authorization, final Callback callback) {
        Call<GenerateToken> call = apiService.getBotToken(authorization);
        call.enqueue(new retrofit2.Callback<GenerateToken>() {
            @Override
            public void onResponse(Call<GenerateToken> call, Response<GenerateToken> response) {
                if (response.body() != null) {
                    GenerateToken generateTokenResponse = response.body();
                    callback.onSuccess(generateTokenResponse);
                } else {
                    callback.onError(500, "Server Error");
                }
            }

            @Override
            public void onFailure(Call<GenerateToken> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    callback.onError(599, t.getMessage());
                } else {
                    callback.onError(598, "TimeOut Error");
                }
            }
        });
    }

}



