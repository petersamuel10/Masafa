package com.vavisa.masafah.tap_my_shipment.company_details;

import com.google.gson.annotations.SerializedName;

public class RatingModel {

    @SerializedName("company_id")
    private String company_id;
    @SerializedName("rating")
    private Float rating;

    public RatingModel(String company_id, Float rating) {
        this.company_id = company_id;
        this.rating = rating;
    }

    public String getCompany_id() {
        return company_id;
    }
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
}
