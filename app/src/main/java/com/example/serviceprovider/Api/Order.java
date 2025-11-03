package com.example.serviceprovider.Api;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    String id;
    String user_id;
    String service_provider_id;
    String service_id;
    String description;
    String payment;
    String providerName;
    String categoryName;
    String buyerName;
    String total_charges;
    String extra_work;
    String extra_work_charges;
    String time_taken;
    String rating_done;

    public void setRating_done(String rating_done) {
        this.rating_done = rating_done;
    }

    public String getRating_done() {
        return rating_done;
    }


    public String getId() {
        return id;
    }

    public String getTotal_charges() {
        return total_charges;
    }

    public String getExtra_work() {
        return extra_work;
    }

    public String getExtra_work_charges() {
        return extra_work_charges;
    }

    public String getTime_taken() {
        return time_taken;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    String buyerContact;

    public String getBuyerAddress() {
        return buyerAddress;
    }

    String buyerAddress;

    public String getProviderName() {
        return providerName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getService_provider_id() {
        return service_provider_id;
    }

    public String getService_id() {
        return service_id;
    }

    public String getDescription() {
        return description;
    }

    public String getPayment() {
        return payment;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.user_id);
        dest.writeString(this.service_provider_id);
        dest.writeString(this.service_id);
        dest.writeString(this.description);
        dest.writeString(this.payment);
        dest.writeString(this.providerName);
        dest.writeString(this.categoryName);
        dest.writeString(this.buyerName);
        dest.writeString(this.total_charges);
        dest.writeString(this.extra_work);
        dest.writeString(this.extra_work_charges);
        dest.writeString(this.time_taken);
        dest.writeString(this.rating_done);
        dest.writeString(this.buyerContact);
        dest.writeString(this.buyerAddress);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.user_id = source.readString();
        this.service_provider_id = source.readString();
        this.service_id = source.readString();
        this.description = source.readString();
        this.payment = source.readString();
        this.providerName = source.readString();
        this.categoryName = source.readString();
        this.buyerName = source.readString();
        this.total_charges = source.readString();
        this.extra_work = source.readString();
        this.extra_work_charges = source.readString();
        this.time_taken = source.readString();
        this.rating_done = source.readString();
        this.buyerContact = source.readString();
        this.buyerAddress = source.readString();
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.id = in.readString();
        this.user_id = in.readString();
        this.service_provider_id = in.readString();
        this.service_id = in.readString();
        this.description = in.readString();
        this.payment = in.readString();
        this.providerName = in.readString();
        this.categoryName = in.readString();
        this.buyerName = in.readString();
        this.total_charges = in.readString();
        this.extra_work = in.readString();
        this.extra_work_charges = in.readString();
        this.time_taken = in.readString();
        this.rating_done = in.readString();
        this.buyerContact = in.readString();
        this.buyerAddress = in.readString();
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
