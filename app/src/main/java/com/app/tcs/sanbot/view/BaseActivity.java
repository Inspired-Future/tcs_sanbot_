package com.app.tcs.sanbot.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.app.tcs.sanbot.R;
import com.app.tcs.sanbot.appconstant.AppConstant;
import com.app.tcs.sanbot.bean.GenerateToken;
import com.app.tcs.sanbot.bean.StartConversation;
import com.app.tcs.sanbot.model.GetBotTokenModelImpl;
import com.app.tcs.sanbot.model.StartConversationModelImpl;
import com.app.tcs.sanbot.presenter.GetBotTokenPresenterImpl;
import com.app.tcs.sanbot.presenter.IGetBotTokenPresenter;
import com.app.tcs.sanbot.presenter.IStartConversationPresenter;
import com.app.tcs.sanbot.presenter.StartConversationPresenterImpl;
import com.app.tcs.sanbot.restfull.ApiClient;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.app.tcs.sanbot.service.BotSocketService;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class BaseActivity extends AppCompatActivity implements IGetBotTokenPresenter.View, IStartConversationPresenter.View{
   @BindView(R.id.pb_loader)
    ProgressBar pbLoader;

    public ApiInterface apiService;
    public GetBotTokenPresenterImpl getBotTokenPresenter;

    private Socket mSocket;
    public static final String TAG = BotSocketService.class.getSimpleName();
    public StartConversationPresenterImpl startConversationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        if (apiService == null) {
            apiService = ApiClient.getClient(
                    BaseActivity.this).create(ApiInterface.class);
        }
        getBotTokenPresenter = new GetBotTokenPresenterImpl(
                this,
                new GetBotTokenModelImpl(this, apiService)
        );
        getBotTokenPresenter.getBotToken(AppConstant.BOT_SECRET_KEY);

    }

    @Override
    public void showProgress() {
        pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoader.setVisibility(View.GONE);
    }

    @Override
    public void showError(int code, String message) {

    }

    @Override
    public void onGetBotTokenSuccessful(GenerateToken generateTokenResponse) {
        startConversationPresenter = new StartConversationPresenterImpl(
                this,
                new StartConversationModelImpl(this, apiService)
        );
        startConversationPresenter.getToken("Bearer " + generateTokenResponse.getToken());
    }

    @Override
    public void onStartConversationSuccessful(StartConversation startConversation) {
        /*Intent intent = new Intent(this, BotSocketService.class);
        intent.putExtra("Bot_WSS",startConversation.getStreamUrl().replace("wss://","https://"));
        startService(intent);
*/
        //connectSocket(startConversation.getStreamUrl());//.replace("wss://","https://"));



        connectSocket("https://74625a41.ngrok.io/devices");
       // connectWebSocket(startConversation.getStreamUrl());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",startConversation.getConversationId());
        bundle.putString("token",startConversation.getToken());
        chatFragment.setArguments(bundle);
        ft.add(R.id.fl_chatlist, chatFragment);
        ft.commit();
    }


    private void connectSocket(String url){

       /* String baseUrl = url.substring(0,34);
        final String queryUrl = url.substring(35,url.length());
        Log.d(TAG, "call: url - " + url);
        Log.d(TAG, "call: baseUrl - " + baseUrl);
        Log.d(TAG, "call: queryUrl - " + queryUrl);*/
        try {
            mSocket = IO.socket(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }

       // mSocket.on("message", onNewMessage);
       // mSocket.on("typing", onTypeMessage);
        /*JSONObject obj = new JSONObject();
        try {
            obj.put("url", queryUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("get", obj);
        */


        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_CONNECT");
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_CONNECT_ERROR :::: " );
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_CONNECT_TIMEOUT");
            }
        });

        mSocket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_CONNECTING");
            }
        });



        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_DISCONNECT");
            }
        });

        mSocket.on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_ERROR");
            }
        });


        mSocket.on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_MESSAGE");
            }
        });

        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_DISCONNECT");
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_CONNECT_TIMEOUT");
            }
        });

        mSocket.on(Socket.EVENT_PING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_PING");
            }
        });

        mSocket.on(Socket.EVENT_PING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_PING");
            }
        });

        mSocket.on(Socket.EVENT_PONG, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_PONG");
            }
        });

        mSocket.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_RECONNECT");
            }
        });

        mSocket.on(Socket.EVENT_RECONNECT_ATTEMPT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_RECONNECT_ATTEMPT");
            }
        });

        mSocket.on(Socket.EVENT_RECONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_RECONNECT_ERROR");
            }
        });

        mSocket.on(Socket.EVENT_RECONNECT_FAILED, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_RECONNECT_FAILED");
            }
        });

        mSocket.on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("STAG", "Socket.EVENT_RECONNECTING");
            }
        });


        mSocket.connect();

        if(mSocket.connected()){
            Log.d(TAG, "connected");
        }

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
}
