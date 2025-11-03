package com.example.serviceprovider.Api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.R;

import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Order> orders ;
    Context context ;
    OnAcceptOrderBtnListener orderBtnListener;

    public OrderAdapter(Context context, List<Order> orders, OnAcceptOrderBtnListener orderBtnListener) {
        this.orders = orders;
        this.context = context;
        this.orderBtnListener = orderBtnListener;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.buyer_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.buyerName.setText(order.getBuyerName());
        holder.address.setText(order.getBuyerAddress());
        holder.description.setText(order.getDescription());
        holder.acceptBtn.setOnClickListener(view -> {
            orderBtnListener.passOrder(order);
        });

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
        holder.buyerImage.setImageResource(imageId);
    }

    @Override
    public int getItemCount()
    {
        return orders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView buyerName;
        TextView description;
        TextView address;
        Button acceptBtn;
        ImageView buyerImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            buyerImage = itemView.findViewById(R.id.buyerImage);
            acceptBtn = itemView.findViewById(R.id.acceptBtn);
            buyerName = itemView.findViewById(R.id.buyerName);
            description = itemView.findViewById(R.id.sellerDescription);
            address = itemView.findViewById(R.id.sellerCity);

        }
    }

    public  interface OnAcceptOrderBtnListener
    {
        void passOrder(Order order);
    }
}
