package com.vavisa.masafah.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("otp")
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
