package com.example.serviceprovider.SellerActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.ServiceProviderUpdateResponse;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerProfileSetting extends AppCompatActivity implements View.OnClickListener {

    EditText company_name;
    EditText mobile;
    EditText phone;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile_setting);

        Button updateBtn = findViewById(R.id.sellerProfileSettingUpdateBtn);
        updateBtn.setOnClickListener(this);

        company_name = findViewById(R.id.sellerProfileSettingCompany);
        mobile = findViewById(R.id.sellerProfileSettingMobile);
        phone = findViewById(R.id.sellerProfileSettingPhone);
        address = findViewById(R.id.sellerProfileSettingAddress);

    }

    @Override
    public void onClick(View view) {

        Call<ServiceProviderUpdateResponse> call = RetrofitClient.getInstance().getApi()
                .sellerUpdate(
                        company_name.getText().toString(),
                        phone.getText().toString(),
                        mobile.getText().toString(),
                        address.getText().toString(),
                        "Bearer " + MySharedPreferences.getAPIToken(this)
                        );

        call.enqueue(new Callback<ServiceProviderUpdateResponse>() {
            @Override
            public void onResponse(Call<ServiceProviderUpdateResponse> call, Response<ServiceProviderUpdateResponse> response) {

                if(response.isSuccessful())
                {
                    MySharedPreferences.setServiceProvider(SellerProfileSetting.this, response.body().getServiceProvider());
                    MySharedPreferences.setUserType(SellerProfileSetting.this, MySharedPreferences.USER_TYPE.Service_Provider);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ServiceProviderUpdateResponse> call, Throwable t) {

            }
        });

    }
}