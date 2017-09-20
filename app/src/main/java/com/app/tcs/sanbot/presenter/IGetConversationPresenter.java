package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.BotMsgResponse;

public interface IGetConversationPresenter {

    interface View extends BaseView {
        void onGetConversationSuccessful(BotMsgResponse botMsgResponse);
    }

    void getConversationMsg(String conversationId, String token);
}
