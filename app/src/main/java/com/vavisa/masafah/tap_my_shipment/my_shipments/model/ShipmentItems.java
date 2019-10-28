package com.vavisa.masafah.tap_my_shipment.my_shipments.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.add_shipment.model.Shipment;

import java.util.ArrayList;

public class ShipmentItems implements Parcelable {

    @SerializedName("address_to")
    private AddressModel addressTo;
    @SerializedName("products")
    private ArrayList<Shipment> shipmentList;


    private ShipmentItems(Parcel in) {
        addressTo = in.readParcelable(AddressModel.class.getClassLoader());
        shipmentList = in.createTypedArrayList(Shipment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(addressTo, flags);
        dest.writeTypedList(shipmentList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShipmentItems> CREATOR = new Creator<ShipmentItems>() {
        @Override
        public ShipmentItems createFromParcel(Parcel in) {
            return new ShipmentItems(in);
        }

        @Override
        public ShipmentItems[] newArray(int size) {
            return new ShipmentItems[size];
        }
    };

    public AddressModel getAddressTo() {
        return addressTo;
    }
    public void setAddressTo(AddressModel addressTo) {
        this.addressTo = addressTo;
    }

    public ArrayList<Shipment> getShipmentList() {
        return shipmentList;
    }
    public void setShipmentList(ArrayList<Shipment> shipmentList) {
        this.shipmentList = shipmentList;
    }
}
