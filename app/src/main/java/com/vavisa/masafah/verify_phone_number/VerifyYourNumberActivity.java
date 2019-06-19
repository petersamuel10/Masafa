package com.vavisa.masafah.verify_phone_number;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.activities.MainActivity;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.login.Login;

public class VerifyYourNumberActivity extends BaseActivity implements VerifyViews {

    private EditText otpCode1;
    private EditText otpCode2;
    private EditText otpCode3;
    private EditText otpCode4;
    private TextView resend_otp_txt;
    private Button verifyButton;

    private String otp_verify_str, opt_str;

    private VerifyPresenter verifyPresenter;

    Login loginModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        initViews();

        loginModel = new Login();
        loginModel.setMobile(getIntent().getExtras().getString("mobile_number"));
        loginModel.setOtp(getIntent().getExtras().getString("otp"));

        verifyPresenter = new VerifyPresenter();
        verifyPresenter.attachView(this);

        verifyButton.setOnClickListener(v -> {
            opt_str = otpCode1.getText().toString() +
                    otpCode2.getText().toString() +
                    otpCode3.getText().toString() +
                    otpCode4.getText().toString();


            verifyPresenter.verify_opt(loginModel);
        });

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

        resend_otp_txt.setOnClickListener(v -> verifyPresenter.resendOTP(loginModel));
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.verify_number_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        otpCode1 = findViewById(R.id.otp_code_1);
        otpCode2 = findViewById(R.id.otp_code_2);
        otpCode3 = findViewById(R.id.otp_code_3);
        otpCode4 = findViewById(R.id.otp_code_4);

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
    public void verify_opt(VerifyResponseModel verifyResponseModel) {
        start(MainActivity.class);
    }

    @Override
    public void OTP(String otp) {
        loginModel.setOtp(otp);
    }
}
