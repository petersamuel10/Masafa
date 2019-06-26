package com.vavisa.masafah.tap_add.add_address;

import com.google.gson.annotations.SerializedName;

public class AddressModel {

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

    public AddressModel(String name, String block, String street, String area, String building, String details, String notes) {
        this.name = name;
        this.block = block;
        this.street = street;
        this.area = area;
        this.building = building;
        this.details = details;
        this.notes = notes;
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
