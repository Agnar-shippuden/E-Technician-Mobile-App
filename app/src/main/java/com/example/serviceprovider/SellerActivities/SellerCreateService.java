package com.example.serviceprovider.SellerActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.CategoriesResponse;
import com.example.serviceprovider.Api.Category;
import com.example.serviceprovider.Api.CreateServiceResponse;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerCreateService extends AppCompatActivity implements Callback<CategoriesResponse>, AdapterView.OnItemSelectedListener, View.OnClickListener {

    Category[] categories;
    Spinner spinner;
    int selected_category_id;
    Button createServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_create_service);

        spinner = findViewById(R.id.serviceCategory);

        Call<CategoriesResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .categories();

        call.enqueue(this);

        spinner.setOnItemSelectedListener(this);

        createServiceBtn = findViewById(R.id.createServiceBtn);
        createServiceBtn.setOnClickListener(this);
    }

    @Override
    public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {

        CategoriesResponse categoriesResponse = response.body();

        if (response.isSuccessful())
        {

            categories = categoriesResponse.getCategories();
            ((Spinner)findViewById(R.id.serviceCategory)).setAdapter(new ArrayAdapter<Category>(SellerCreateService.this, android.R.layout.simple_spinner_dropdown_item, categoriesResponse.getCategories()));

        }
        else
        {
            Toast.makeText(SellerCreateService.this, "Failed to retrieve categories!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<CategoriesResponse> call, Throwable t) {
        Toast.makeText(SellerCreateService.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selected_category_id = categories[i].getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

        String service_name = ((EditText)findViewById(R.id.serviceName)).getText().toString();
        String service_hourly_cost = ((EditText)findViewById(R.id.sellerHourlyCost)).getText().toString();

        Call<CreateServiceResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createService(
                        service_name,
                        service_hourly_cost,
                        selected_category_id,
                        "Bearer " + MySharedPreferences.getAPIToken(this)
                );

        call.enqueue(new Callback<CreateServiceResponse>() {

            @Override
            public void onResponse(Call<CreateServiceResponse> call, Response<CreateServiceResponse> response) {
                if(response.isSuccessful())
                {
                    MySharedPreferences.setService(SellerCreateService.this, response.body().getService());

                    finish();
                }
            }

            @Override
            public void onFailure(Call<CreateServiceResponse> call, Throwable t) {

            }
        });
    }
}