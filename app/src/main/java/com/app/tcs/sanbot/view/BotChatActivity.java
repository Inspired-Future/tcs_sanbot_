package com.app.tcs.sanbot.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.app.tcs.sanbot.R;
import com.app.tcs.sanbot.appconstant.AppConstant;
import com.app.tcs.sanbot.bean.GenerateToken;
import com.app.tcs.sanbot.bean.StartConversation;
import com.app.tcs.sanbot.model.GetBotTokenModelImpl;
import com.app.tcs.sanbot.model.StartConversationModelImpl;
import com.app.tcs.sanbot.presenter.GetBotTokenPresenterImpl;
import com.app.tcs.sanbot.presenter.IGetBotTokenPresenter;
import com.app.tcs.sanbot.presenter.IStartConversationPresenter;
import com.app.tcs.sanbot.presenter.StartConversationPresenterImpl;
import com.app.tcs.sanbot.restfull.ApiClient;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.app.tcs.sanbot.service.BotSocketService;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class BotChatActivity extends BaseLuisActivity implements IGetBotTokenPresenter.View, IStartConversationPresenter.View {
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;

    boolean chatFlag = false;

    public ApiInterface apiService;
    public GetBotTokenPresenterImpl getBotTokenPresenter;
    public ChatFragment chatFragment;

    private Socket mSocket;
    public static final String TAG = BotSocketService.class.getSimpleName();
    public StartConversationPresenterImpl startConversationPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);


        if (apiService == null) {
            apiService = ApiClient.getClient(
                    BotChatActivity.this).create(ApiInterface.class);
        }
        getBotTokenPresenter = new GetBotTokenPresenterImpl(
                this,
                new GetBotTokenModelImpl(this, apiService)
        );
        getBotTokenPresenter.getBotToken(AppConstant.BOT_SECRET_KEY);


    }

    @Override
    protected void checkFaceDeduction() {

    }

    @Override
    protected void onSendLuisMsg(String msg) {
        chatFragment.sendLuisMsg(msg);
    }



    @Override
    public void showProgress() {
        pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoader.setVisibility(View.GONE);
    }

    @Override
    public void showError(int code, String message) {

    }

    @Override
    public void onGetBotTokenSuccessful(GenerateToken generateTokenResponse) {
        startConversationPresenter = new StartConversationPresenterImpl(
                this,
                new StartConversationModelImpl(this, apiService)
        );
        startConversationPresenter.getToken("Bearer " + generateTokenResponse.getToken());
    }

    @Override
    public void onStartConversationSuccessful(StartConversation startConversation) {


        /*Intent intent = new Intent(this, BotSocketService.class);
        intent.putExtra("Bot_WSS",startConversation.getStreamUrl().replace("wss://","https://"));
        startService(intent);*/

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", startConversation.getConversationId());
        bundle.putString("token", startConversation.getToken());
        chatFragment.setArguments(bundle);
        ft.add(R.id.fl_chatlist, chatFragment);
        ft.commit();
    }




}
