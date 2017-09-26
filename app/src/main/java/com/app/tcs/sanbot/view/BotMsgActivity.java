package com.app.tcs.sanbot.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.app.tcs.sanbot.R;
import com.app.tcs.sanbot.appconstant.AppConstant;
import com.app.tcs.sanbot.bean.BotMsgActivitiesResponse;
import com.app.tcs.sanbot.bean.BotMsgButtonResponse;
import com.app.tcs.sanbot.bean.BotMsgResponse;
import com.app.tcs.sanbot.bean.GenerateToken;
import com.app.tcs.sanbot.bean.SendConversationResponse;
import com.app.tcs.sanbot.bean.StartConversation;
import com.app.tcs.sanbot.model.GetBotTokenModelImpl;
import com.app.tcs.sanbot.model.GetConversationModelImpl;
import com.app.tcs.sanbot.model.SendConversationModelImpl;
import com.app.tcs.sanbot.model.StartConversationModelImpl;
import com.app.tcs.sanbot.presenter.GetBotTokenPresenterImpl;
import com.app.tcs.sanbot.presenter.GetConversationPresenterImpl;
import com.app.tcs.sanbot.presenter.IGetBotTokenPresenter;
import com.app.tcs.sanbot.presenter.IGetConversationPresenter;
import com.app.tcs.sanbot.presenter.ISendConversationPresenter;
import com.app.tcs.sanbot.presenter.IStartConversationPresenter;
import com.app.tcs.sanbot.presenter.SendConversationPresenterImpl;
import com.app.tcs.sanbot.presenter.StartConversationPresenterImpl;
import com.app.tcs.sanbot.restfull.ApiClient;
import com.app.tcs.sanbot.restfull.ApiInterface;
import com.app.tcs.sanbot.service.BotSocketService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;
import com.qihancloud.opensdk.function.unit.HandMotionManager;
import com.qihancloud.opensdk.function.unit.ProjectorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.client.Socket;


public class BotMsgActivity extends BaseLuisActivity implements IGetBotTokenPresenter.View,
        IStartConversationPresenter.View, ISendConversationPresenter.View,
        IGetConversationPresenter.View, TextToSpeech.OnInitListener {

    public ApiInterface apiService;
    public ChatFragment chatFragment;
    public GetBotTokenPresenterImpl getBotTokenPresenter;
    public StartConversationPresenterImpl startConversationPresenter;
    public SendConversationPresenterImpl sendConversationPresenter;
    public GetConversationPresenterImpl getConversationPresenter;
    private String conversationId;
    private String conversationToken;
    private Handler getBotMsgHandler;
    private int lastMsgWhichTalked = 0;

    public TextToSpeech tts_face_detection;
    private HandMotionManager handMotionManager;

    private Handler customHandler;


    public static final String TAG = BotSocketService.class.getSimpleName();

    ProjectorManager projectorManager;


    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tl_bot_btn_list)
    TableLayout tlBotMsglist;

    @BindView(R.id.iv_rajesh_head)
    ImageView rajeshHead;


    @BindView(R.id.iv_sanbot_head)
    ImageView sanbotHead;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_bot_msg);
        ButterKnife.bind(this);
        projectorManager = (ProjectorManager) getUnitManager(FuncConstant.PROJECTOR_MANAGER);

        handMotionManager = (HandMotionManager) getUnitManager(FuncConstant.HANDMOTION_MANAGER);


        sharepreferenceKeystore.updateBoolean("TextToSpeechFlag", false);
        tts_face_detection = new TextToSpeech(this, this);

        if (apiService == null) {
            apiService = ApiClient.getClient(
                    BotMsgActivity.this).create(ApiInterface.class);
        }
        getBotTokenPresenter = new GetBotTokenPresenterImpl(
                this,
                new GetBotTokenModelImpl(this, apiService)
        );
        getBotTokenPresenter.getBotToken(AppConstant.BOT_SECRET_KEY);

        /*ViewTreeObserver vto = tvMsg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvMsg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tvMsg.animateText("");
            }
        });
*/

        // getBotTokenPresenter.getBotToken(AppConstant.BOT_SECRET_KEY);

    }

    @Override
    protected void checkFaceDeduction() {

    }

    @Override
    protected void onSendLuisMsg(String msg) {
        tlBotMsglist.setVisibility(View.GONE);

        //tvMsg.setSingleLine(false);
        //tvMsg.animateText(msg);
        sanbotHead.setVisibility(View.GONE);
        rajeshHead.setVisibility(View.VISIBLE);
        tvMsg.setText(msg);
        sendConversationPresenter.sendConversation(conversationId,
                "message", msg, "user1", conversationToken, false);

    }

    @Override
    protected void onSendPartialLuisMsg(String msg) {
        tvMsg.setText(msg);
        sanbotHead.setVisibility(View.GONE);
        rajeshHead.setVisibility(View.VISIBLE);
        //tlBotMsglist.removeAllViews();
    }


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
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

        conversationId = startConversation.getConversationId();
        conversationToken = startConversation.getToken();

        sendConversationPresenter = new SendConversationPresenterImpl(
                this,
                new SendConversationModelImpl(this, apiService)
        );
        getConversationPresenter = new GetConversationPresenterImpl(
                this,
                new GetConversationModelImpl(this, apiService)
        );
        //getBotMsgHandler = new Handler();
        //getBotMsgHandler.postDelayed(getBotMsgThread, 0);

        getConversationPresenter.getConversationMsg(conversationId, conversationToken);
    }


    @Override
    public void onGetConversationSuccessful(BotMsgResponse botMsgResponse) {

        if (!sharepreferenceKeystore.getBoolean("TextToSpeechFlag") && botMsgResponse.getActivities().size() > lastMsgWhichTalked) {
            if (lastMsgWhichTalked == 0) {
                NoAngleHandMotion noAngleHandMotion = new NoAngleHandMotion(NoAngleHandMotion.PART_RIGHT, 30, NoAngleHandMotion.ACTION_UP);
                handMotionManager.doNoAngleMotion(noAngleHandMotion);
            } else if (lastMsgWhichTalked == 2) {
                NoAngleHandMotion noAngleHandMotion = new NoAngleHandMotion(NoAngleHandMotion.PART_RIGHT, 30, NoAngleHandMotion.ACTION_RESET);
                handMotionManager.doNoAngleMotion(noAngleHandMotion);
            }
            if (botMsgResponse.getActivities().get(lastMsgWhichTalked).getFrom().getName() != null) {
                if (botMsgResponse.getActivities().get(lastMsgWhichTalked).getFrom().getName().equalsIgnoreCase("Sofia")) {
                    //tvMsg.setBackgroundColor(Color.parseColor("#ff0000"));
                    //botMsgResponse.getActivities().get(0).getId()
                    sanbotHead.setVisibility(View.VISIBLE);
                    rajeshHead.setVisibility(View.GONE);
                    updateSofiaMsg(botMsgResponse.getActivities().get(lastMsgWhichTalked), botMsgResponse.getActivities().get(0).getId());
                } else {
                    getConversationPresenter.getConversationMsg(conversationId, conversationToken);
                }
            } else {

                getConversationPresenter.getConversationMsg(conversationId, conversationToken);
            }
            lastMsgWhichTalked++;
        } else {
            getConversationPresenter.getConversationMsg(conversationId, conversationToken);
        }

    }

    private void updateSofiaMsg(BotMsgActivitiesResponse botMsgActivitiesResponse, String botId) {
        if (botMsgActivitiesResponse.getText() != null) {
            tvMsg.setText(botMsgActivitiesResponse.getText().replace("Hello Rawjaish","Hello Rajesh"));
        }
        //tvMsg.setText(botMsgActivitiesResponse.getText());
        sharepreferenceKeystore.updateBoolean("TextToSpeechFlag", true);
        textToSpeech(botMsgActivitiesResponse.getText());
        tlBotMsglist.removeAllViews();
        tlBotMsglist.setVisibility(View.VISIBLE);
        if (botMsgActivitiesResponse.getAttachments() != null && botMsgActivitiesResponse.getAttachments().size() > 0) {
            if (botMsgActivitiesResponse.getAttachments().size() > 1) {
                sanbotHead.setVisibility(View.GONE);
                rajeshHead.setVisibility(View.GONE);
                for (int i = 0; i < botMsgActivitiesResponse.getAttachments().size(); i++) {
                    TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.bot_content_new, null);
                    TextView dynamicNewsTextTitle = (TextView) row.findViewById(R.id.tv_news_title);
                    TextView dynamicNewsTextDescription = (TextView) row.findViewById(R.id.tv_news_description);
                    final ImageView dynamicNewsImage = (ImageView) row.findViewById(R.id.iv_news_img);

                    dynamicNewsTextTitle.setText(botMsgActivitiesResponse.getAttachments().get(i).getContent().getTitle());

                    dynamicNewsTextDescription.setText(botMsgActivitiesResponse.getAttachments().get(i).getContent().getText());

                    if (botMsgActivitiesResponse.getAttachments().get(i).getContent().getImages() != null &&
                            botMsgActivitiesResponse.getAttachments().get(i).getContent().getImages().size() > 0) {
                        Glide.with(this)
                                .load(botMsgActivitiesResponse.getAttachments().get(i).getContent().getImages().get(0).getUrl())
                                .asBitmap().fitCenter().error(R.mipmap.ic_launcher)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        dynamicNewsImage.setImageBitmap(resource);
                                    }
                                });
                    }

                    tlBotMsglist.addView(row);
                }


                projectorManager.setMode(ProjectorManager.MODE_WALL);
                projectorManager.setMirror(ProjectorManager.MIRROR_CLOSE);
                //projectorManager.setMirror(ProjectorManager.MIRROR_UD);
                //projectorManager.switchProjector(true);
                //projectorManager.setMode(ProjectorManager.MODE_WALL);
                projectorManager.switchProjector(true);

                customHandler = new Handler();
                customHandler.postDelayed(updateListThread, 15000);


            } else {
                if (botMsgActivitiesResponse.getAttachments().get(0).getContent().getButtons() == null) {

                    TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.bot_content_wheather, null);
                    TextView dynamicNewsTextTitle = (TextView) row.findViewById(R.id.tv_wheather_title);
                    TextView dynamicNewsTextDescription = (TextView) row.findViewById(R.id.tv_wheather_description);
                    final ImageView dynamicNewsImage = (ImageView) row.findViewById(R.id.iv_wheather_img);


                    dynamicNewsTextTitle.setText(botMsgActivitiesResponse.getAttachments().get(0).getContent().getTitle());

                    textToSpeech(botMsgActivitiesResponse.getAttachments().get(0).getContent().getTitle() + botMsgActivitiesResponse.getAttachments().get(0).getContent().getSubtitle());

                    Glide.with(this)
                            .load(botMsgActivitiesResponse.getAttachments().get(0).getContent().getImages().get(0).getUrl())
                            .asBitmap().fitCenter().error(R.mipmap.ic_launcher)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    dynamicNewsImage.setImageBitmap(resource);
                                }
                            });

                    dynamicNewsTextDescription.setText(botMsgActivitiesResponse.getAttachments().get(0).getContent().getSubtitle());
                    tlBotMsglist.addView(row);
                } else {
                    List<BotMsgButtonResponse> botMsgButtonResponses = botMsgActivitiesResponse.getAttachments().get(0).getContent().getButtons();
                    for (int i = 0; i < botMsgButtonResponses.size(); i++) {
                        TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.bot_content_button, null);

                        TextView dynamicTextView = (TextView) row.findViewById(R.id.tv_bot_content_button_row);
                        dynamicTextView.setText(botMsgButtonResponses.get(i).getTitle());

                        dynamicTextView.setTag(botId + "$" +
                                botMsgButtonResponses.get(i).getTitle());

                        dynamicTextView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                Log.d("TAG_BOT_MSG", "" + view.getTag());
                                sendConversationPresenter.sendConversation(conversationId,
                                        "message", view.getTag().toString(), "user1", conversationToken, true);
                            }
                        });
                        tlBotMsglist.addView(row);

                    }
                }
            }


        }
    }


    void textToSpeech(String speechTxt) {
        HashMap<String, String> ttsParams = new HashMap<String, String>();
        ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                BotMsgActivity.this.getPackageName());
        tts_face_detection.speak(speechTxt, TextToSpeech.QUEUE_FLUSH, ttsParams);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts_face_detection.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                @Override
                public void onUtteranceCompleted(final String utteranceId) {
                    Log.d("TAG_FACE", "::::: Completed ::::::");

                    sharepreferenceKeystore.updateBoolean("TextToSpeechFlag", false);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getConversationPresenter.getConversationMsg(conversationId, conversationToken);
                        }
                    });
                }
            });

            int result = tts_face_detection.setLanguage(Locale.ENGLISH);
            tts_face_detection.setPitch(1);
            tts_face_detection.setSpeechRate(1f);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }


    @Override
    public void onSendConversationSuccessful(SendConversationResponse sendConversationResponse) {
        getConversationPresenter.getConversationMsg(conversationId, conversationToken);
    }


    private Runnable updateListThread = new Runnable() {
        public void run() {
            projectorManager.switchProjector(false);
            sharepreferenceKeystore.updateBoolean("TextToSpeechFlag", false);
            getConversationPresenter.getConversationMsg(conversationId, conversationToken);
        }
    };


}
