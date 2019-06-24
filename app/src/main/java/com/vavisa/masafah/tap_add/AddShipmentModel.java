package com.vavisa.masafah.tap_add;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.tap_add.add_shipment.model.NewShipmentModel;

import java.util.ArrayList;

public class AddShipmentModel {

    @SerializedName("shipments")
    private ArrayList<NewShipmentModel> shipmentList;
    @SerializedName("delivery_companies_id")
    private ArrayList<Integer> deliveryCompaniesIdList;
    @SerializedName("address_from_id")
    private Integer address_from_id;
    @SerializedName("address_to_id")
    private Integer address_to_id;
    @SerializedName("pickup_time")
    private String pickup_time;

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

    public Integer getAddress_from_id() {
        return address_from_id;
    }
    public void setAddress_from_id(Integer address_from_id) {
        this.address_from_id = address_from_id;
    }

    public Integer getAddress_to_id() {
        return address_to_id;
    }
    public void setAddress_to_id(Integer address_to_id) {
        this.address_to_id = address_to_id;
    }

    public String getPickup_time() {
        return pickup_time;
    }
    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }
}
