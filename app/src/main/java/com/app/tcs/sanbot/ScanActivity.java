package com.app.tcs.sanbot;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.beans.OperationResult;
import com.qihancloud.opensdk.function.beans.SpeakOption;
import com.qihancloud.opensdk.function.unit.MediaManager;
import com.qihancloud.opensdk.function.unit.SpeechManager;

import java.nio.ByteBuffer;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanActivity extends TopBaseActivity {

    @BindView(R.id.ll_scan_result_view)
    LinearLayout scanResultView;
    @BindView(R.id.ll_scan_view)
    LinearLayout scanView;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_scan_result)
    TextView tvScanResult;

    private String name;


    private TextToSpeech tts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity_main);
        ButterKnife.bind(this);

        scanView.setVisibility(View.VISIBLE);
        scanResultView.setVisibility(View.GONE);
        name = getIntent().getStringExtra("name");
        tvUserName.setText("Hello " + name);


    }

    @Override
    protected void onMainServiceConnected() {

    }


    @OnClick(R.id.tv_scan_button)
    public void submit(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Log.d("FaceRecognizeActivity", "Cancelled");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Log.d("FaceRecognizeActivity", "Scanned");

                scanView.setVisibility(View.GONE);
                scanResultView.setVisibility(View.VISIBLE);
                tvScanResult.setText("Thanks " + name+"(Flight No: " + intentResult.getContents() +").");
                //Toast.makeText(this, "Scanned: " + intentResult.getContents(), Toast.LENGTH_LONG).show();
            }
        }
    }


}
