package com.flower.myaudiomessanger.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.predictions.models.LanguageType;
import com.flower.myaudiomessanger.R;
import com.flower.myaudiomessanger.model.Chat;
import com.flower.myaudiomessanger.utility.AppPreference;
import com.flower.myaudiomessanger.utility.ConnectionDirector;
import com.flower.myaudiomessanger.utility.Constant;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public ConnectionDirector cd;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Message> mChat;
    private String imageurl;
    private String userId;

    public MessageAdapter(Context mContext, List<Message> mChat) {
        this.mChat = mChat;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, final int position) {

        final Message chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());
        if (chat.getTime() != null && !chat.getTime().trim().equals("")) {
            holder.time_tv.setText(holder.convertTime(chat.getTime()));
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public TextView txt_seen;
        public TextView time_tv;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            time_tv = itemView.findViewById(R.id.time_tv);
        }

        public String convertTime(String time) {
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
            String dateString = formatter.format(new Date(Long.parseLong(time)));
            return dateString;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mChat.get(position).getSender().equals(AppPreference.getStringPreference(mContext, Constant.userId))) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}