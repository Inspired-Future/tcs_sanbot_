package com.app.tcs.sanbot.bean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 1407053 on 9/8/2017.
 */

public class LuisSpeechResponse {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("intents")
    @Expose
    private List<LuisSpeechIntentResponse> intents = null;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<LuisSpeechIntentResponse> getIntents() {
        return intents;
    }

    public void setIntents(List<LuisSpeechIntentResponse> intents) {
        this.intents = intents;
    }



}
