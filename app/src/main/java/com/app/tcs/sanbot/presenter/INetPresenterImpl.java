package com.app.tcs.sanbot.presenter;


import com.app.tcs.sanbot.bean.SendConversationResponse;
import com.app.tcs.sanbot.model.IINetModel;
import com.app.tcs.sanbot.model.ISendConversationModel;
import com.bumptech.glide.load.data.StreamAssetPathFetcher;

public class INetPresenterImpl implements IINetPresenter {
    private IINetModel iiNetModel;

    private View mView;

    public INetPresenterImpl(View view, IINetModel iiNetModel) {
        this.mView = view;
        this.iiNetModel = iiNetModel;
    }


    @Override
    public void callINet(String url) {
        mView.showProgress();
        iiNetModel.callINet( url, new IINetModel.Callback() {
            @Override
            public void onSuccess() {
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
