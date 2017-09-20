package com.app.tcs.sanbot.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1407053 on 9/7/2017.
 */

public class ConversationRequest {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("from")
    @Expose
    private ConversationFromRequest from;

    @SerializedName("channelData")
    @Expose
    private ConversationChannelDataRequest channelData;


    public ConversationRequest(String type, String text, ConversationFromRequest from) {
        this.type = type;
        this.text = text;
        this.from = from;
    }

    public ConversationRequest(String type, String text, ConversationFromRequest from, ConversationChannelDataRequest channelData) {
        this.type = type;
        this.text = text;
        this.from = from;
        this.channelData = channelData;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ConversationFromRequest getFrom() {
        return from;
    }

    public void setFrom(ConversationFromRequest from) {
        this.from = from;
    }

    public ConversationChannelDataRequest getChannelData() {
        return channelData;
    }

    public void setChannelData(ConversationChannelDataRequest channelData) {
        this.channelData = channelData;
    }
}
