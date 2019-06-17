package com.vavisa.masafah.network;

import com.vavisa.masafah.login.Login;
import com.vavisa.masafah.login.LoginResponse;
import com.vavisa.masafah.verify_phone_number.VerifyResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIFunctions {

    @POST("public/api/user/login")
    Call<LoginResponse> loginCall(@Body Login login);

    @POST("public/api/user/verifyOTP")
    Call<VerifyResponseModel> verifyOtpCall(@Body Login login);

    @POST("public/api/user/resendOTP")
    Call<LoginResponse> resendOtpCall(@Body Login login);
}
