package com.vavisa.masafah.tap_add.companies;

import com.google.gson.annotations.SerializedName;

public class CompanyModel {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("image")
    private String image;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
