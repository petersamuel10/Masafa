package com.vavisa.masafah.tap_add;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.add_shipment.model.ShipmentItemModel;

import java.util.ArrayList;

public class AddShipmentModel implements Parcelable {

    @SerializedName("shipment_id")
    private String shipment_id;
    @SerializedName("shipments")
    private ArrayList<ShipmentItemModel> shipmentList;
    @SerializedName("delivery_companies_id")
    private ArrayList<Integer> deliveryCompaniesIdList;
    @SerializedName("address_from_id")
    private Integer address_from_id;
    @SerializedName("is_today")
    private Boolean is_today;
    @SerializedName("pickup_time_from")
    private String pickup_time_from;
    @SerializedName("pickup_time_to")
    private String pickup_time_to;

    private AddressModel pickup_address;
    private String price;



    public AddShipmentModel() {
    }

    public AddShipmentModel(Parcel in) {
        shipment_id = in.readString();
        shipmentList = in.createTypedArrayList(ShipmentItemModel.CREATOR);
        deliveryCompaniesIdList = new ArrayList<>();
        in.readList(deliveryCompaniesIdList, Integer.class.getClassLoader());
        if (in.readByte() == 0) {
            address_from_id = null;
        } else {
            address_from_id = in.readInt();
        }
        byte tmpIs_today = in.readByte();
        is_today = tmpIs_today == 0 ? null : tmpIs_today == 1;
        pickup_time_from = in.readString();
        pickup_time_to = in.readString();
        pickup_address = in.readParcelable(AddressModel.class.getClassLoader());
        price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shipment_id);
        dest.writeTypedList(shipmentList);
        dest.writeList(deliveryCompaniesIdList);
        if (address_from_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(address_from_id);
        }
        dest.writeByte((byte) (is_today == null ? 0 : is_today ? 1 : 2));
        dest.writeString(pickup_time_from);
        dest.writeString(pickup_time_to);
        dest.writeParcelable(pickup_address, flags);
        dest.writeString(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddShipmentModel> CREATOR = new Creator<AddShipmentModel>() {
        @Override
        public AddShipmentModel createFromParcel(Parcel in) {
            return new AddShipmentModel(in);
        }

        @Override
        public AddShipmentModel[] newArray(int size) {
            return new AddShipmentModel[size];
        }
    };

    public String getShipment_id() {
        return shipment_id;
    }
    public void setShipment_id(String shipment_id) {
        this.shipment_id = shipment_id;
    }

    public ArrayList<ShipmentItemModel> getShipmentList() {
        return shipmentList;
    }
    public void setShipmentList(ArrayList<ShipmentItemModel> shipmentList) {
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

    public Boolean getIs_today() {
        return is_today;
    }
    public void setIs_today(Boolean is_today) {
        this.is_today = is_today;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

}
