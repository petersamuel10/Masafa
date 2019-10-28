package com.vavisa.masafah.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.helpers.OTP.CountryModel;
import com.vavisa.masafah.helpers.OTP.OTPViews;
import com.vavisa.masafah.helpers.OTP.SendOTPPresenter;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Constants;
import com.vavisa.masafah.util.KeyboardUtil;
import com.vavisa.masafah.verify_phone_number.VerifyYourNumberActivity;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements OTPViews {

    private TextInputEditText mobileNumber;
    private Button country_code_btn, continueButton;
    private SendOTPPresenter OTPPresenter;
    private ArrayList<CountryModel> countriesList;
    private Integer select_country_pos = 0;
    private String country_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        if (Connectivity.checkInternetConnection())
            OTPPresenter.getCountries();
        else
            showErrorConnection();

        country_code_btn.setOnClickListener(v -> {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.select_country));
            String[] countries_name = new String[countriesList.size()];
            for (int i = 0; i < countriesList.size(); i++) {
                countries_name[i] = countriesList.get(i).getName();
            }

            alert.setSingleChoiceItems(countries_name, select_country_pos, (dialog, position) -> {
                dialog.dismiss();
                select_country_pos = position;
                country_code_btn.setText(countriesList.get(position).getCountry_code());
                country_id = countriesList.get(position).getId();

            });
            alert.create().show();
        });

        continueButton.setOnClickListener(
                v -> {
                    KeyboardUtil.hideKeyboardFrom(this, continueButton);
                    if (validate()) {
                        if (Connectivity.checkInternetConnection())
                            OTPPresenter.sendOtp(this, country_code_btn.getText() + mobileNumber.getText().toString());
                        else
                            showErrorConnection();
                    }
                });
    }

    private void initViews() {

        OTPPresenter = new SendOTPPresenter();
        OTPPresenter.attachView(this);
        country_code_btn = findViewById(R.id.country_code);
        mobileNumber = findViewById(R.id.mobile_number);
        continueButton = findViewById(R.id.continue_buton);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(mobileNumber.getText())) {
            showMessage(getString(R.string.please_enter_mobile_number));
            return false;
        }
        return true;
    }

    @Override
    public void handleVerification_id(String verification_id) {

        LoginModel loginModel =
                new LoginModel(mobileNumber.getText().toString(),
                        country_id,
                        Constants.ONE_SIGNAL_TOKEN,
                        2,
                        country_code_btn.getText().toString(),
                        verification_id);

        Intent intent = new Intent(this, VerifyYourNumberActivity.class);
        intent.putExtra("login_model", loginModel);
        startActivity(intent);

    }

    @Override
    public void countries(ArrayList<CountryModel> countriesList) {
        this.countriesList = countriesList;
        country_code_btn.setText(countriesList.get(0).getCountry_code());
        country_id = countriesList.get(0).getId();
    }
}
