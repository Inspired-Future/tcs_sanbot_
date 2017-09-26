package com.app.tcs.sanbot.view;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaCodec;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.app.tcs.sanbot.R;
import com.google.gson.Gson;
import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.FaceRecognizeBean;
import com.qihancloud.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.qihancloud.opensdk.function.unit.HandMotionManager;
import com.qihancloud.opensdk.function.unit.MediaManager;
import com.qihancloud.opensdk.function.unit.interfaces.media.FaceRecognizeListener;
import com.qihancloud.opensdk.function.unit.interfaces.media.MediaStreamListener;
import com.wang.avi.AVLoadingIndicatorView;
//import com.wang.avi.AVLoadingIndicatorView;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class FaceDetectionActivity extends TopBaseActivity {

    @BindView(R.id.rl_loader)
    RelativeLayout rlLoaderView;

     @BindView(R.id.pb_loader)
     AVLoadingIndicatorView avi;

    private MediaManager mediaManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_face_detection);
        ButterKnife.bind(this);
        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);
        //avi.setIndicatorColor(Color.parseColor("#FFD700"));
        avi.show();
        initListener();
    }

    @Override
    protected void onMainServiceConnected() {
    }




    private void initListener() {

        mediaManager.setMediaListener(new FaceRecognizeListener() {
            @Override
            public void recognizeResult(List<FaceRecognizeBean> list) {
                startActivity(new Intent(FaceDetectionActivity.this, BotMsgActivity.class));
                finish();

                /*StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUser() != null) {
                        startActivity(new Intent(FaceDetectionActivity.this, BotMsgActivity.class));
                        finish();
                    }
                }*/
                   /* for (FaceRecognizeBean bean : list) {
                        if(list.get())
                        sb.append(new Gson().toJson(bean));
                        sb.append("\n");
                    }*/
                //Log.d("TAG_FACE", "sb.toString() ::: " + sb.toString());
                // }
            }
        });


    }

    @OnClick(R.id.btn_start)
    public void submit(View view) {
        startActivity(new Intent(FaceDetectionActivity.this, BotMsgActivity.class));
        finish();
    }

}
