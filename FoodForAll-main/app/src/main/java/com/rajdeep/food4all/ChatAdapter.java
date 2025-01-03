package com.rajdeep.food4all;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// ChatAdapter.java
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {
    private List<Message> messages;

    public ChatAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageText.setText(message.getText());

        // Set message alignment and background
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                holder.messageText.getLayoutParams();

        if (message.isUser()) {
            params.gravity = Gravity.END;
            holder.messageText.setBackgroundResource(R.drawable.bg_message_user);
        } else {
            params.gravity = Gravity.START;
            holder.messageText.setBackgroundResource(R.drawable.bg_message_boat);
        }

        holder.messageText.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        MessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}