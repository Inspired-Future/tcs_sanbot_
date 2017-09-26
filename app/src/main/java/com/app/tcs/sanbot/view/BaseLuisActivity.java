/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license.
 * //
 * Project Oxford: http://ProjectOxford.ai
 * //
 * ProjectOxford SDK GitHub:
 * https://github.com/Microsoft/ProjectOxford-ClientSDK
 * //
 * Copyright (c) Microsoft Corporation
 * All rights reserved.
 * //
 * MIT License:
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * //
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * //
 * THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.app.tcs.sanbot.view;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.app.tcs.sanbot.R;
import com.app.tcs.sanbot.appconstant.AppConstant;
import com.app.tcs.sanbot.bean.LuisSpeechIntentResponse;
import com.app.tcs.sanbot.bean.LuisSpeechResponse;
import com.app.tcs.sanbot.utils.SharepreferenceKeystore;
import com.google.gson.Gson;
import com.microsoft.bing.speech.SpeechClientStatus;
import com.microsoft.cognitiveservices.speechrecognition.DataRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.ISpeechRecognitionServerEvents;
import com.microsoft.cognitiveservices.speechrecognition.MicrophoneRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionResult;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionStatus;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionServiceFactory;
import com.qihancloud.opensdk.base.TopBaseActivity;

import java.util.Locale;

public abstract class BaseLuisActivity extends TopBaseActivity implements ISpeechRecognitionServerEvents{
    int m_waitSeconds = 0;
    DataRecognitionClient dataClient = null;
    MicrophoneRecognitionClient micClient = null;
    FinalResponseStatus isReceivedResponse = FinalResponseStatus.NotReceived;
    Handler customHandler;
    public SharepreferenceKeystore sharepreferenceKeystore;
   // public TextToSpeech tts;



    public enum FinalResponseStatus {NotReceived, OK, Timeout}

    /**
     * Gets the primary subscription key
     */
    public String getPrimaryKey() {
        return this.getString(R.string.primaryKey);
    }

    /**
     * Gets the LUIS application identifier.
     *
     * @return The LUIS application identifier.
     */
    private String getLuisAppId() {
        return this.getString(R.string.luisAppID);
    }

    /**
     * Gets the LUIS subscription identifier.
     *
     * @return The LUIS subscription identifier.
     */
    private String getLuisSubscriptionID() {
        return this.getString(R.string.luisSubscriptionID);
    }


    /**
     * Gets the default locale.
     *
     * @return The default locale.
     */
    private String getDefaultLocale() {
        return "en-us";
    }

    /**
     * Gets the short wave file path.
     *
     * @return The short wave file.
     */
    private String getShortWaveFile() {
        return "whatstheweatherlike.wav";
    }

    /**
     * Gets the long wave file path.
     *
     * @return The long wave file.
     */
    private String getLongWaveFile() {
        return "batman.wav";
    }

    /**
     * Gets the Cognitive Service Authentication Uri.
     *
     * @return The Cognitive Service Authentication Uri.  Empty if the global default is to be used.
     */
    private String getAuthenticationUri() {
        return this.getString(R.string.authenticationUri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharepreferenceKeystore = SharepreferenceKeystore.getInstance(this);
        customHandler = new Handler();
        //tts = new TextToSpeech(this, this);
        initiateClient();
    }

    @Override
    protected void onMainServiceConnected() {
    }


    private void initiateClient() {
        if (this.micClient != null) {
            this.micClient.endMicAndRecognition();
            try {
                this.micClient.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            this.micClient = null;
        }

        if (this.dataClient != null) {
            try {
                this.dataClient.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            this.dataClient = null;
        }

        this.m_waitSeconds = 200;

        if (this.micClient == null) {
            this.WriteLine("--- Start microphone dictation with Intent detection ----");
            this.micClient =
                    SpeechRecognitionServiceFactory.createMicrophoneClientWithIntent(
                            this,
                            this.getDefaultLocale(),
                            this,
                            this.getPrimaryKey(),
                            this.getLuisAppId(),
                            this.getLuisSubscriptionID());
            this.micClient.setAuthenticationUri(this.getAuthenticationUri());
        }
        this.micClient.startMicAndRecognition();
    }


    public void onFinalResponseReceived(final RecognitionResult response) {
        boolean isFinalDicationMessage =
                (response.RecognitionStatus == RecognitionStatus.EndOfDictation ||
                        response.RecognitionStatus == RecognitionStatus.DictationEndSilenceTimeout);
        if (null != this.micClient
                && isFinalDicationMessage) {
            // we got the final result, so it we can end the mic reco.  No need to do this
            // for dataReco, since we already called endAudio() on it as soon as we were done
            // sending all the data.

            //this.micClient.endMicAndRecognition();
        }

        if (isFinalDicationMessage) {
            this.isReceivedResponse = FinalResponseStatus.OK;
        }

        if (!isFinalDicationMessage) {
            this.WriteLine("********* Final n-BEST Results *********");

            if(response.Results.length>0) {
                if(this instanceof SplashActivity){
                    /*if(response.Results[0].DisplayText.toLowerCase().indexOf("hello") != -1){
                        checkFaceDeduction();
                    }*/

                }else if (this instanceof BotChatActivity) {
                    /*if (!sharepreferenceKeystore.getBoolean(AppConstant.SPEECH_LISTENER_FLAG)) {
                        sharepreferenceKeystore.updateBoolean(AppConstant.SPEECH_LISTENER_FLAG, true);
                        onSendLuisMsg(response.Results[0].DisplayText);
                    }*/

                }else if (this instanceof BotMsgActivity) {
                    if (!sharepreferenceKeystore.getBoolean("TextToSpeechFlag")) {
                        //sharepreferenceKeystore.updateBoolean(AppConstant.SPEECH_LISTENER_FLAG, true);
                        onSendLuisMsg(response.Results[0].DisplayText);
                    }
                }
                this.WriteLine("[" + 0 + "]" + " Confidence=" + response.Results[0].Confidence +
                        " Text=\"" + response.Results[0].DisplayText + "\"");
                this.WriteLine();
            }
        }
    }

    /**
     * Called when a final response is received and its intent is parsed
     */
    public void onIntentReceived(final String payload) {
        this.WriteLine("--- Intent received by onIntentReceived() ---");


        Log.d("TAG_ROBOT","payload  :::: " + payload);
        /*if (this instanceof SplashActivity) {
            Gson gson = new Gson();
            LuisSpeechResponse luisSpeechResponse = gson.fromJson(payload, LuisSpeechResponse.class);
            for(int i=0; i<luisSpeechResponse.getIntents().size();i++){
                if(luisSpeechResponse.getIntents().get(i).getIntent().equalsIgnoreCase("GeneralUtterance.Greet")){
                    if(luisSpeechResponse.getIntents().get(i).getScore()>0.5){
                        checkFaceDeduction();
                        break;
                    }
                }
            }
        }*/
        this.WriteLine(payload);
        this.WriteLine();
    }

    public void onPartialResponseReceived(final String response) {
        this.WriteLine("--- Partial result received by onPartialResponseReceived() ---");
        this.WriteLine(response);
        if (this instanceof BotMsgActivity) {
            if (!sharepreferenceKeystore.getBoolean("TextToSpeechFlag")) {
                onSendPartialLuisMsg(response);
            }
        }
        this.WriteLine();
    }

    public void onError(final int errorCode, final String response) {
        this.WriteLine("--- Error received by onError() ---");
        this.WriteLine("Error code: " + SpeechClientStatus.fromInt(errorCode) + " " + errorCode);
        this.WriteLine("Error text: " + response);
        this.WriteLine();
    }

    /**
     * Called when the microphone status has changed.
     *
     * @param recording The current recording state
     */
    public void onAudioEvent(boolean recording) {
        this.WriteLine("--- Microphone status change received by onAudioEvent() ---");
        this.WriteLine("********* Microphone status: " + recording + " *********");
        if (recording) {
            this.WriteLine("Please start speaking.");
        }

        WriteLine();
        if (!recording) {
            if (null != this.micClient) {
                this.micClient.endMicAndRecognition();
                customHandler.postDelayed(updateClient, 100);
            }
            //initiateClient();
        }
    }

    /**
     * Writes the line.
     */
    private void WriteLine() {
        this.WriteLine("");
    }

    /**
     * Writes the line.
     *
     * @param text The line to write.
     */
    private void WriteLine(String text) {
        Log.d("TAG_LUIS", text);
    }

    private Runnable updateClient = new Runnable() {
        public void run() {
            initiateClient();
        }
    };

    protected abstract void checkFaceDeduction();

    protected abstract void onSendLuisMsg(String msg);

    protected abstract void onSendPartialLuisMsg(String msg);


}
