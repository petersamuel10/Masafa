package com.vavisa.masafah.tap_my_shipment.my_shipments;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShipmentModel {

    @SerializedName("pending")
    private ArrayList<String> pending;
    @SerializedName("accepted")
    private ArrayList<String> accepted;
    @SerializedName("pickedUp")
    private ArrayList<String> pickedUp;
    @SerializedName("delivered")
    private ArrayList<String> delivered;

    public ArrayList<String> getPending() {
        return pending;
    }

    public void setPending(ArrayList<String> pending) {
        this.pending = pending;
    }

    public ArrayList<String> getAccepted() {
        return accepted;
    }

    public void setAccepted(ArrayList<String> accepted) {
        this.accepted = accepted;
    }

    public ArrayList<String> getPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(ArrayList<String> pickedUp) {
        this.pickedUp = pickedUp;
    }

    public ArrayList<String> getDelivered() {
        return delivered;
    }

    public void setDelivered(ArrayList<String> delivered) {
        this.delivered = delivered;
    }
}
