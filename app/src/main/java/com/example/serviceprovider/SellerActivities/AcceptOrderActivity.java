package com.example.serviceprovider.SellerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.Order;
import com.example.serviceprovider.Chat.ChatActivity;
import com.example.serviceprovider.R;


public class AcceptOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order);

        Order order = (Order) getIntent().getParcelableExtra(ItemContainer.KEY_ITEM);


        ((TextView)findViewById(R.id.orderDescription)).setText(order.getDescription());
        ((TextView)findViewById(R.id.orderAddress)).setText(order.getBuyerAddress());
        ((TextView)findViewById(R.id.orderName)).setText(order.getBuyerName());
        ((TextView)findViewById(R.id.orderContact)).setText(order.getBuyerContact());

        ((Button)findViewById(R.id.serviceConfirmOrder)).setOnClickListener(view -> {
            Intent intent = new Intent(AcceptOrderActivity.this, StartServiceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(ItemContainer.KEY_ITEM, order);
            startActivity(intent);
            finish();
        });

        ((ImageView)findViewById(R.id.serviceProviderChatIcon)).setOnClickListener(view -> {
            Intent intent = new Intent(AcceptOrderActivity.this, ChatActivity.class);
            startActivity(intent);
        });

    }
}