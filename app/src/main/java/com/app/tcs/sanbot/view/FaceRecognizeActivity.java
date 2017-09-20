package com.app.tcs.sanbot.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.app.tcs.sanbot.R;
import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.FaceRecognizeBean;
import com.qihancloud.opensdk.function.unit.MediaManager;
import com.qihancloud.opensdk.function.unit.interfaces.media.FaceRecognizeListener;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class FaceRecognizeActivity extends TopBaseActivity {

    @BindView(R.id.ll_button_view)
    LinearLayout llButtonView;
    @BindView(R.id.ll_loader)
    LinearLayout llLoaderView;
    private int type = 0;



    private MediaManager mediaManager;
    /*MediaCodec mediaCodec;
    long decodeTimeout = 16000;
    MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
    ByteBuffer[] videoInputBuffers;
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_recognize_activity);

        ButterKnife.bind(this);
        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);


    }

    @Override
    public void onResume() {
        super.onResume();
        llButtonView.setVisibility(View.VISIBLE);
        llLoaderView.setVisibility(View.GONE);
        initListener();
        type = 0;

        //speechManager.startSpeak("Haaaaaa   I am rajesh");
    }

    @Override
    protected void onMainServiceConnected() {

    }

    @Optional
    @OnClick({R.id.tv_anonymous_user, R.id.tv_authentication_user})
    public void onClick(View view) {
        llButtonView.setVisibility(View.GONE);
        llLoaderView.setVisibility(View.VISIBLE);
        switch (view.getId()){
            case R.id.tv_anonymous_user:
                type = 2;
                break;
            case R.id.tv_authentication_user:
                type = 1;
                break;
        }
    }

    private void initListener() {
        mediaManager.setMediaListener(new FaceRecognizeListener() {
            @Override
            public void recognizeResult(List<FaceRecognizeBean> list) {
                if(type!= 0 && list.size()>0) {
                    StringBuilder sb = new StringBuilder();
                    if(type == 1) {
                        for (FaceRecognizeBean bean : list) {
                            if (bean.getUser() != null) {
                                Intent intent = new Intent(FaceRecognizeActivity.this, ScanActivity.class);
                                intent.putExtra("name", bean.getUser());
                                startActivity(intent);
                            }
                           // sb.append(new Gson().toJson(bean));
                           // sb.append("\n");
                            Log.d("TAGGGG", "hjhhjhhjhj" + sb.toString());
                        }
                    }else{
                        Intent intent = new Intent(FaceRecognizeActivity.this, ScanActivity.class);
                        intent.putExtra("name", "Guest User");
                        startActivity(intent);
                    }
                }
            }
        });
    }


}
