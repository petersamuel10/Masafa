package com.vavisa.masafah.tap_add.add_shipment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ShipmentItemModel implements Parcelable {

    @SerializedName("category_id")
    private Integer category_id;
    @SerializedName("category_name")
    private String cat_name;
    @SerializedName("quantity")
    private Integer quantity;


    public ShipmentItemModel(String cat_name, Integer category_id, Integer quantity) {
        this.cat_name = cat_name;
        this.category_id = category_id;
        this.quantity = quantity;
    }


    protected ShipmentItemModel(Parcel in) {
        if (in.readByte() == 0) {
            category_id = null;
        } else {
            category_id = in.readInt();
        }
        cat_name = in.readString();
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
    }

    public static final Creator<ShipmentItemModel> CREATOR = new Creator<ShipmentItemModel>() {
        @Override
        public ShipmentItemModel createFromParcel(Parcel in) {
            return new ShipmentItemModel(in);
        }

        @Override
        public ShipmentItemModel[] newArray(int size) {
            return new ShipmentItemModel[size];
        }
    };

    public String getCat_name() {
        return cat_name;
    }
    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (category_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(category_id);
        }
        dest.writeString(cat_name);
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
    }
}
