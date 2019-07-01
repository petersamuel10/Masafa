package com.vavisa.masafah.tap_my_shipment.my_shipments;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyModel;

import java.util.ArrayList;

public class ShipmentModel {

    @SerializedName("id")
    private String id;
    @SerializedName("price")
    private String price;
    @SerializedName("user_id")
    private String user_id;
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
    @SerializedName("address_to")
    private AddressModel address_to;
    @SerializedName("items")
    private ArrayList<Items> items;
    @SerializedName("company")
    private CompanyModel company;

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

    public AddressModel getAddress_to() {
        return address_to;
    }
    public void setAddress_to(AddressModel address_to) {
        this.address_to = address_to;
    }

    public ArrayList<Items> getItems() {
        return items;
    }
    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public CompanyModel getCompany() {
        return company;
    }
    public void setCompany(CompanyModel company) {
        this.company = company;
    }
}
