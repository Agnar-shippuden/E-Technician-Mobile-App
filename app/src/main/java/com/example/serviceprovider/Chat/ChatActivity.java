package com.example.serviceprovider.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.BuyerActivities.RequestOrder;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.SocketClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    ImageView backBtn;
    ChatMessageAdapter adapter;
    public class ChatMessage
    {
        String user_name;
        String message;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }


        public ChatMessage(String user_name, String message) {
            this.user_name = user_name;
            this.message = message;
        }
    }

    List<ChatMessage> messageList;
    RecyclerView Recycler_Chatbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        initCometChat();
        initViews();

        messageList = new ArrayList<ChatMessage>();

        backBtn = findViewById(R.id.backIcon);
        backBtn.setOnClickListener(view -> {
            Intent backIntent = new Intent(ChatActivity.this, RequestOrder.class);
            startActivity(backIntent);
            finish();
        });


        Recycler_Chatbox = findViewById(R.id.Recycler_Chatbox);
        Recycler_Chatbox.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        Recycler_Chatbox.addItemDecoration(new DividerItemDecoration(ChatActivity.this, DividerItemDecoration.HORIZONTAL));
        adapter =  new ChatMessageAdapter(ChatActivity.this, messageList);
        Recycler_Chatbox.setAdapter(adapter);

        SocketClient.getInstance().getSocket().on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String user_name;
                        String message;
                        try {
                            user_name = data.getString("user_name");
                            message = data.getString("message");
                        } catch (JSONException e) {
                            return;
                        }
//                        // add the message to view
                        addMessage(user_name, message);
                    }
                });
            }
        });

    }



    private void addMessage(String user_name,String message) {
        messageList.add(new ChatMessage(user_name, message));
        adapter.notifyItemInserted(messageList.size() - 1);
    }

    private void initViews() {
        ImageView submitButton = findViewById(R.id.sendMessage);
        EditText chat = findViewById(R.id.chat);

        submitButton.setOnClickListener(view -> {
            if(!chat.getText().toString().isEmpty())
            {
                String user_name = "";
                switch (MySharedPreferences.getUserType(view.getContext()))
                {
                    case Service_Provider:
                        user_name = MySharedPreferences.getServiceProvider(view.getContext()).getName();
                        break;
                    case Service_Comsumer:
                        user_name = MySharedPreferences.getUser(view.getContext()).getName();
                        break;
                }
                SocketClient.getInstance().SendMessage(user_name, chat.getText().toString());
            }
        });


    }


}