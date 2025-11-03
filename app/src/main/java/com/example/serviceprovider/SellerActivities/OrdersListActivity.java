package com.example.serviceprovider.SellerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.Api.Order;
import com.example.serviceprovider.Api.OrdersResponse;
import com.example.serviceprovider.Api.ServiceProviderCompletedOrderAdapter;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        recyclerView = findViewById(R.id.orderRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrdersListActivity.this));

        Call<OrdersResponse> call = RetrofitClient.getInstance().getApi()
                .sellerGetCompeletedOrders("Bearer " + MySharedPreferences.getAPIToken(OrdersListActivity.this));

        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    orders = response.body().getOrders();
                    recyclerView.setAdapter(new ServiceProviderCompletedOrderAdapter(OrdersListActivity.this, orders));
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_BottomBar);
        bottomNavigationView.setSelectedItemId(R.id.listMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeMenu:
                        startActivity(new Intent(getApplicationContext(), SellerActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.orderMenu:
                        startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.listMenu:
                        return true;
                    case R.id.profileMenu:
                        startActivity(new Intent(getApplicationContext(), SellerProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });


    }
}