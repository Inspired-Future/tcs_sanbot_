package com.app.tcs.sanbot.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.app.tcs.sanbot.R;
import com.app.tcs.sanbot.bean.BotMsgActivitiesResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private EventTriggerInterface eventTriggerInterface;
    private Context context;

    List<BotMsgActivitiesResponse> botMsgActivitiesResponseList;
    public ChatListAdapter(Context context, List<BotMsgActivitiesResponse> botMsgActivitiesResponseList,
                           EventTriggerInterface eventTriggerInterface) {
        this.context = context;
        this.botMsgActivitiesResponseList = botMsgActivitiesResponseList;
        this.eventTriggerInterface = eventTriggerInterface;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_my_msg)
        TextView tvMyMsg;
        @BindView(R.id.tv_my_msg_timestamp)
        TextView tvMyMsgTimeStamp;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public class BotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.rl_bot_msg)
        RelativeLayout rlBotMsg;
        @BindView(R.id.tv_bot_msg)
        TextView tvBotMsg;
        @BindView(R.id.tv_bot_msg_timestamp)
        TextView tvBotMsgTimeStamp;
        @BindView(R.id.ll_bot_content)
        LinearLayout llBotContent;
        @BindView(R.id.tv_bot_title)
        TextView tvBotTitleMsg;
        @BindView(R.id.tv_bot_subtitle)
        TextView tvBotSubTitleMsg;
        @BindView(R.id.tl_bot_btn_list)
        TableLayout tlBotBtnList;
        @BindView(R.id.iv_bot_image)
        ImageView ivBotImage;

        public BotViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*Intent intent = new Intent(context, MoviesDetailsActivity.class);
            intent.putExtra("position", moviesListResults.get(getPosition()).getId());
            context.startActivity(intent);*/
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_conversation_view, parent, false);
            return new BotViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_conversation_view, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.tvMyMsg.setText(botMsgActivitiesResponseList.get(position).getText());
            myViewHolder.tvMyMsgTimeStamp.setText(botMsgActivitiesResponseList.get(position).getTimestamp());
        } else if (holder instanceof BotViewHolder) {
            final BotViewHolder botViewHolder = (BotViewHolder) holder;

            if(botMsgActivitiesResponseList.get(position).getText()!=null){
                botViewHolder.rlBotMsg.setVisibility(View.VISIBLE);
                botViewHolder.llBotContent.setVisibility(View.GONE);
                botViewHolder.tvBotMsg.setText(botMsgActivitiesResponseList.get(position).getText());
            }else {
                botViewHolder.rlBotMsg.setVisibility(View.GONE);
                botViewHolder.llBotContent.setVisibility(View.VISIBLE);
                botViewHolder.tvBotTitleMsg.setText(botMsgActivitiesResponseList.get(position)
                        .getAttachments().get(0).getContent().getTitle());

                if(botMsgActivitiesResponseList.get(position)
                        .getAttachments().get(0).getContent().getSubtitle()!=null) {
                    botViewHolder.tvBotSubTitleMsg.setText(botMsgActivitiesResponseList.get(position)
                            .getAttachments().get(0).getContent().getSubtitle());
                }

                if(botMsgActivitiesResponseList.get(position).getAttachments().get(0).getContent()
                        .getImages()!=null)

                Glide.with(context)
                        .load(botMsgActivitiesResponseList.get(position).getAttachments().get(0).getContent()
                                .getImages().get(0).getUrl())
                        .asBitmap().fitCenter().error(R.mipmap.ic_launcher)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                botViewHolder.ivBotImage.setImageBitmap(resource);
                            }
                        });

                int btnSize = botMsgActivitiesResponseList.get(position).getAttachments().get(0).getContent().getButtons().size();

                botViewHolder.tlBotBtnList.removeAllViews();
                for (int i = 0; i < btnSize; i++) {
                    TableRow row = (TableRow) LayoutInflater.from(context).inflate(R.layout.bot_content_button, null);
                    TextView dynamicTextView = (TextView) row.findViewById(R.id.tv_bot_content_button_row);

                    dynamicTextView.setText(botMsgActivitiesResponseList.get(position).getAttachments()
                            .get(0).getContent().getButtons().get(i).getTitle());

                    dynamicTextView.setTag(botMsgActivitiesResponseList.get(position).getId() + "$" +
                            botMsgActivitiesResponseList.get(position).getAttachments()
                                    .get(0).getContent().getButtons().get(i).getTitle());

                    dynamicTextView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Log.d("Hello", "" + view.getTag());
                            eventTriggerInterface.eventTrigger("" + view.getTag());
                        }
                    });
                    botViewHolder.tlBotBtnList.addView(row);
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return botMsgActivitiesResponseList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(botMsgActivitiesResponseList.get(position).getFrom().getId().equalsIgnoreCase("Loungie-Production")){
            return 0;
        }else {
            return 1;
        }
    }

    public interface EventTriggerInterface {
        public void eventTrigger(String msg);
    }

    public void updateRecord(List<BotMsgActivitiesResponse> botMsgActivitiesResponseList) {
        this.botMsgActivitiesResponseList = botMsgActivitiesResponseList;
        notifyDataSetChanged();
    }
}
