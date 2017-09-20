package com.app.tcs.sanbot.model;


import com.app.tcs.sanbot.bean.BotMsgResponse;

public interface IGetConversationModel {
    void getConversationDetails(String conversation, String token, Callback callback);

    interface Callback {
        void onSuccess(BotMsgResponse botMsgResponse);
        void onError(int code, String error);
    }
}
