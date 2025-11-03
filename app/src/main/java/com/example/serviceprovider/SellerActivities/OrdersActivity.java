package com.example.serviceprovider.SellerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.Api.Order;
import com.example.serviceprovider.Api.OrderAdapter;
import com.example.serviceprovider.Api.OrdersResponse;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    List<Order> orders ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.orderRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));

        Call<OrdersResponse> call = RetrofitClient.getInstance().getApi()
                .sellerGetPeningOrders("Bearer " + MySharedPreferences.getAPIToken(OrdersActivity.this));

        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful())
                {
                    orders = response.body().getOrders();
                    if(orders.size() > 0)
                    {
                        recyclerView.setAdapter(new OrderAdapter( OrdersActivity.this, orders, order -> {
                            Intent i = new Intent(OrdersActivity.this, ItemContainer.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra(ItemContainer.KEY_ITEM, order);
                            startActivity(i);
                        }));
                    }
                    else
                    {
                        Toast.makeText(OrdersActivity.this, "Currently there are no orders for you.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_BottomBar);
        bottomNavigationView.setSelectedItemId(R.id.orderMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.homeMenu:
                        startActivity(new Intent(getApplicationContext(), SellerActivity.class));
                        overridePendingTransition(0,0) ;
                        return true;

                    case R.id.orderMenu:
                        return true;
                    case R.id.listMenu:
                        startActivity(new Intent(getApplicationContext(), OrdersListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profileMenu:
                        startActivity(new Intent(getApplicationContext(), SellerProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });


    }
}