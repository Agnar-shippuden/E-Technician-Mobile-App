package com.example.serviceprovider.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellerCreateServiceResponse {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("hourly_cost")
    @Expose
    private String hourly_cost;

    @SerializedName("category_id")
    @Expose
    private String category_id;

    public String getName() {
        return name;
    }

    public String getHourly_cost() {
        return hourly_cost;
    }

    public String getCategory_id() {
        return category_id;
    }
}
