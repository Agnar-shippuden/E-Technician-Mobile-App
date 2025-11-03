package com.example.serviceprovider.BuyerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.Service;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerItemContainer extends AppCompatActivity {

    ImageView backBtn;

    ImageView buyerServiceImg;
    TextView buyerServiceDetail;
    TextView buyerServiceCategory;
    TextView buyerOrderDate;
    RatingBar buyerServiceRating;
    RadioButton paymentCash, paymentOnline;

    Button requestOrderBtn ;

    public static final String KEY_ITEM = "SELECTED_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_item_container);

        Service item = (Service) getIntent().getParcelableExtra(KEY_ITEM);

        buyerServiceImg = findViewById(R.id.orderImage);
        buyerServiceDetail = findViewById(R.id.serviceDetails);
        buyerServiceCategory = findViewById(R.id.serviceCategory);
        buyerOrderDate = findViewById(R.id.serviceDate);
        buyerServiceRating = findViewById(R.id.serviceRating);
        paymentCash = findViewById(R.id.paymentCash);
        paymentOnline = findViewById(R.id.paymentOnline);


        backBtn = findViewById(R.id.backIcon);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent backBtnIntent = new Intent(BuyerItemContainer.this, )
//            }
//        });

        requestOrderBtn = findViewById(R.id.requestOrderBtn);
        requestOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent requestOrderIntent = new Intent(BuyerItemContainer.this, RequestOrder.class);
                requestOrderIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                requestOrderIntent.putExtra(BuyerItemContainer.KEY_ITEM, item);
                startActivity(requestOrderIntent);
                finish();
            }
        });

        if(item != null) {
            buyerServiceDetail.setText(item.getName());
            buyerServiceCategory.setText(item.getCategoryName());

            int imageId = R.drawable.profile;
            switch (item.getCategoryName().toLowerCase(Locale.ROOT))
            {
                case "plumber":
                    imageId = R.drawable.plumber;
                    break;
                case "electrician":
                    imageId = R.drawable.electrician;
                    break;
                case "cleaner":
                    imageId = R.drawable.bucket;
                    break;
                case "salon":
                    imageId = R.drawable.salon;
                    break;
                case "massage":
                    imageId = R.drawable.massage;
                    break;
                case "painting":
                    imageId = R.drawable.paintroll;
                    break;
            }
            buyerServiceImg.setImageResource(imageId);


            Call<Float> call = RetrofitClient.getInstance().getApi().getServiceAvgRating(item.getId());
            call.enqueue(new Callback<Float>() {
                @Override
                public void onResponse(Call<Float> call, Response<Float> response) {
                    if(response.isSuccessful())
                    {
                        buyerServiceRating.setRating(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Float> call, Throwable t) {

                }
            });
        }
    }
}