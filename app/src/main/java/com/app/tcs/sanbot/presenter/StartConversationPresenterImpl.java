package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.StartConversation;
import com.app.tcs.sanbot.model.IStartConversationModel;

public class StartConversationPresenterImpl implements IStartConversationPresenter {


    private IStartConversationModel iStartConversationModel;

    private View mView;

    public StartConversationPresenterImpl(View view, IStartConversationModel iStartConversationModel) {
        this.mView = view;
        this.iStartConversationModel = iStartConversationModel;
    }

    @Override
    public void getToken(String authorization) {
        mView.showProgress();
        iStartConversationModel.startConversationDetails(authorization, new IStartConversationModel.Callback() {

            @Override
            public void onSuccess(StartConversation startConversationResponse) {
                mView.onStartConversationSuccessful(startConversationResponse);
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
