package com.example.serviceprovider.Api;

import android.os.Parcel;
import android.os.Parcelable;

public class Service implements Parcelable {
    String id;
    String service_provider_id;
    String name;
    String hourly_cost;
    String providerName;
    String categoryName;


    public String getId() {
        return id;
    }

    public String getService_provider_id() {
        return service_provider_id;
    }

    public String getCategoryName() {
        return categoryName;
    }


    public String getHourly_cost() {
        return hourly_cost;
    }

    public String getName() {
        return name;
    }

    public String getProviderName() {
        return providerName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.service_provider_id);
        dest.writeString(this.name);
        dest.writeString(this.hourly_cost);
        dest.writeString(this.providerName);
        dest.writeString(this.categoryName);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.service_provider_id = source.readString();
        this.name = source.readString();
        this.hourly_cost = source.readString();
        this.providerName = source.readString();
        this.categoryName = source.readString();
    }

    public Service() {
    }

    protected Service(Parcel in) {
        this.id = in.readString();
        this.service_provider_id = in.readString();
        this.name = in.readString();
        this.hourly_cost = in.readString();
        this.providerName = in.readString();
        this.categoryName = in.readString();
    }

    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel source) {
            return new Service(source);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}
