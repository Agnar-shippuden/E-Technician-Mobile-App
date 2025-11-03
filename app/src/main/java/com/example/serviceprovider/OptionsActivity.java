package com.example.serviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.BuyerActivities.BuyerLoginActivity;
import com.example.serviceprovider.BuyerActivities.MainActivity;
import com.example.serviceprovider.SellerActivities.SellerActivity;
import com.example.serviceprovider.SellerActivities.SellerLoginActivity;

public class OptionsActivity extends AppCompatActivity {

    Button sellerBtn , buyerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Log.v("TEST", "1");

        boolean already_logged_in  = MySharedPreferences.getBooleanValue(OptionsActivity.this, SharedPref.LOGGED_IN,false);
        if(already_logged_in)
        {
            Log.v("TEST", "2");

            String loginType = MySharedPreferences.getStringValue(OptionsActivity.this, SharedPref.LOGIN_TYPE, "");
            if(loginType.equalsIgnoreCase("USER"))
            {
                Intent loginBtnIntent = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(loginBtnIntent);
                finish();

            }
            else if(loginType.equalsIgnoreCase("SERVICE"))
            {
                Intent loginBtnIntent = new Intent(OptionsActivity.this, SellerActivity.class);
                startActivity(loginBtnIntent);
                finish();
            }
        }

        sellerBtn = findViewById(R.id.seller);
        buyerBtn = findViewById(R.id.buyer);

        sellerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent serviceBtnIntent = new Intent(OptionsActivity.this, SellerLoginActivity.class);
                startActivity(serviceBtnIntent);
                finish();
            }
        });

        buyerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent serviceBtnIntent = new Intent(OptionsActivity.this, BuyerLoginActivity.class);
                startActivity(serviceBtnIntent);
                finish();
            }
        });


    }
}