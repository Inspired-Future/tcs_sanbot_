
package com.app.tcs.sanbot.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BotMsgAttachmentResponse {

    @SerializedName("contentType")
    @Expose
    private String contentType;
    @SerializedName("content")
    @Expose
    private BotMsgContentResponse content;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public BotMsgContentResponse getContent() {
        return content;
    }

    public void setContent(BotMsgContentResponse content) {
        this.content = content;
    }

}
