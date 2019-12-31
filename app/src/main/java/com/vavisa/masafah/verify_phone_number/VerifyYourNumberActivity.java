package com.vavisa.masafah.verify_phone_number;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.vavisa.masafah.R;
import com.vavisa.masafah.activities.MainActivity;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.login.LoginModel;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Preferences;
import com.vavisa.masafah.verify_phone_number.model.VerifyResponseModel;

public class VerifyYourNumberActivity extends BaseActivity implements VerifyViews {

    private EditText otpCode1, otpCode2, otpCode3, otpCode4, otpCode5, otpCode6;
    private TextView resend_otp_txt;
    private Button verifyButton;
    private String otp_str;
    private VerifyPresenter verifyPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        initViews();

        LoginModel loginModel;
        boolean isChangeMobile;
        if (getIntent().hasExtra("update_mobile")) {
            isChangeMobile = true;
            loginModel = getIntent().getParcelableExtra("update_mobile");
        } else {
            isChangeMobile = false;
            loginModel = getIntent().getParcelableExtra("login_model");
        }

        verifyPresenter = new VerifyPresenter(this, loginModel, isChangeMobile);
        verifyPresenter.attachView(this);

        verifyButton.setOnClickListener(v -> {

            otp_str = otpCode1.getText().toString() +
                    otpCode2.getText().toString() +
                    otpCode3.getText().toString() +
                    otpCode4.getText().toString() +
                    otpCode5.getText().toString() +
                    otpCode6.getText().toString();

            if (Connectivity.checkInternetConnection())
                verifyPresenter.verifyOTP(otp_str);
            else
                showErrorConnection();

        });

        otpCode1.requestFocus();

        otpCode1.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        otpCode1.clearFocus();
                        otpCode2.requestFocus();
                    }
                });
        otpCode2.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        otpCode2.clearFocus();
                        otpCode3.requestFocus();
                    }
                });
        otpCode3.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        otpCode3.clearFocus();
                        otpCode4.requestFocus();
                    }
                });
        otpCode4.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        otpCode4.clearFocus();
                        otpCode5.requestFocus();
                    }
                });
        otpCode5.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        otpCode5.clearFocus();
                        otpCode6.requestFocus();
                    }
                });

        resend_otp_txt.setOnClickListener(v -> {
            clearEditText();
            resend_otp_txt.setTextColor(Color.RED);
            showMessage(getString(R.string.otp_resend_successfully));
            if (Connectivity.checkInternetConnection())
                verifyPresenter.resendOTP();
            else
                showErrorConnection();
        });
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.verify_number_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        otpCode1 = findViewById(R.id.otp_code_1);
        otpCode2 = findViewById(R.id.otp_code_2);
        otpCode3 = findViewById(R.id.otp_code_3);
        otpCode4 = findViewById(R.id.otp_code_4);
        otpCode5 = findViewById(R.id.otp_code_5);
        otpCode6 = findViewById(R.id.otp_code_6);

        resend_otp_txt = findViewById(R.id.resend_otp_txt);
        resend_otp_txt.setPaintFlags(resend_otp_txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        verifyButton = findViewById(R.id.verify_button);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void userInfo(VerifyResponseModel verifyResModel) {
        // to update mobile in profile when come back again due to update mobile number
        // to prevent load profile again on onStart function
        Preferences.getInstance().putString("mobile", verifyResModel.getUser().getMobile());
        Preferences.getInstance().putString("access_token", verifyResModel.getAccess_token());
        Preferences.getInstance().putString("country_id", verifyResModel.getUser().getCountry_id());

        if (getIntent().hasExtra("update_mobile"))
            onBackPressed();
        else
            start(MainActivity.class);

    }

    @Override
    public void clearEditText() {
        otpCode1.setText("");
        otpCode2.setText("");
        otpCode3.setText("");
        otpCode4.setText("");
        otpCode5.setText("");
        otpCode6.setText("");
        otpCode1.requestFocus();
    }

}
