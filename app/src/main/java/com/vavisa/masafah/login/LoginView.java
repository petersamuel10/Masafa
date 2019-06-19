package com.vavisa.masafah.login;

import com.vavisa.masafah.base.BaseView;

import java.util.ArrayList;

public interface LoginView extends BaseView {

    void LoginResult(String otp);
    void countries(ArrayList<CountryModel> countriesList);
}
