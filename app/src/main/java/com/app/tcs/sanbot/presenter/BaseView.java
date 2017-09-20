package com.app.tcs.sanbot.presenter;


public interface BaseView {

    void showProgress();
    void hideProgress();
    void showError(int code, String message);
}
