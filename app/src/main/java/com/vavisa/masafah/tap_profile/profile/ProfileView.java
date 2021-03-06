package com.vavisa.masafah.tap_profile.profile;

import com.vavisa.masafah.base.BaseView;
import com.vavisa.masafah.tap_profile.profile.model.UpdateProfileResponseM;
import com.vavisa.masafah.verify_phone_number.model.User;

public interface ProfileView extends BaseView {

    void user(User user);
    void updateProfileResponse(User user);
    void changeMobileResponse(String updated_mobile, String otp);
    void logout ();
}
