package com.vavisa.masafah.tap_add.add_address;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.helpers.OTP.CountryModel;

public class AddressModel implements Parcelable {

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
    @SerializedName("country")
    private CountryModel country;
    @SerializedName("city")
    private CountryModel city;
    @SerializedName("governorate")
    private CountryModel governorate;
    @SerializedName("building")
    private String building;
    @SerializedName("details")
    private String details;
    @SerializedName("notes")
    private String notes;
    @SerializedName("country_id")
    private String country_id;
    @SerializedName("governorate_id")
    private String governorate_id;
    @SerializedName("city_id")
    private String city_id;

    public AddressModel() {
    }

    // for add new address
    public AddressModel(String name, String mobile,
                        String country_id, String governorate_id, String city_id,
                        String block, String street,String building, String details, String notes) {
        this.name = name;
        this.mobile = mobile;
        this.country_id = country_id;
        this.governorate_id = governorate_id;
        this.city_id = city_id;
        this.block = block;
        this.street = street;
        this.building = building;
        this.details = details;
        this.notes = notes;
    }

    //for edit address
    public AddressModel(String address_id, String name, String mobile,
                        String country_id, String governorate_id, String city_id,
                        String block, String street,String building, String details, String notes ){
        this.id = address_id;
        this.name = name;
        this.mobile = mobile;
        this.country_id = country_id;
        this.governorate_id = governorate_id;
        this.city_id = city_id;
        this.block = block;
        this.street = street;
        this.building = building;
        this.details = details;
        this.notes = notes;
    }


    protected AddressModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        mobile = in.readString();
        block = in.readString();
        street = in.readString();
        country = in.readParcelable(CountryModel.class.getClassLoader());
        city = in.readParcelable(CountryModel.class.getClassLoader());
        governorate = in.readParcelable(CountryModel.class.getClassLoader());
        building = in.readString();
        details = in.readString();
        notes = in.readString();
        country_id = in.readString();
        governorate_id = in.readString();
        city_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(block);
        dest.writeString(street);
        dest.writeParcelable(country, flags);
        dest.writeParcelable(city, flags);
        dest.writeParcelable(governorate, flags);
        dest.writeString(building);
        dest.writeString(details);
        dest.writeString(notes);
        dest.writeString(country_id);
        dest.writeString(governorate_id);
        dest.writeString(city_id);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public CountryModel getCountry() {
        return country;
    }
    public void setCountry(CountryModel country) {
        this.country = country;
    }

    public CountryModel getCity() {
        return city;
    }
    public void setCity(CountryModel city) {
        this.city = city;
    }

    public CountryModel getGovernorate() {
        return governorate;
    }
    public void setGovernorate(CountryModel governorate) {
        this.governorate = governorate;
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

    public String getCountry_id() {
        return country_id;
    }
    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getGovernorate_id() {
        return governorate_id;
    }
    public void setGovernorate_id(String governorate_id) {
        this.governorate_id = governorate_id;
    }

    public String getCity_id() {
        return city_id;
    }
    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

}
