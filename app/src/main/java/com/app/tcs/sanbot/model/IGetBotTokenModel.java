package com.app.tcs.sanbot.model;


import com.app.tcs.sanbot.bean.GenerateToken;

public interface IGetBotTokenModel {
    void getBotTokenDetails(String authorization, Callback callback);

    interface Callback {
        void onSuccess(GenerateToken generateTokenResponse);
        void onError(int code, String error);
    }
}
