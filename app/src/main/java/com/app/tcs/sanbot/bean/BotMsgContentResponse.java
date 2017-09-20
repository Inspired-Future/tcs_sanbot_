
package com.app.tcs.sanbot.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BotMsgContentResponse {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("images")
    @Expose
    private List<BotMsgImageResponse> images = null;
    @SerializedName("buttons")
    @Expose
    private List<BotMsgButtonResponse> buttons = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<BotMsgImageResponse> getImages() {
        return images;
    }

    public void setImages(List<BotMsgImageResponse> images) {
        this.images = images;
    }

    public List<BotMsgButtonResponse> getButtons() {
        return buttons;
    }

    public void setButtons(List<BotMsgButtonResponse> buttons) {
        this.buttons = buttons;
    }

}
