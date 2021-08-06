package com.flower.myaudiomessanger.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Users;
import com.flower.myaudiomessanger.MainActivity;
import com.flower.myaudiomessanger.R;
import com.flower.myaudiomessanger.ui.MessageActivity;
import com.flower.myaudiomessanger.utility.Constant;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<Users> mUsers;
    private boolean ischat;
    private OnItemClick onItemClick;

    Typeface MR,MRR;
    String theLastMessage;

    public UserAdapter(Context mContext,
                       OnItemClick onItemClick,
                       List<Users> mUsers,
                       boolean ischat){
        this.onItemClick = onItemClick;
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    public UserAdapter(Context mContext,
                       List<Users> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Users user = mUsers.get(position);
        holder.username.setTypeface(MR);
        holder.last_msg.setTypeface(MRR);

        holder.username.setText(user.getName());

            /*if (user.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.GONE);
            }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra(Constant.chatUserId, user.getId());
                intent.putExtra(Constant.chatUserName, user.getName());
                intent.putExtra(Constant.chatUserEmail, user.getEmail());
                intent.putExtra(Constant.chatUserLanguage, user.getLanguage());
                intent.putExtra(Constant.chatUserLanguageCode, user.getLanguageCode());
                intent.putExtra(Constant.chatUserLanguageVoice, user.getVoice());
                intent.putExtra(Constant.chatUserLanguageEngine, user.getEngine());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;
        private TextView last_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            last_msg = itemView.findViewById(R.id.last_msg);
        }
    }

}
