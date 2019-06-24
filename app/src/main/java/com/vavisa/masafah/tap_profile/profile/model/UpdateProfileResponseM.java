package com.vavisa.masafah.tap_profile.profile.model;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafah.verify_phone_number.model.User;

public class UpdateProfileResponseM {

    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private User user;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
