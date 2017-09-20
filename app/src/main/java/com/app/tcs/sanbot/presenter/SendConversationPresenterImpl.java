package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.SendConversationResponse;
import com.app.tcs.sanbot.model.ISendConversationModel;

public class SendConversationPresenterImpl implements ISendConversationPresenter {
    private ISendConversationModel iSendConversationModel;

    private View mView;

    public SendConversationPresenterImpl(View view, ISendConversationModel iSendConversationModel) {
        this.mView = view;
        this.iSendConversationModel = iSendConversationModel;
    }


    @Override
    public void sendConversation(String conversation, String type, String text, String userid, String token, boolean flag) {
        mView.showProgress();
        iSendConversationModel.sendConversationDetails(conversation,type,text,userid,token,flag, new ISendConversationModel.Callback() {
            @Override
            public void onSuccess(SendConversationResponse sendConversationResponse) {
                mView.onSendConversationSuccessful(sendConversationResponse);
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
