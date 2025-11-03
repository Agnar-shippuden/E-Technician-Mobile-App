package com.example.serviceprovider.BuyerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.UserUpdateResponse;
import com.example.serviceprovider.Location.LocationActivity;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.OptionsActivity;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;
import com.example.serviceprovider.SharedPref;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userName , userNumber, userAddress ;
    Button logoutButton , updateProfileBtn;
    MySharedPreferences mySharedPreferences ;
    ImageView addressIcon ;
    TextView addressText ;
    int PLACE_PICKER_REQUEST = 1 ;
    public static final int RC_LOCATION = 12923;
    public static final String KEY_LOCATION = "location.settings";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        addressIcon = findViewById(R.id.buyerLocationIcon);
        addressText = findViewById(R.id.buyerLocationText);

        addressIcon.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, LocationActivity.class);
            startActivityForResult(intent, RC_LOCATION);
        });

        

        logoutButton = findViewById(R.id.userLogout);

        userName = findViewById(R.id.changeUserName);
        userNumber = findViewById(R.id.changeUserNumber);
        userAddress = findViewById(R.id.changeUserAddress);

        userAddress.setText(MySharedPreferences.getUser(this).getAddress());
        userName.setText(MySharedPreferences.getUser(this).getName());
        userNumber.setText(MySharedPreferences.getUser(this).getContact());

        updateProfileBtn = findViewById(R.id.userAccountUpdateBtn);

        updateProfileBtn.setOnClickListener(this);

        logoutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.userAccountUpdateBtn:
                updateUserAccount();
                break;

            case R.id.userLogout:
                userLogout();
                break;
        }

    }

    private void userLogout()
    {
        Call<String> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogout("Bearer " + MySharedPreferences.getStringValue(SettingsActivity.this, SharedPref.USER_TOKEN, ""));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (response.isSuccessful())
                {
                    Toast.makeText(SettingsActivity.this, response.body(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable)
            {
                Toast.makeText(SettingsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MySharedPreferences.setStringValue(SettingsActivity.this, SharedPref.USER_TOKEN, "");
        MySharedPreferences.setBooleanValue(SettingsActivity.this, SharedPref.LOGGED_IN,false);
        MySharedPreferences.setStringValue(SettingsActivity.this, SharedPref.LOGIN_TYPE,"");
        MySharedPreferences.setUser(SettingsActivity.this, null);
        MySharedPreferences.setUserType(SettingsActivity.this, MySharedPreferences.USER_TYPE.Service_Comsumer);


        Intent logoutBtnIntent = new Intent(SettingsActivity.this, OptionsActivity.class);
        startActivity(logoutBtnIntent);
        finish();
    }

    private void updateUserAccount() {

        String name = userName.getText().toString();
        String contact = userNumber.getText().toString();
        String address = userAddress.getText().toString();

        if(!name.isEmpty() && !contact.isEmpty() && !address.isEmpty())
        {
            String token = MySharedPreferences.getAPIToken(SettingsActivity.this);
            Call<UserUpdateResponse> call = RetrofitClient.getInstance().getApi()
                    .userUpdate(name, address, contact, "Bearer " + token);

            call.enqueue(new Callback<UserUpdateResponse>() {
                @Override
                public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response)
                {

                    UserUpdateResponse updateResponse = response.body();
                    if (response.isSuccessful())
                    {
                        Toast.makeText(SettingsActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        MySharedPreferences.setUser(SettingsActivity.this, updateResponse.getUser());
                        MySharedPreferences.setUserType(SettingsActivity.this, MySharedPreferences.USER_TYPE.Service_Comsumer);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UserUpdateResponse> call, Throwable throwable)
                {
                    Toast.makeText(SettingsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            if (name.isEmpty())
            {
                userName.setError("Please Provide a name");
            }

            if (contact.isEmpty())
            {
                userNumber.setError("Please Provide Mobile Number");
            }

            if (address.isEmpty())
            {
                userAddress.setError("Please Provide Address");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_LOCATION && resultCode == RESULT_OK) {
            String location = Objects.requireNonNull(data).getStringExtra(KEY_LOCATION);
            if(location != null)
                userAddress.setText(location);
        }
    }
}