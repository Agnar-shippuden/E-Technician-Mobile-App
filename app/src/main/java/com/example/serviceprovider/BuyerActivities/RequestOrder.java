package com.example.serviceprovider.BuyerActivities;

import static com.example.serviceprovider.BuyerActivities.BuyerItemContainer.KEY_ITEM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.OrderResponse;
import com.example.serviceprovider.Api.Service;
import com.example.serviceprovider.Chat.ChatActivity;
import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.Payment.PaymentActivity;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestOrder extends AppCompatActivity implements View.OnClickListener {

    ImageView backBtn, buyerChatBtn, buyerOnlinePayment ;
    Button confirmOrderBtn;
    EditText description;
    EditText address;
    TextView contact;
    TextView name;
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_order);

        service = (Service) getIntent().getParcelableExtra(KEY_ITEM);

        confirmOrderBtn = findViewById(R.id.buyerConfirmOrder);
        description = findViewById(R.id.serviceDescription);
        address = findViewById(R.id.buyerCompleteAddress);
        contact = findViewById(R.id.buyerContact);
        name = findViewById(R.id.buyerName);

        address.setText(MySharedPreferences.getUser(this).getAddress());
        contact.setText(MySharedPreferences.getUser(this).getContact());
        name.setText(MySharedPreferences.getUser(this).getName());

        confirmOrderBtn.setOnClickListener(this);

        backBtn = findViewById(R.id.backIcon);
        backBtn.setOnClickListener(view -> {
                finish();
        });

        buyerChatBtn = findViewById(R.id.buyerChatIcon);
        buyerChatBtn.setOnClickListener(view -> {
            Intent intent = new Intent(RequestOrder.this, ChatActivity.class);
            startActivity(intent);
        });

        buyerOnlinePayment = findViewById(R.id.BuyerOnlinePayment);
        buyerOnlinePayment.setOnClickListener(view -> {
            Intent buyerOnlineIntent = new Intent(RequestOrder.this, PaymentActivity.class);
            buyerOnlineIntent.putExtra("Price", service.getHourly_cost().toString());
            startActivity(buyerOnlineIntent);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 0 && resultCode == RESULT_OK) {
            // Get String data from Intent
            String ResponseCode = data.getStringExtra("pp_ResponseCode");
            System.out.println("DateFn: ResponseCode:" + ResponseCode);
            if(ResponseCode.equals("000")) {
                Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        Call<OrderResponse> call = RetrofitClient.getInstance().getApi()
                .createOrder(
                        service.getId(),
                        service.getService_provider_id(),
                        description.getText().toString(),
                        "Bearer " + MySharedPreferences.getAPIToken(this)
                );

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(RequestOrder.this, "Order is placed. Please Wait while your orders is picked by the service provider", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
    }
}