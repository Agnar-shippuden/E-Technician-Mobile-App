package com.example.serviceprovider.SellerActivities;

import com.example.serviceprovider.Api.Service;
import com.example.serviceprovider.Api.ServiceProvider;

public class ServiceProviderInfoResponse {

    ServiceProvider serviceProvider;
    Service service;
    String rating;
    
    public ServiceProvider getServiceProvider() { return serviceProvider; }
    public Service getService() { return service; }
    public String getRating() { return rating; }
}
