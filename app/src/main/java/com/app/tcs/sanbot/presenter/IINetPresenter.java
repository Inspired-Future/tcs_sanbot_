package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.StartConversation;

public interface IINetPresenter {

    interface View extends BaseView {
        void onCallINetSuccessful();
    }

    void callINet(String url);
}
