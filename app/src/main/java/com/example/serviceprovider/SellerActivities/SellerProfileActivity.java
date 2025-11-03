package com.example.serviceprovider.SellerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.ServiceProvider;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerProfileActivity extends AppCompatActivity {

    ImageView sellerProfileSetting ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_BottomBar);
        bottomNavigationView.setSelectedItemId(R.id.profileMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.homeMenu:
                        startActivity(new Intent(getApplicationContext(),SellerActivity.class));
                        overridePendingTransition(0,0) ;
                        return true;

                    case R.id.orderMenu:
                        startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.listMenu:
                        startActivity(new Intent(getApplicationContext(), OrdersListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profileMenu:
                        return true;

                }
                return false;
            }
        });

        // Profile Setting
        sellerProfileSetting = findViewById(R.id.sellerProfileSetting);
        sellerProfileSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sellerProfileIntent = new Intent(SellerProfileActivity.this, SellerProfileSetting.class);
                startActivity(sellerProfileIntent);
            }
        });

        // ended Profile Setting

        Call<String> call2 = RetrofitClient.getInstance().getApi().getTotalEarnings("Bearer " + MySharedPreferences.getAPIToken(SellerProfileActivity.this));
        call2.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    ((TextView)findViewById(R.id.totalEarnings)).setText(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        ServiceProvider serviceProvider = MySharedPreferences.getServiceProvider(this);
        if(serviceProvider == null)
        {
            Call<ServiceProviderInfoResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .sellerInfo("Bearer " + MySharedPreferences.getAPIToken(this));

            call.enqueue(new Callback<ServiceProviderInfoResponse>() {
                @Override
                public void onResponse(Call<ServiceProviderInfoResponse> call, Response<ServiceProviderInfoResponse> response)
                {

                    ServiceProviderInfoResponse infoResponse = response.body();
                    if (response.isSuccessful())
                    {
                        MySharedPreferences.setServiceProvider(SellerProfileActivity.this, infoResponse.getServiceProvider());
                        MySharedPreferences.setUserType(SellerProfileActivity.this, MySharedPreferences.USER_TYPE.Service_Provider);

                        MySharedPreferences.setService(SellerProfileActivity.this, infoResponse.getService());
                        setupServiceProvider(infoResponse.getServiceProvider());
                    }
                }

                @Override
                public void onFailure(Call<ServiceProviderInfoResponse> call, Throwable throwable)
                {
                    Toast.makeText(SellerProfileActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            setupServiceProvider(serviceProvider);
        }

    }

    void setupServiceProvider(ServiceProvider serviceProvider)
    {
        if(serviceProvider == null)
        {
            return;
        }



        ((TextView)findViewById(R.id.sellerProfileEmail)).setText(serviceProvider.getEmail());
        ((TextView)findViewById(R.id.sellerProfilePhoneNumber)).setText(serviceProvider.getContact_phone());
        ((TextView)findViewById(R.id.sellerProfileMobileNumber)).setText(serviceProvider.getContact_mobile());
        ((TextView)findViewById(R.id.sellerProfileAddress)).setText(serviceProvider.getAddress());
        ((TextView)findViewById(R.id.sellerProfileCompanyName)).setText(serviceProvider.getCompany());
        ((TextView)findViewById(R.id.sellerName)).setText(serviceProvider.getName());
//        ((TextView)findViewById(R.id.sellerProfileCategory)).setText(serviceProvider);
    }

}