package com.vavisa.masafah.tap_my_shipment.my_shipments.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.add_shipment.model.Shipment;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyModel;

import java.util.ArrayList;

public class ShipmentModel implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("price")
    private String price;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("is_today")
    private Boolean isToday;
    @SerializedName("pickup_time_from")
    private String pickup_time_from;
    @SerializedName("pickup_time_to")
    private String pickup_time_to;
    @SerializedName("status")
    private String status;
    @SerializedName("payment_type")
    private String payment_type;
    @SerializedName("address_from")
    private AddressModel address_from;
    @SerializedName("items")
    private ArrayList<ShipmentItems> items;
    @SerializedName("company")
    private CompanyModel company;


    protected ShipmentModel(Parcel in) {
        id = in.readString();
        price = in.readString();
        user_id = in.readString();
        byte tmpIsToday = in.readByte();
        isToday = tmpIsToday == 0 ? null : tmpIsToday == 1;
        pickup_time_from = in.readString();
        pickup_time_to = in.readString();
        status = in.readString();
        payment_type = in.readString();
        address_from = in.readParcelable(AddressModel.class.getClassLoader());
        items = in.createTypedArrayList(ShipmentItems.CREATOR);
        company = in.readParcelable(CompanyModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(price);
        dest.writeString(user_id);
        dest.writeByte((byte) (isToday == null ? 0 : isToday ? 1 : 2));
        dest.writeString(pickup_time_from);
        dest.writeString(pickup_time_to);
        dest.writeString(status);
        dest.writeString(payment_type);
        dest.writeParcelable(address_from, flags);
        dest.writeTypedList(items);
        dest.writeParcelable(company, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShipmentModel> CREATOR = new Creator<ShipmentModel>() {
        @Override
        public ShipmentModel createFromParcel(Parcel in) {
            return new ShipmentModel(in);
        }

        @Override
        public ShipmentModel[] newArray(int size) {
            return new ShipmentModel[size];
        }
    };

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPickup_time_from() {
        return pickup_time_from;
    }
    public void setPickup_time_from(String pickup_time_from) {
        this.pickup_time_from = pickup_time_from;
    }

    public String getPickup_time_to() {
        return pickup_time_to;
    }
    public void setPickup_time_to(String pickup_time_to) {
        this.pickup_time_to = pickup_time_to;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_type() {
        return payment_type;
    }
    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public AddressModel getAddress_from() {
        return address_from;
    }
    public void setAddress_from(AddressModel address_from) {
        this.address_from = address_from;
    }

    public ArrayList<ShipmentItems> getItems() {
        return items;
    }
    public void setItems(ArrayList<ShipmentItems> items) {
        this.items = items;
    }

    public CompanyModel getCompany() {
        return company;
    }
    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    public Boolean getToday() {
        return isToday;
    }
    public void setToday(Boolean today) {
        isToday = today;
    }
}
