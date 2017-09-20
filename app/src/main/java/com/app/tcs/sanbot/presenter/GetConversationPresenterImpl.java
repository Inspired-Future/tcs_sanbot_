package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.BotMsgResponse;
import com.app.tcs.sanbot.model.IGetConversationModel;

public class GetConversationPresenterImpl implements IGetConversationPresenter {
    private IGetConversationModel iGetConversationModel;

    private View mView;

    public GetConversationPresenterImpl(View view, IGetConversationModel iGetConversationModel) {
        this.mView = view;
        this.iGetConversationModel = iGetConversationModel;
    }


    @Override
    public void getConversationMsg(String conversationId, String token) {
        mView.showProgress();
        iGetConversationModel.getConversationDetails(conversationId, token, new IGetConversationModel.Callback() {
            @Override
            public void onSuccess(BotMsgResponse botMsgResponse) {
                mView.onGetConversationSuccessful(botMsgResponse);
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
