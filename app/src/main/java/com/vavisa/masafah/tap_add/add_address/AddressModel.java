package com.vavisa.masafah.tap_add.add_address;

import com.google.gson.annotations.SerializedName;

public class AddressModel {

    @SerializedName("address_id")
    private String address_id;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
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
}
