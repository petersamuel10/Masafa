package com.vavisa.masafah.verify_phone_number;

import com.vavisa.masafah.base.BaseView;

public interface VerifyViews extends BaseView {

    void verify_opt(VerifyResponseModel verifyResponseModel);
    void OTP(String otp);

}
