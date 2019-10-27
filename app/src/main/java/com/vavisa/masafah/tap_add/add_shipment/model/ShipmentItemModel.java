package com.vavisa.masafah.tap_add.add_shipment.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.tap_add.add_address.AddressModel;

public class ShipmentItemModel implements Parcelable {

    @SerializedName("category_id")
    private Integer category_id;
    @SerializedName("category_name")
    private String cat_name;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("address_to_id")
    private Integer address_to_id;

    private AddressModel drop_address;

    // for display data in invoice
    private Boolean isVisited = false;

    public ShipmentItemModel() {
    }

    public ShipmentItemModel(Integer category_id, String cat_name, Integer quantity, Integer address_to_id) {
        this.category_id = category_id;
        this.cat_name = cat_name;
        this.quantity = quantity;
        this.address_to_id = address_to_id;
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
        if (in.readByte() == 0) {
            address_to_id = null;
        } else {
            address_to_id = in.readInt();
        }
        drop_address = in.readParcelable(AddressModel.class.getClassLoader());
        byte tmpIsVisited = in.readByte();
        isVisited = tmpIsVisited == 0 ? null : tmpIsVisited == 1;
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
        if (address_to_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(address_to_id);
        }
        dest.writeParcelable(drop_address, flags);
        dest.writeByte((byte) (isVisited == null ? 0 : isVisited ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Integer getAddress_to_id() {
        return address_to_id;
    }
    public void setAddress_to_id(Integer address_to_id) {
        this.address_to_id = address_to_id;
    }

    public AddressModel getDrop_address() {
        return drop_address;
    }
    public void setDrop_address(AddressModel drop_address) {
        this.drop_address = drop_address;
    }

    public Boolean getVisited() {
        return isVisited;
    }
    public void setVisited(Boolean visited) {
        isVisited = visited;
    }
}
