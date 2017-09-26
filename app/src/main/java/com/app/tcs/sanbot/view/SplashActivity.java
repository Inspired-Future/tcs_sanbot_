package com.app.tcs.sanbot.view;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.tcs.sanbot.R;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.FaceRecognizeBean;
import com.qihancloud.opensdk.function.unit.MediaManager;
import com.qihancloud.opensdk.function.unit.SpeechManager;
import com.qihancloud.opensdk.function.unit.interfaces.media.FaceRecognizeListener;
import com.qihancloud.opensdk.function.unit.interfaces.speech.SpeakListener;


import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class SplashActivity extends BaseLuisActivity {

    @BindView(R.id.rl_loader)
    RelativeLayout rlLoaderView;

    private MediaManager mediaManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        rlLoaderView.setVisibility(View.GONE);
    }


    @Override
    protected void onMainServiceConnected() {
    }

    @Override
    protected void checkFaceDeduction() {

        //tts.speak("Hello Good afternoon", TextToSpeech.QUEUE_FLUSH, null);
        doFaceRecognition();
    }

    @Override
    protected void onSendLuisMsg(String msg) {

    }

    @Override
    protected void onSendPartialLuisMsg(String msg) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("TAG_ROBOT","onNewIntent");
    }

    private void doFaceRecognition() {
       /* rlLoaderView.setVisibility(View.VISIBLE);
        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);
        initListener();*/
        Intent intent = new Intent(SplashActivity.this, BotChatActivity.class);
        startActivity(intent);

    }

    @Optional
    @OnClick({ R.id.tv_start, R.id.tv_face})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.tv_start:
                doFaceRecognition();
                break;

            case R.id.tv_face:
                initListener();
                break;
        }
    }


    private void initListener() {

        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);
        mediaManager.setMediaListener(new FaceRecognizeListener() {
            @Override
            public void recognizeResult(List<FaceRecognizeBean> list) {
                if (list.size() > 0) {
                    rlLoaderView.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(SplashActivity.this, BotChatActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
