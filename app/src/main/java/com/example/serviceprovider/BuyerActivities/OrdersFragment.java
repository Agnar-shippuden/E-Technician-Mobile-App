package com.example.serviceprovider.BuyerActivities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.Api.Service;
import com.example.serviceprovider.Api.ServicesAdapter;
import com.example.serviceprovider.Api.ServicesResponse;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersFragment extends Fragment
{
    RecyclerView recyclerView;
    List<Service> services ;
    Button requestBtn ;
    ImageView image;
    EditText searchProviders ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_orders, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);



        recyclerView = view.findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // retrofit ka kam ....
        Call<ServicesResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .services();

        call.enqueue(new Callback<ServicesResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ServicesResponse> call, Response<ServicesResponse> response)
            {
                if (response.isSuccessful())
                {
                    services = response.body().getServices();
                    recyclerView.setAdapter(new ServicesAdapter(getActivity(), services, service -> {
                        Intent i = new Intent(requireActivity(), BuyerItemContainer.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(BuyerItemContainer.KEY_ITEM, service);
                        startActivity(i);
                    }));
                }
                else
                {
                    Toast.makeText(getActivity(), "Failed to get service list", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServicesResponse> call, Throwable t)
            {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}