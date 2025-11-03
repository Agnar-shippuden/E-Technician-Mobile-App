package com.example.serviceprovider.Api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceprovider.R;

import java.util.List;

public class ServiceProviderCompletedOrderAdapter extends RecyclerView.Adapter<ServiceProviderCompletedOrderAdapter.ViewHolder> {

    Context context;
    List<Order> orders;

    public ServiceProviderCompletedOrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ServiceProviderCompletedOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.seller_completed_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderCompletedOrderAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.serviceBuyerName.setText(order.getBuyerName());
        holder.serviceDescription.setText(order.getDescription());
        holder.itemAmount.setText(order.getTotal_charges());
        holder.sellerCity.setText(order.getBuyerAddress());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView serviceBuyerName;
        TextView serviceDescription;
        TextView itemAmount;
        TextView sellerCity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceBuyerName = itemView.findViewById(R.id.serviceBuyerName);
            serviceDescription = itemView.findViewById(R.id.serviceDescription);
            itemAmount = itemView.findViewById(R.id.itemAmount);
            sellerCity = itemView.findViewById(R.id.sellerCity);
        }
    }
}
