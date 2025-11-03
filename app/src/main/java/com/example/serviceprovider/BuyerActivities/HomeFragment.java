package com.example.serviceprovider.BuyerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.services.ElectricianActivity;

public class HomeFragment extends Fragment
{

    ImageView buyerNotificationBtn ;
    TextView viewAll ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView electricianBtn = view.findViewById(R.id.electrician);
        CardView plumberBtn = view.findViewById(R.id.plumber);
        CardView cleanerBtn = view.findViewById(R.id.cleaner);
        CardView saloonBtn = view.findViewById(R.id.saloon);
        CardView massageBtn = view.findViewById(R.id.massage);
        CardView paintingBtn = view.findViewById(R.id.painting);

        ((Toolbar)view.findViewById(R.id.toolbar)).setTitle(MySharedPreferences.getUser(getActivity()).getName());

        buyerNotificationBtn = view.findViewById(R.id.buyerNotificationBtn);


        // For Electrician
        electricianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ElectricianActivity.class);
                startActivity(intent);
            }
        });

        // For Notification Activity
        buyerNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BuyerNotificationActivity.class);
                startActivity(intent);
            }
        });

        // For Plumber
        plumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ElectricianActivity.class);
                startActivity(intent);
            }
        });

        // For Cleaner
        cleanerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ElectricianActivity.class);
                startActivity(intent);
            }
        });

        // For Saloon
        saloonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ElectricianActivity.class);
                startActivity(intent);
            }
        });

        // For Massage
        massageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ElectricianActivity.class);
                startActivity(intent);
            }
        });

        // For Painting
        paintingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ElectricianActivity.class);
                startActivity(intent);
            }
        });

        viewAll = view.findViewById(R.id.viewALl);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), OrdersFragment.class);
//                startActivity(intent);
//            }
//        });
//
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new OrdersFragment())
                        .commit();
            }
        });



        return view;
    }


}