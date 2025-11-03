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

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder>
{
    List<Service> services ;
    Context context;
    OnRequestOrderBtnListener orderBtnListener;


    public ServicesAdapter(Context context, List<Service> services, OnRequestOrderBtnListener listener) {
        this.context = context;
        this.services = services;
        this.orderBtnListener = listener;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.services_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position)
    {
        Service service = services.get(position);

        holder.sellerName.setText(service.getProviderName());
        holder.sellerHourlyCost.setText(service.getHourly_cost() + " p/h");
        holder.serviceName.setText(service.getName());
        int imageId = R.drawable.profile;
        switch (service.getCategoryName().toLowerCase(Locale.ROOT))
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
        holder.image.setImageResource(imageId);

        holder.requestBtn.setOnClickListener(view -> {
            orderBtnListener.passService(service);
        });

    }



    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView sellerName;
        TextView serviceName;

        TextView sellerHourlyCost;
        Button requestBtn;

        ImageView image;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.buyerImage);
            sellerName = itemView.findViewById(R.id.sellerName);
            serviceName = itemView.findViewById(R.id.serviceTitle);
            sellerHourlyCost = itemView.findViewById(R.id.sellerHourlyCost);
            requestBtn = itemView.findViewById(R.id.requestBtn);

        }
    }

    public  interface OnRequestOrderBtnListener
    {
        void passService(Service service);
    }
}
