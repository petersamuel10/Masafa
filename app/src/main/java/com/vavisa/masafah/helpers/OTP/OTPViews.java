package com.vavisa.masafah.helpers.OTP;

import com.vavisa.masafah.base.BaseView;

import java.util.ArrayList;

public interface OTPViews extends BaseView {

    void handleVerification_id(String verification_id);
    void countries(ArrayList<CountryModel> countriesList);
}
