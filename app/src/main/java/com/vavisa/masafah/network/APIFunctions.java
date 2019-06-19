package com.vavisa.masafah.network;

import com.vavisa.masafah.login.CountryModel;
import com.vavisa.masafah.login.Login;
import com.vavisa.masafah.login.LoginResponse;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.tap_profile.TermsAndCondition.TermsModel;
import com.vavisa.masafah.verify_phone_number.VerifyResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIFunctions {

    @GET("public/api/user/getCountries")
    Call<ArrayList<CountryModel>> countryCall();

    @POST("public/api/user/login")
    Call<LoginResponse> loginCall(@Body Login login);

    @POST("public/api/user/verifyOTP")
    Call<VerifyResponseModel> verifyOtpCall(@Body Login login);

    @POST("public/api/user/resendOTP")
    Call<LoginResponse> resendOtpCall(@Body Login login);

    @GET("public/api/user/getTermsAndConditions")
    Call<TermsModel> termsCall();

    @GET("public/api/user/getShipments")
    Call<ShipmentModel> getShipmentCall(@Header("Authorization") String authorization);



}
