package com.vavisa.masafah.tap_add;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.add_shipment.model.NewShipmentModel;

import java.util.ArrayList;

public class AddShipmentModel {

    @SerializedName("shipments")
    private ArrayList<NewShipmentModel> shipmentList;
    @SerializedName("delivery_companies_id")
    private ArrayList<Integer> deliveryCompaniesIdList;
    @SerializedName("address_from_id")
    private String address_from_id;
    @SerializedName("address_to_id")
    private String address_to_id;
    @SerializedName("pickup_time_from")
    private String pickup_time_from;
    @SerializedName("pickup_time_to")
    private String pickup_time_to;

    private AddressModel pickup_address;
    private AddressModel drop_address;
    private String price;

    public ArrayList<NewShipmentModel> getShipmentList() {
        return shipmentList;
    }
    public void setShipmentList(ArrayList<NewShipmentModel> shipmentList) {
        this.shipmentList = shipmentList;
    }

    public ArrayList<Integer> getDeliveryCompaniesIdList() {
        return deliveryCompaniesIdList;
    }
    public void setDeliveryCompaniesIdList(ArrayList<Integer> deliveryCompaniesIdList) {
        this.deliveryCompaniesIdList = deliveryCompaniesIdList;
    }

    public String getAddress_from_id() {
        return address_from_id;
    }
    public void setAddress_from_id(String address_from_id) {
        this.address_from_id = address_from_id;
    }

    public String getAddress_to_id() {
        return address_to_id;
    }
    public void setAddress_to_id(String address_to_id) {
        this.address_to_id = address_to_id;
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

    public AddressModel getPickup_address() {
        return pickup_address;
    }
    public void setPickup_address(AddressModel pickup_address) {
        this.pickup_address = pickup_address;
    }

    public AddressModel getDrop_address() {
        return drop_address;
    }
    public void setDrop_address(AddressModel drop_address) {
        this.drop_address = drop_address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
