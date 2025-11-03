package com.example.serviceprovider.SellerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceprovider.Api.Order;
import com.example.serviceprovider.R;

import java.util.Locale;

public class ItemContainer extends AppCompatActivity {

    ImageView backBtn ;

    ImageView orderImg ;
    TextView orderDetails;
    TextView orderCategory;
    Button acceptOrderBtn;
//    TextView sellerOrderDate ;
//    RatingBar sellerServiceRating ;
//    TextView sellerPaymentMethod ;

    public static final String KEY_ITEM = "SELECTED_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_container);

        Order order = (Order) getIntent().getParcelableExtra(KEY_ITEM);
        acceptOrderBtn = findViewById(R.id.acceptOrderBtn);
        acceptOrderBtn.setOnClickListener(view -> {
            Intent acceptOrderIntent = new Intent(ItemContainer.this, AcceptOrderActivity.class);
            acceptOrderIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            acceptOrderIntent.putExtra(ItemContainer.KEY_ITEM, order);
            startActivity(acceptOrderIntent);
            finish();
        });

        orderImg = findViewById(R.id.orderImage);
        orderDetails = findViewById(R.id.orderDetails);
        orderCategory = findViewById(R.id.orderCategory);
//        sellerOrderDate = findViewById(R.id.serviceDate);
//        sellerServiceRating = findViewById(R.id.serviceRating);
//        sellerPaymentMethod = findViewById(R.id.paymentMethod);

        backBtn = findViewById(R.id.backIcon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        if(order != null)
        {
            int imageId = R.drawable.profile;
            switch (order.getCategoryName().toLowerCase(Locale.ROOT))
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
            orderImg.setImageResource(imageId);

            orderDetails.setText(order.getDescription());
            orderCategory.setText(order.getCategoryName());
        }



    }
}