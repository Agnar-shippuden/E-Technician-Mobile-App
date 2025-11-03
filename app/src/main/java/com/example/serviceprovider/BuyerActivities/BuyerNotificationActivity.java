package com.example.serviceprovider.BuyerActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.serviceprovider.R;

public class BuyerNotificationActivity extends AppCompatActivity {

    ImageView buyerHomeBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_notification);

        buyerHomeBtn = findViewById(R.id.buyerHome);

        buyerHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(BuyerNotificationActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

    }
}