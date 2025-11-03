package com.example.serviceprovider;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.serviceprovider.Api.Service;
import com.example.serviceprovider.Api.ServiceProvider;
import com.example.serviceprovider.Api.User;
import com.google.gson.Gson;

public class MySharedPreferences {

    private static final String APP_SETTINGS = "com.example.serviceprovider";

    public enum USER_TYPE {
        Service_Provider,
        Service_Comsumer
    };

    private MySharedPreferences() {}

    public static void clear(Context context)
    {
        getSharedPreferencesManager(context).edit().clear();
    }

    public static void setUserType(Context context, USER_TYPE user_type)
    {
        Gson gson = new Gson();
        String json = gson.toJson(user_type);
        SharedPreferences.Editor editor = getSharedPreferencesManager(context).edit();
        editor.putString("UserType",json);
        editor.apply();
    }

    public static void setUser(Context context, User user)
    {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SharedPreferences.Editor editor = getSharedPreferencesManager(context).edit();
        editor.putString("User",json);
        editor.apply();
    }

    public static User getUser(Context context)
    {
        Gson gson = new Gson();
        return gson.fromJson(getSharedPreferencesManager(context).getString("User", ""), User.class);
    }

    public static USER_TYPE getUserType(Context context)
    {
        Gson gson = new Gson();
        return gson.fromJson(getSharedPreferencesManager(context).getString("UserType", ""), USER_TYPE.class);
    }


    public static void setService(Context context, Service service)
    {
        Gson gson = new Gson();
        String json = gson.toJson(service);
        SharedPreferences.Editor editor = getSharedPreferencesManager(context).edit();
        editor.putString("Service",json);
        editor.apply();
    }

    public static Service getService(Context context)
    {
        Gson gson = new Gson();
        return gson.fromJson(getSharedPreferencesManager(context).getString("Service", ""), Service.class);
    }


    public static void setServiceProvider(Context context, ServiceProvider serviceProvider)
    {
        Gson gson = new Gson();
        String json = gson.toJson(serviceProvider);
        SharedPreferences.Editor editor = getSharedPreferencesManager(context).edit();
        editor.putString("ServiceProvider",json);
        editor.apply();
    }

    public static ServiceProvider getServiceProvider(Context context)
    {
        Gson gson = new Gson();
        return gson.fromJson(getSharedPreferencesManager(context).getString("ServiceProvider", ""), ServiceProvider.class);
    }


    public static SharedPreferences getSharedPreferencesManager(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    /**
     * method to set string value
     * @param context context of the application
     * @param KEY key of the value
     * @param Value value
     */
    public static void setStringValue(Context context, String KEY, String Value){
        SharedPreferences.Editor editor = getSharedPreferencesManager(context).edit();
        editor.putString(KEY,Value);
        editor.apply();
    }

    /**
     * method to get the value from the following key
     * @param context context of the application
     * @param KEY key of the value
     * @param DefaultValue Default Value
     */
    public static String getStringValue(Context context,String KEY,String DefaultValue){
        return getSharedPreferencesManager(context).getString(KEY,DefaultValue);
    }

    /**
     * method to set values in the integer files of the applications..
     * @param context context of the application
     * @param KEY Key of the application
     * @param Value value of the application
     */
    public static void setIntegerValue(Context context, String KEY, int Value){
        SharedPreferences.Editor editor = getSharedPreferencesManager(context).edit();
        editor.putInt(KEY,Value);
        editor.apply();
    }

    /**
     * method to get the value from the following key
     * @param context context of the application
     * @param KEY key of the value
     * @param DefaultValue Default Value
     */
    public static boolean getBooleanValue(Context context,String KEY,boolean DefaultValue){
        return getSharedPreferencesManager(context).getBoolean(KEY,DefaultValue);
    }

    /**
     * method to set values in the integer files of the applications..
     * @param context context of the application
     * @param KEY Key of the application
     * @param Value value of the application
     */
    public static void setBooleanValue(Context context, String KEY, boolean Value){
        SharedPreferences.Editor editor = getSharedPreferencesManager(context).edit();
        editor.putBoolean(KEY,Value);
        editor.apply();
    }

    /**
     * method to get the value from the following key
     * @param context context of the application
     * @param KEY key of the value
     * @param DefaultValue Default Value
     */
    public static int getIntegerValue(Context context,String KEY,int DefaultValue){
        return getSharedPreferencesManager(context).getInt(KEY,DefaultValue);
    }

    public static String getAPIToken(Context context)
    {
        return getSharedPreferencesManager(context).getString(SharedPref.USER_TOKEN, "");
    }

    public static void setAPIToken(Context context, String token)
    {
        SharedPreferences.Editor editor = getSharedPreferencesManager(context).edit();
        editor.putString(SharedPref.USER_TOKEN, token);
        editor.apply();
    }
}
