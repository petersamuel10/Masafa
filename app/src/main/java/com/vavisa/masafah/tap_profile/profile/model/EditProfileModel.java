package com.vavisa.masafah.tap_profile.profile.model;

import com.google.gson.annotations.SerializedName;

public class EditProfileModel {

    @SerializedName("mobile")
    private String mobile;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("email")
    private String email;
    @SerializedName("image")
    private String image;

    public EditProfileModel(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public EditProfileModel(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
