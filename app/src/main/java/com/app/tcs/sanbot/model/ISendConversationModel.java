package com.app.tcs.sanbot.model;


import com.app.tcs.sanbot.bean.SendConversationResponse;

public interface ISendConversationModel {
    void sendConversationDetails(String conversation, String type, String text, String userid, String token, boolean flag,
                                 Callback callback);

    interface Callback {
        void onSuccess(SendConversationResponse sendConversationResponse);
        void onError(int code, String error);
    }
}
