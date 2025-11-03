package com.example.serviceprovider.BuyerActivities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.Api.BuyerCompletedOrderAdapter;
import com.example.serviceprovider.Api.Order;
import com.example.serviceprovider.Api.OrdersResponse;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersListFragment extends Fragment {

    RecyclerView recyclerView;
    List<Order> orders;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // retrofit ka kam ....
        Call<OrdersResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .userGetCompeletedOrders("Bearer " + MySharedPreferences.getAPIToken(getActivity()));

        call.enqueue(new Callback<OrdersResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response)
            {
                if (response.isSuccessful())
                {
                    orders = response.body().getOrders();
                    Log.v("http", "Size of orders is : " + String.valueOf(orders.size()));
                    recyclerView.setAdapter(new BuyerCompletedOrderAdapter(getActivity(), orders));
                }
                else
                {
                    Toast.makeText(getActivity(), "Failed to get orders list", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t)
            {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}