package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.SendConversationResponse;

public interface ISendConversationPresenter {

    interface View extends BaseView {
        void onSendConversationSuccessful(SendConversationResponse sendConversationResponse);
    }

    void sendConversation(String conversation, String type, String text, String userid, String token, boolean flag);
}
