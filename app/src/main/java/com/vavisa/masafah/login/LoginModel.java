package com.vavisa.masafah.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoginModel implements Parcelable {

    @SerializedName("idToken")
    private String id_token;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("country_id")
    private String country_id;
    @SerializedName("player_id")
    private String onSignal_player_id;
    @SerializedName("device_type")
    private Integer device_type;

    private String country_code;
    private String verification_id;

    public LoginModel() {
    }

    public LoginModel(String mobile, String country_id, String onSignal_player_id,
                      Integer device_type, String country_code, String verification_id) {
        this.mobile = mobile;
        this.country_id = country_id;
        this.onSignal_player_id = onSignal_player_id;
        this.device_type = device_type;
        this.country_code = country_code;
        this.verification_id = verification_id;
    }

    protected LoginModel(Parcel in) {
        id_token = in.readString();
        mobile = in.readString();
        if (in.readByte() == 0) {
            country_id = null;
        } else {
            country_id = in.readString();
        }
        onSignal_player_id = in.readString();
        if (in.readByte() == 0) {
            device_type = null;
        } else {
            device_type = in.readInt();
        }
        country_code = in.readString();
        verification_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_token);
        dest.writeString(mobile);
        if (country_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(country_id);
        }
        dest.writeString(onSignal_player_id);
        if (device_type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(device_type);
        }
        dest.writeString(country_code);
        dest.writeString(verification_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

    public String getId_token() {
        return id_token;
    }
    public void setId_token(String id_token) {
        this.id_token = id_token;
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

    public String getCountry_code() {
        return country_code;
    }
    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getVerification_id() {
        return verification_id;
    }
    public void setVerification_id(String verification_id) {
        this.verification_id = verification_id;
    }
}
