package com.example.serviceprovider.Api;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.MySharedPreferences;
import com.example.serviceprovider.R;
import com.example.serviceprovider.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerCompletedOrderAdapter extends RecyclerView.Adapter<BuyerCompletedOrderAdapter.ViewHolder> {

    Context context;
    static Boolean rating_done = false;
    List<Order> orders;

    public BuyerCompletedOrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public BuyerCompletedOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.buyer_completed_orders, parent, false);


        return new BuyerCompletedOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerCompletedOrderAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.serviceProviderName.setText(order.getProviderName());
        holder.serviceDescription.setText(order.getDescription());
        holder.itemAmount.setText(order.getTotal_charges());
        holder.sellerCity.setText(order.getBuyerAddress());


        View view  = holder.itemView;

        view.setOnClickListener(view1 -> {

            if(order.getRating_done().equalsIgnoreCase("no"))
            {
                Dialog rankDialog = new Dialog(view1.getContext());
                rankDialog.setContentView(R.layout.rating_dialog);
                rankDialog.setCancelable(false);
                RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.rating_bar);
                ratingBar.setRating(0);
                ratingBar.setIsIndicator(false);
                ratingBar.setOnRatingBarChangeListener((ratingBar1, v, b) -> {
                    String t = "";

                    if (v <= 1)
                    {
                        t = "Worst";
                    }else if(v > 1  && v <= 2)
                    {
                        t = "Bad";
                    }else if(v > 2  && v <= 3)
                    {
                        t = "Good";
                    }
                    else if(v > 3  && v <= 4)
                    {
                        t = "Best";
                    }else if(v >4  && v <= 5)
                    {
                        t = "Excellent";
                    }

                    ((TextView) rankDialog.findViewById(R.id.rating_textview)).setText(t);

                    Button rating_submit_btn = rankDialog.findViewById(R.id.rating_submit_btn);

                    rating_submit_btn.setOnClickListener(view2 -> {

                        String service_id = order.getService_id();
                        String service_provider_id = order.getService_provider_id();
                        String ratingDescription = ((EditText)rankDialog.findViewById(R.id.ratingDescription)).getText().toString();
                        Call<RatingResponse> call = RetrofitClient.getInstance().getApi()
                                .giveRating(order.getId(),
                                        String.valueOf(v), ratingDescription, service_id, service_provider_id, "Bearer " + MySharedPreferences.getAPIToken(view2.getContext()));

                        call.enqueue(new Callback<RatingResponse>() {
                            @Override
                            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                                if (response.isSuccessful())
                                {
                                    rankDialog.dismiss();
                                    order.setRating_done("yes");
                                }
                            }

                            @Override
                            public void onFailure(Call<RatingResponse> call, Throwable t) {

                            }
                        });

                    });

                });

                rankDialog.show();

            }
            else
            {
                Toast.makeText(view1.getContext(), "Rating is already given", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView serviceProviderName;
        TextView serviceDescription;
        TextView itemAmount;
        TextView sellerCity;
        View whole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            whole = itemView;
            serviceProviderName = itemView.findViewById(R.id.serviceProviderName);
            serviceDescription = itemView.findViewById(R.id.serviceDescription);
            itemAmount = itemView.findViewById(R.id.itemAmount);
            sellerCity = itemView.findViewById(R.id.sellerCity);
        }
    }
}
