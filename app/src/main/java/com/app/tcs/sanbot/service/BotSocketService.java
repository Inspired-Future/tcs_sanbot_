package com.app.tcs.sanbot.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class BotSocketService extends Service {
    private Socket mSocket;
    public static final String TAG = BotSocketService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "on created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "start command", Toast.LENGTH_SHORT).show();
         String url = intent.getStringExtra("Bot_WSS");

        String baseUrl = url.substring(0,63);//"https://directline.botframework.com/v3/directline/conversations";
        final String queryUrl = url.substring(63,url.length());

        Log.d(TAG, "call: url - " + url);
        Log.d(TAG, "call: baseUrl - " + baseUrl);
        Log.d(TAG, "call: queryUrl - " + queryUrl);

        try {
            mSocket = IO.socket(baseUrl); //http://deposits.socashdev.com/user/announce");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        mSocket.on("message", onNewMessage);
        mSocket.on("typing", onTypeMessage);


        JSONObject obj = new JSONObject();
        try {
            obj.put("url", queryUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("get", obj);






        mSocket.connect();

        if(mSocket.connected()){
            Log.d(TAG, "connected");
        }


        return START_STICKY;
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //String message = args[0].toString();
            Log.d(TAG, "call: new message ");
        }
    };

    private Emitter.Listener onTypeMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //String message = args[0].toString();
            Log.d(TAG, "call: new onTypeMessage ");
        }
    };


    @Override
    public void onDestroy(){
        mSocket.disconnect();
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }


}