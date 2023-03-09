package com.ap.bot.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ap.bot.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MessageListAdapter extends BaseAdapter {

    private final List<String> chatMessages;
    private Activity context;

    public  MessageListAdapter(Activity context, List<String> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @Override
    public int getCount() {
        if (chatMessages != null) {
            return chatMessages.size();
        } else {
            return 0;
        }
    }

    @Override
    public String getItem(int position) {
        if (chatMessages != null) {
            return chatMessages.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String chatMessage = getItem(position);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.list_item_message, parent,false);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtMessage.setText(chatMessage);

        return convertView;
    }

    public void add(String message) {
        chatMessages.add(message);
    }

    public void add(List<String> messages) {
        chatMessages.addAll(messages);
    }


    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.bubbleMessageContainer = (LinearLayout)v.findViewById(R.id.bubbleMessageContainer);
        holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        Log.d("viewHolder",holder.txtMessage.toString());

        return holder;
    }


    private static class ViewHolder {
        public LinearLayout bubbleMessageContainer;
        public TextView txtMessage;
    }
}
