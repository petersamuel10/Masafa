package com.vavisa.masafah.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Constants;
import com.vavisa.masafah.util.KeyboardUtil;
import com.vavisa.masafah.verify_phone_number.VerifyYourNumberActivity;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements LoginView {

    private TextInputEditText mobileNumber;
    private Button country_code_btn, continueButton;
    private LoginPresenter loginPresenter;
    private ArrayList<CountryModel> countriesList;
    private Integer select_country_pos = 0, country_id = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        loginPresenter.getCountries();

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
                        if (Connectivity.checkInternetConnection()) {
                            Login loginModel = new Login(mobileNumber.getText().toString(), country_id, Constants.ONE_SIGNAL_TOKEN, 2);
                            loginPresenter.loginFun(loginModel);
                        } else
                            showErrorConnection();
                    }
                });
    }

    private void initViews() {
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);

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
    public void LoginResult(String otp) {
        Log.d("otp", otp);
        Intent intent = new Intent(this, VerifyYourNumberActivity.class);
        intent.putExtra("mobile_number", mobileNumber.getText().toString());
        startActivity(intent);
    }

    @Override
    public void countries(ArrayList<CountryModel> countriesList) {
        this.countriesList = countriesList;
    }
}
