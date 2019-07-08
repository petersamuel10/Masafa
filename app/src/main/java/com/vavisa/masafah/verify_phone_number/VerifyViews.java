package com.vavisa.masafah.verify_phone_number;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.verify_phone_number.model.VerifyResponseModel;

public interface VerifyViews extends BaseView {

    void userInfo(VerifyResponseModel verifyResponseModel);
    void clearEditText();

}
