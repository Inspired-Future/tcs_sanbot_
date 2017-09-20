
package com.app.tcs.sanbot.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BotMsgResponse {

    @SerializedName("activities")
    @Expose
    private List<BotMsgActivitiesResponse> activities = null;

    public List<BotMsgActivitiesResponse> getActivities() {
        return activities;
    }

    public void setActivities(List<BotMsgActivitiesResponse> activities) {
        this.activities = activities;
    }
}
