package com.vavisa.masafah.verify_phone_number.model;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.verify_phone_number.model.User;

public class VerifyResponseModel {

    @SerializedName("access_token")
    private String access_token;
    @SerializedName("user")
    private User user;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

