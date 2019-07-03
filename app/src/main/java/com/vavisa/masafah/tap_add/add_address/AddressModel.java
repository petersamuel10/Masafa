package com.vavisa.masafah.tap_add.add_address;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AddressModel implements Parcelable {

    @SerializedName("address_id")
    private String address_id;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("block")
    private String block;
    @SerializedName("street")
    private String street;
    @SerializedName("area")
    private String area;
    @SerializedName("building")
    private String building;
    @SerializedName("details")
    private String details;
    @SerializedName("notes")
    private String notes;

    public AddressModel() {
    }

    // for add new address
    public AddressModel(String name, String block, String street, String area, String building, String details, String notes) {
        this.name = name;
        this.block = block;
        this.street = street;
        this.area = area;
        this.building = building;
        this.details = details;
        this.notes = notes;
    }

    //for edit address
    public AddressModel(String address_id, String name, String block, String street, String area, String building, String details, String notes) {
        this.address_id = address_id;
        this.name = name;
        this.block = block;
        this.street = street;
        this.area = area;
        this.building = building;
        this.details = details;
        this.notes = notes;
    }

    protected AddressModel(Parcel in) {
        address_id = in.readString();
        id = in.readString();
        name = in.readString();
        mobile = in.readString();
        block = in.readString();
        street = in.readString();
        area = in.readString();
        building = in.readString();
        details = in.readString();
        notes = in.readString();
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

    public String getAddress_id() {
        return address_id;
    }
    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBlock() {
        return block;
    }
    public void setBlock(String block) {
        this.block = block;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public String getBuilding() {
        return building;
    }
    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address_id);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(block);
        dest.writeString(street);
        dest.writeString(area);
        dest.writeString(building);
        dest.writeString(details);
        dest.writeString(notes);
    }
}
