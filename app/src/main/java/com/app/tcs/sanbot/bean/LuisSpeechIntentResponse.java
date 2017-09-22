package com.app.tcs.sanbot.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 1407053 on 9/8/2017.
 */

public class LuisSpeechIntentResponse {

    @SerializedName("intent")
    @Expose
    private String intent;
    @SerializedName("score")
    @Expose
    private Double score;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
