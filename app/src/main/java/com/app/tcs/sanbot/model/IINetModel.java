package com.app.tcs.sanbot.model;


import com.app.tcs.sanbot.bean.SendConversationResponse;

public interface IINetModel {
    void callINet( String url, Callback callback);

    interface Callback {
        void onSuccess();
        void onError(int code, String error);
    }
}
