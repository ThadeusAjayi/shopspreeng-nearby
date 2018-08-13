package com.shopspreeng.nearby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Thadeus-APMIS on 8/12/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context mContext;
    private ArrayList<MessageItem> allMessages;

    MessageAdapter (Context context) {
        mContext = context;
        allMessages = new ArrayList<>();
    }

    public void addNewMessage(MessageItem messageItem) {
        allMessages.add(messageItem);
        notifyDataSetChanged();
    }


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MessageItem currentMessage = allMessages.get(position);

        holder.messageObject.setText(currentMessage.getMessage());
        holder.messageTime.setText(DateUtils.getRelativeTimeSpanString(currentMessage.getTime(), Calendar.getInstance().getTimeInMillis(), 0));

    }

    @Override
    public int getItemCount() {
        return allMessages != null ? allMessages.size() : 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageObject, messageTime;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageObject = itemView.findViewById(R.id.message_object);
            messageTime = itemView.findViewById(R.id.message_time);
        }
    }
}
