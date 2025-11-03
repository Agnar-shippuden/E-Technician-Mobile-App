package com.example.serviceprovider.Api;

import com.example.serviceprovider.SellerActivities.ServiceProviderInfoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Api {

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("service/register")
    Call<ServiceProviderRegisterResponse> serviceProviderRegister(
            @Field("name") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String confirmPassword
    );

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("service/login")
    Call<ServiceProviderLogInResponse> sellerLogin(@Field("email") String email, @Field("password") String password);

    @Headers({"Accept: application/json"})
    @GET("service/info")
    Call<ServiceProviderInfoResponse> sellerInfo(@Header("Authorization") String token);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @PUT("service/update")
    Call<ServiceProviderUpdateResponse> sellerUpdate(
            @Field("company") String company,
            @Field("contact_phone") String phone,
            @Field("contact_mobile") String mobile,
            @Field("address") String address,
            @Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @POST("service/logout")
    Call<String> sellerLogout(@Header("Authorization") String token);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("service/service")
    Call<CreateServiceResponse> createService(
            @Field("name") String name,
            @Field("hourly_cost") String hourly_cost,
            @Field("category_id") int category_id,
            @Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("service/service")
    Call<ServiceResponse> getService(
            @Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("service/totalEarnings")
    Call<String> getTotalEarnings(
            @Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("service/pendingOrders")
    Call<OrdersResponse> sellerGetPeningOrders(
            @Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("service/completedOrders")
    Call<OrdersResponse> sellerGetCompeletedOrders(
            @Header("Authorization") String token);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("service/orderDone")
    Call<OrderResponse> orderDone(
            @Field("order_id") String order_id,
            @Field("total_charges") String total_charges,
            @Field("extra_work") String extra_work,
            @Field("extra_work_charges") String extra_work_charges,
            @Field("time_taken") String time_taken,
            @Header("Authorization") String token);


    @Headers({"Accept: application/json"})
    @POST("user/logout")
    Call<String> userLogout(@Header("Authorization") String token);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("user/register")
    Call<UserRegisterResponse> userRegister(
            @Field("name") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String confirmPassword
    );

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("user/login")
    Call<UserLoginResponse> userLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @PUT("user/update")
    Call<UserUpdateResponse> userUpdate(@Field("name") String name, @Field("address") String address, @Field("contact") String contact, @Header("Authorization") String token);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("user/order")
    Call<OrderResponse> createOrder(
            @Field("service_id") String service_id,
            @Field("service_provider_id") String service_provider_id,
            @Field("description") String description,
            @Header("Authorization") String token);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @GET("user/order")
    Call<OrdersResponse> getOrders(
            @Header("Authorization") String token);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("user/giveRating")
    Call<RatingResponse> giveRating(
            @Field("order_id") String order_id,
            @Field("stars") String stars,
            @Field("description") String description,
            @Field("service_id") String service_id,
            @Field("service_provider_id") String service_provider_id,
            @Header("Authorization") String token);


    @Headers({"Accept: application/json"})
    @GET("services")
    Call<ServicesResponse> services();

    @Headers({"Accept: application/json"})
    @GET("categories")
    Call<CategoriesResponse> categories();

    @Headers({"Accept: application/json"})
    @GET("user/pendingOrders")
    Call<OrdersResponse> userGetPeningOrders(
            @Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("user/completedOrders")
    Call<OrdersResponse> userGetCompeletedOrders(
            @Header("Authorization") String token);


    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @GET("getServiceProviderAverageRating")
    Call<String> getServiceProviderAvgRating(
            @Field("service_provider_id") String service_provider_id);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("getServiceAverageRating")
    Call<Float> getServiceAvgRating(
            @Field("service_id") String service_id);


}
