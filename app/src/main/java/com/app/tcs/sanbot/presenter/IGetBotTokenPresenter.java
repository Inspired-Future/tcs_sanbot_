package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.GenerateToken;

public interface IGetBotTokenPresenter {

    interface View extends BaseView {
        void onGetBotTokenSuccessful(GenerateToken generateTokenResponse);
    }

    void getBotToken(String authorization);
}
