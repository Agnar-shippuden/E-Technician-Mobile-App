package com.example.serviceprovider.Api;

public class ServiceProviderRegisterResponse {
    private String token;
    private ServiceProvider serviceProvider;
    private String rating;

    public String getRating() {
        return rating;
    }


    public String getToken() {
        return token;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }
}


