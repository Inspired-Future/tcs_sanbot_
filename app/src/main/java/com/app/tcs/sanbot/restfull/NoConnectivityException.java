package com.app.tcs.sanbot.restfull;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    private int code;

    public int getCode() {
        return 1000;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }

}