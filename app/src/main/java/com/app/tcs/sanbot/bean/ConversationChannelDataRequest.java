package com.app.tcs.sanbot.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1407053 on 9/7/2017.
 */

public class ConversationChannelDataRequest {

    @SerializedName("clientActivityId")
    @Expose
    private String clientActivityId;


    public ConversationChannelDataRequest(String clientActivityId) {
        this.clientActivityId = clientActivityId;
    }

    public String getClientActivityId() {
        return clientActivityId;
    }

    public void setClientActivityId(String clientActivityId) {
        this.clientActivityId = clientActivityId;
    }
}
