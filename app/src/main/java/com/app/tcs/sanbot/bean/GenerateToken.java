package com.app.tcs.sanbot.bean;

/**
 * Created by 1407053 on 9/7/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateToken {

    @SerializedName("conversationId")
    @Expose
    private String conversationId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

}