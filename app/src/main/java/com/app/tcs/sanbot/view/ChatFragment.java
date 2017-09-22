package com.app.tcs.sanbot.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.app.tcs.sanbot.R;
import com.app.tcs.sanbot.adapter.ChatListAdapter;
import com.app.tcs.sanbot.bean.BotMsgActivitiesResponse;
import com.app.tcs.sanbot.bean.BotMsgResponse;
import com.app.tcs.sanbot.bean.SendConversationResponse;
import com.app.tcs.sanbot.model.GetConversationModelImpl;
import com.app.tcs.sanbot.model.SendConversationModelImpl;
import com.app.tcs.sanbot.presenter.GetConversationPresenterImpl;
import com.app.tcs.sanbot.presenter.IGetConversationPresenter;
import com.app.tcs.sanbot.presenter.ISendConversationPresenter;
import com.app.tcs.sanbot.presenter.SendConversationPresenterImpl;
import com.app.tcs.sanbot.restfull.ApiClient;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ChatFragment extends Fragment implements ISendConversationPresenter.View,
        IGetConversationPresenter.View , ChatListAdapter.EventTriggerInterface {

    @BindView(R.id.rv_chat_list)
    RecyclerView rvChatList;
    @BindView(R.id.et_message)
    EditText etMessage;

    private String conversationId;
    private String conversationToken;

    private Unbinder mUnbinder;
    public SendConversationPresenterImpl sendConversationPresenter;
    public GetConversationPresenterImpl getConversationPresenter;
    public ApiInterface apiService;
    public ChatListAdapter chatListAdapter;
    List<BotMsgActivitiesResponse> botMsgActivitiesResponseList;
    Handler customHandler, barCodeScanHandler;
    int arraySize = 0;
    LinearLayoutManager llm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        if (apiService == null) {
            apiService = ApiClient.getClient(getActivity()).create(ApiInterface.class);
        }


        botMsgActivitiesResponseList = new ArrayList<BotMsgActivitiesResponse>();
        llm = new LinearLayoutManager(getActivity());
        llm.setStackFromEnd(true);
        rvChatList.setHasFixedSize(true);
        rvChatList.setLayoutManager(llm);
        rvChatList.setItemAnimator(new DefaultItemAnimator());
        conversationId = getArguments().getString("id");
        conversationToken = getArguments().getString("token");
        sendConversationPresenter = new SendConversationPresenterImpl(
                this,
                new SendConversationModelImpl(getActivity(), apiService)
        );
        getConversationPresenter = new GetConversationPresenterImpl(
                this,
                new GetConversationModelImpl(getActivity(), apiService)
        );

        chatListAdapter = new ChatListAdapter(getActivity(), botMsgActivitiesResponseList, ChatFragment.this);
        rvChatList.setAdapter(chatListAdapter);
        customHandler = new Handler();
        customHandler.postDelayed(updateListThread, 0);
        return v;
    }


    @OnClick(R.id.btn_send)
    public void sendConversation(View view) {
        if (etMessage.getText().toString().trim().length() > 0) {
            sendConversationPresenter.sendConversation(conversationId,
                    "message", etMessage.getText().toString().trim(),
                    "user1", conversationToken, false);
        }
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(int code, String message) {
    }

    @Override
    public void onSendConversationSuccessful(SendConversationResponse sendConversationResponse) {
        getConversationPresenter.getConversationMsg(conversationId, conversationToken);
    }

    @Override
    public void onGetConversationSuccessful(BotMsgResponse botMsgResponse) {
        if (botMsgResponse.getActivities().size() > 0 & arraySize != botMsgResponse.getActivities().size()) {

            botMsgActivitiesResponseList.clear();
            botMsgActivitiesResponseList = botMsgResponse.getActivities();
            chatListAdapter.updateRecord(botMsgActivitiesResponseList);
            llm.scrollToPosition(botMsgActivitiesResponseList.size() - 1);
            arraySize = botMsgResponse.getActivities().size();
        }
    }

    @Override
    public void eventTrigger(String msg) {
        sendConversationPresenter.sendConversation(conversationId,
                "message", msg, "user1", conversationToken, true);
    }

    @Override
    public void scanQRCode() {

        barCodeScanHandler = new Handler();
        barCodeScanHandler.postDelayed(barCodeScanThread, 1000);
        //IntentIntegrator integrator = new IntentIntegrator.forSupportFragment(this);

        /*integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();*/
    }

    private Runnable updateListThread = new Runnable() {
        public void run() {
            getConversationPresenter.getConversationMsg(conversationId, conversationToken);
            customHandler.postDelayed(this, 1000);
        }
    };


    private Runnable barCodeScanThread = new Runnable() {
        public void run() {
            IntentIntegrator.forSupportFragment(ChatFragment.this)
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES).initiateScan();
        }
    };

    void sendLuisMsg(String msg) {
        sendConversationPresenter.sendConversation(conversationId,
                "message", msg,
                "user1", conversationToken, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                sendLuisMsg("Scan failed");
            } else {
                sendLuisMsg(intentResult.getContents());
            }
        }
    }
}

