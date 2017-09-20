package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.StartConversation;

public interface IStartConversationPresenter {

    interface View extends BaseView {
        void onStartConversationSuccessful(StartConversation startConversation);
    }

    void getToken(String sercetKey);
}
