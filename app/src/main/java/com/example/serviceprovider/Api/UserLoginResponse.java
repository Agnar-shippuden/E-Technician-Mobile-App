package com.example.serviceprovider.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginResponse {
    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("token")
    @Expose
    private String token;

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
