package com.app.tcs.sanbot.presenter;

import com.app.tcs.sanbot.bean.GenerateToken;
import com.app.tcs.sanbot.model.IGetBotTokenModel;

public class GetBotTokenPresenterImpl implements IGetBotTokenPresenter {
    private IGetBotTokenModel iGetBotTokenModel;

    private View mView;

    public GetBotTokenPresenterImpl(View view, IGetBotTokenModel iGetBotTokenModel) {
        this.mView = view;
        this.iGetBotTokenModel = iGetBotTokenModel;
    }

    @Override
    public void getBotToken(String authorization) {
        mView.showProgress();
        iGetBotTokenModel.getBotTokenDetails(authorization, new IGetBotTokenModel.Callback() {
            @Override
            public void onSuccess(GenerateToken response) {
                mView.onGetBotTokenSuccessful(response);
                mView.hideProgress();
            }

            @Override
            public void onError(int code, String error) {
                mView.showError(code, error);
                mView.hideProgress();
            }
        });

    }
}
