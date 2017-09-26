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

public class Main3Activity extends AppCompatActivity implements TextToSpeech.OnInitListener  {


    public TextToSpeech tts;
    Handler customHandler;
    //HTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //tts = new TextToSpeech(this, this);
        customHandler = new Handler();
        //customHandler.postDelayed(nextRotateThread, 16000);
       // tv = (HTextView) findViewById(R.id.textview3);

       /* ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tv.animateText("YOUR NIGHT LIFE PLANNER");
                customHandler.postDelayed(nextRotateThread, 2000);
            }
        });*/

        //

        //tv.setAnimation(new SimpleAnimationListener(this));

        /*tv.setAnimation(new SimpleAnimationListener(this));

       // tv.setProgress(50 / 100f);
        if (tv instanceof HTextView) {


            ((HTextView) tv).animateText("hgdshsdjkjdskjdsjlkjdslkjslkdjlkdsjlkjsdlkjds");


        }*/
        //tv.animateText("hgdshsdjkjdskjdsjlkjdslkjslkdjlkdsjlkjsdlkjds");
        //((HTextView) v).animateText



    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {


            tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {

                @Override
                public void onUtteranceCompleted(final String utteranceId) {
                    //System.out.println("Completed");
                    Log.d("TAG_RAJ","::::: Completed ::::::");

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //UI changes
                        }
                    });
                }


            });

            int result = tts.setLanguage(Locale.US);
            tts.setPitch(1);
            tts.setSpeechRate(1f);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }
        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }


    private Runnable nextRotateThread = new Runnable() {
        public void run() {

            /*HashMap<String,String> ttsParams = new HashMap<String, String>();
            ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                    Main3Activity.this.getPackageName());


            String text = "Hello there I am Sevaka. Your SIA Krisflyer lounge assistant. " +
                    "Let me guide you through the services. Please choose from the following";

            tts.speak(text, TextToSpeech.QUEUE_FLUSH, ttsParams);*/
            //tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
           // tts.speak()


           // tv.animateText("haaaaaaaa LIFE PLANNER");

            customHandler.postDelayed(nextRotateThread, 1000);

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

}
