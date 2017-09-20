package com.app.tcs.sanbot.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 1407053 on 9/8/2017.
 */

public class BotMsgActivitiesResponse {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("from")
    @Expose
    private BotMsgFromResponse from;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("attachments")
    @Expose
    private List<BotMsgAttachmentResponse> attachments = null;
    @SerializedName("id")
    @Expose
    private String id;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public BotMsgFromResponse getFrom() {
        return from;
    }

    public void setFrom(BotMsgFromResponse from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BotMsgAttachmentResponse> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<BotMsgAttachmentResponse> attachments) {
        this.attachments = attachments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
