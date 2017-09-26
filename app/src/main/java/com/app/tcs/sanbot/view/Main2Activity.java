package com.app.tcs.sanbot.view;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.app.tcs.sanbot.R;
import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;
import com.qihancloud.opensdk.function.unit.WheelMotionManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class Main2Activity extends TopBaseActivity {


    private WheelMotionManager wheelMotionManager;
    private Handler customHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);
        customHandler = new Handler();




    }


    @Optional
    @OnClick(R.id.btn_move)
    public void onClick(View view){
        DistanceWheelMotion distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_FORWARD_RUN, 20, 400);
        wheelMotionManager.doDistanceMotion(distanceWheelMotion);

        customHandler = new Handler();
        customHandler.postDelayed(updateListThread, 16000);
    }

    @Override
    protected void onMainServiceConnected() {

    }

    private Runnable updateListThread = new Runnable() {
        public void run() {
            RelativeAngleWheelMotion relativeAngleWheelMotion;
           relativeAngleWheelMotion = new RelativeAngleWheelMotion(RelativeAngleWheelMotion.TURN_LEFT, 10, 90);
            wheelMotionManager.doRelativeAngleMotion(relativeAngleWheelMotion);

            customHandler.postDelayed(nextRotateThread, 5000);

        }
    };

    private Runnable nextRotateThread = new Runnable() {
        public void run() {
            DistanceWheelMotion distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_FORWARD_RUN, 20, 200);
            wheelMotionManager.doDistanceMotion(distanceWheelMotion);

        }
    };
}
