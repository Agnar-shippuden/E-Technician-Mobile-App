package com.example.serviceprovider.SellerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.serviceprovider.Api.Service;
import com.example.serviceprovider.Api.ServiceProvider;
import com.example.serviceprovider.Api.ServiceResponse;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.OptionsActivity;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;
import com.example.serviceprovider.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Variables declaration for drawer
    DrawerLayout drawerLayout ;
    NavigationView navigationView ;
    Toolbar toolbar ;
    ImageView sellerNotificationBtn ;
    static ServiceProvider serviceProvider;
    TextView sellerName;
    Button createService ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        // yahan par drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        toolbar = findViewById(R.id.toolbar);

        // for Notification btn
        sellerNotificationBtn = findViewById(R.id.sellerNotificationBtn);

        // toolbar
       setSupportActionBar(toolbar);

        // navigation
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout , toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_BottomBar);
        bottomNavigationView.setSelectedItemId(R.id.homeMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.homeMenu:
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
                        startActivity(new Intent(getApplicationContext(),SellerProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        // create Service activity
        createService = findViewById(R.id.createService);
        createService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createService = new Intent(SellerActivity.this, SellerCreateService.class);
                startActivity(createService);
            }
        });


        Service service = MySharedPreferences.getService(this);
        if(service != null)
        {
            this.setupService(service);
        } else {
            Call<ServiceResponse> serviceResponseCall = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getService("Bearer " + MySharedPreferences.getAPIToken(this));

            serviceResponseCall.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {

                    ServiceResponse serviceResponse = response.body();

                    if(response.isSuccessful())
                    {
                        MySharedPreferences.setService(SellerActivity.this, serviceResponse.getService());
                        setupService(serviceResponse.getService());
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse> call, Throwable t) {

                }
            });

        }

        // for Notification Button Intent
        sellerNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notificaitonIntent = new Intent(SellerActivity.this, SellerNotificationActivity.class);
                startActivity(notificaitonIntent);
            }
        });

        ServiceProvider serviceProvider =  MySharedPreferences.getServiceProvider(this);
        if(serviceProvider != null)
        {
            this.setupServiceProvider(serviceProvider);
        }
        else
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
                        MySharedPreferences.setServiceProvider(SellerActivity.this, infoResponse.getServiceProvider());
                        MySharedPreferences.setUserType(SellerActivity.this, MySharedPreferences.USER_TYPE.Service_Provider);
                        MySharedPreferences.setService(SellerActivity.this, infoResponse.getService());
                        setupServiceProvider(infoResponse.getServiceProvider());
                        setupService(infoResponse.getService());
                    }
                }

                @Override
                public void onFailure(Call<ServiceProviderInfoResponse> call, Throwable throwable)
                {
                    Toast.makeText(SellerActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void  setupService(Service service)
    {
        if(service == null)
        {
            return;
        }

        Button btn = findViewById(R.id.createService);
        btn.setVisibility(View.GONE);


        TextView serviceName = findViewById(R.id.serviceName);
//        TextView serviceAddress = findViewById(R.id.serviceAddress);
        TextView serviceHourlyCost = findViewById(R.id.serviceHourlyCost);

        serviceName.setText(service.getName());
        serviceHourlyCost.setText(service.getHourly_cost() + " p/h");
    }

    public void setupServiceProvider(ServiceProvider serviceProvider)
    {
        if(serviceProvider == null)
        {
            return;
        }

        TextView name = findViewById(R.id.sellerName);
        name.setText(serviceProvider.getName());
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                break;

            case R.id.nav_logout:
            {
                Call<String> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .sellerLogout("Bearer " + MySharedPreferences.getStringValue(SellerActivity.this, SharedPref.USER_TOKEN, ""));

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response)
                    {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(SellerActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable)
                    {
                        Toast.makeText(SellerActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                MySharedPreferences.setStringValue(SellerActivity.this, SharedPref.USER_TOKEN, "");
                MySharedPreferences.setBooleanValue(SellerActivity.this, SharedPref.LOGGED_IN, false);
                MySharedPreferences.setStringValue(SellerActivity.this, SharedPref.LOGIN_TYPE,"");
                MySharedPreferences.setServiceProvider(SellerActivity.this, null);
                MySharedPreferences.setService(SellerActivity.this, null);

                Intent loginBtnIntent = new Intent(SellerActivity.this, OptionsActivity.class);
                startActivity(loginBtnIntent);
                finish();

            }
            break;


            case R.id.nav_profile:
                Intent profileIntent = new Intent( SellerActivity.this, SellerProfileActivity.class);
                startActivity(profileIntent);
                break;

        }

        return true;
    }
}