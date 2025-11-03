package com.example.serviceprovider.BuyerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.serviceprovider.Api.User;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;

public class ProfileFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView profileSettingBtn = view.findViewById(R.id.userSetting);
        // For Electrician
        profileSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(profileIntent);
            }
        });

        User user = MySharedPreferences.getUser(getContext());

        TextView sellerName = view.findViewById(R.id.sellerName);
        sellerName.setText(user.getName());

        if(user.getEmail() != null)
        {
            TextView userEmail = view.findViewById(R.id.userEmail);
            userEmail.setText(user.getEmail());
        }

        if(user.getAddress() != null)
        {
            TextView userAddress = view.findViewById(R.id.userAddress);
            userAddress.setText(user.getAddress());
        }

        if(user.getContact() != null)
        {
            TextView userContact = view.findViewById(R.id.userPhone);
            userContact.setText(user.getContact());
        }


        return view;

    }


}