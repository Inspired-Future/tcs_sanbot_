package com.app.tcs.sanbot.model;


import com.app.tcs.sanbot.bean.StartConversation;

public interface IStartConversationModel {
    void startConversationDetails(String token, Callback callback);

    interface Callback {
        void onSuccess(StartConversation startConversationResponse);
        void onError(int code, String error);
    }
}
