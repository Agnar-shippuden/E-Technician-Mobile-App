package com.example.serviceprovider.SellerActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.serviceprovider.R;

public class SellerNotificationActivity extends AppCompatActivity
{
    ImageView sellerHomeBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_notification);

        sellerHomeBtn = findViewById(R.id.sellerHome);

        sellerHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(SellerNotificationActivity.this, SellerActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}