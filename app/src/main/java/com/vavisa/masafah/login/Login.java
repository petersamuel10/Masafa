package com.vavisa.masafah.login;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("country_id")
    private String country_id;

    @SerializedName("otp")
    private String otp;

    @SerializedName("player_id")
    private String onSignal_player_id;

    @SerializedName("device_type")
    private Integer device_type;



    public Login() {
    }

    public Login(String mobile, String country_id, String onSignal_player_id, Integer device_type) {
        this.mobile = mobile;
        this.country_id = country_id;
        this.onSignal_player_id = onSignal_player_id;
        this.device_type = device_type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOnSignal_player_id() {
        return onSignal_player_id;
    }

    public void setOnSignal_player_id(String onSignal_player_id) {
        this.onSignal_player_id = onSignal_player_id;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }
}
