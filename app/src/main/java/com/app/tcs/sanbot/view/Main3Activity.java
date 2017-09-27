package com.app.tcs.sanbot.view;

import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tcs.sanbot.R;
import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Main3Activity extends AppCompatActivity   {


    //public TextToSpeech tts;
    Handler customHandler;
    Handler customHandler111;

    public boolean flag = false;
    int i =0;
    //HTextView tv;
    String[] hh = {"Alaxa", "Alaaxa","Alaksa"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //String

        //tts = new TextToSpeech(this, this);
        customHandler = new Handler();
       /* customHandler.postDelayed(nextRotateThread, 5000);


        customHandler111 = new Handler();
*/
    }

    private Runnable nextRotateThread = new Runnable() {
        public void run() {


            //tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
           // tts.speak()
           // tv.animateText("haaaaaaaa LIFE PLANNER");
            if(i<3){
                i=0;
            }
            stt(hh[i]);
                    i++;


        }
    };



    private Runnable customHandler11 = new Runnable() {
        public void run() {



            stt("turn on light");


            customHandler.postDelayed(nextRotateThread, 5000);

            flag = true;


        }
    };






    class SimpleAnimationListener extends Animation implements Animation.AnimationListener {

        private Context context;

        public SimpleAnimationListener(Context context) {
            this.context = context;
        }


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            Toast.makeText(context, "Animation finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    void stt(String msg){
        Log.d("TAG_ROBOTS","msg ::: --- " + msg);
        HashMap<String,String> ttsParams = new HashMap<String, String>();
        ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                Main3Activity.this.getPackageName());

     //   tts.speak(msg, TextToSpeech.QUEUE_FLUSH, ttsParams);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //tts.stop();
    }
}
