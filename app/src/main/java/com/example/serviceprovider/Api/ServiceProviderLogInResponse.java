package com.example.serviceprovider.Api;

public class ServiceProviderLogInResponse {
    private String token;
    private ServiceProvider serviceProvider;
    private Service service;
    private String rating;

    public String getToken() {
        return token;
    }
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }
    public Service getService() {
        return service;
    }
    public String getRating() { return rating; }
}

