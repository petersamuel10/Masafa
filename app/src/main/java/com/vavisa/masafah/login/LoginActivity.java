package com.vavisa.masafah.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.widget.Button;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.KeyboardUtil;
import com.vavisa.masafah.verify_phone_number.VerifyYourNumberActivity;

public class LoginActivity extends BaseActivity implements LoginView {

    private TextInputEditText mobileNumber;
    private Button country_code_btn, continueButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        country_code_btn = findViewById(R.id.country_code);
        mobileNumber = findViewById(R.id.mobile_number);
        continueButton = findViewById(R.id.continue_buton);

        final LoginPresenter loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);

        continueButton.setOnClickListener(
                v -> {
                    KeyboardUtil.hideKeyboardFrom(this,continueButton);
                    if (validate()) {
                        if (Connectivity.checkInternetConnection()) {
                            Login loginModel = new Login(mobileNumber.getText().toString(), "4");
                            loginPresenter.loginFun(loginModel);
                        } else
                            showErrorConnection();
                    }
                });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(mobileNumber.getText())) {
            showMessage();
            return false;
        }
        return true;
    }

    @Override
    public void LoginResult(String otp) {
        Intent intent = new Intent(this, VerifyYourNumberActivity.class);
        intent.putExtra("mobile_number", mobileNumber.getText().toString());
        intent.putExtra("otp", otp);
        startActivity(intent);
        finish();
    }
}
