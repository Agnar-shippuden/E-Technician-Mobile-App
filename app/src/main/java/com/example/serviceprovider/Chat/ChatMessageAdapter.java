package com.example.serviceprovider.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;

import java.util.List;

class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    Context context;
    List<ChatActivity.ChatMessage> messageList;

    public ChatMessageAdapter(Context context, List<ChatActivity.ChatMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        switch (viewType)
        {
            case 0:
                view = inflater.inflate(R.layout.item_message_received, parent, false);
                break;
            case 1: // We are sender
                view = inflater.inflate(R.layout.item_message_sent, parent, false);
                break;

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(messageList.get(position).getUser_name());
        holder.message.setText(messageList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        switch (MySharedPreferences.getUserType(context))
        {
            case Service_Provider:
                if(messageList.get(position).getUser_name().equals(MySharedPreferences.getServiceProvider(context).getName()))
                    type = 1;
                break;
            case Service_Comsumer:
                if(messageList.get(position).getUser_name().equals(MySharedPreferences.getUser(context).getName()))
                    type = 1;
                break;
        }
        return type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
        }
    }
}
