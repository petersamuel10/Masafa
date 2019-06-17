package com.vavisa.masafah.login;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("country_id")
    private String country_id;

    @SerializedName("otp")
    private String otp;

    public Login() {
    }

    public Login(String mobile, String country_id) {
        this.mobile = mobile;
        this.country_id = country_id;
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
}
