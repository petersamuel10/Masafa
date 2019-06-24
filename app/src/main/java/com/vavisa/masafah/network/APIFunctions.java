package com.vavisa.masafah.network;

import com.vavisa.masafah.login.CountryModel;
import com.vavisa.masafah.login.Login;
import com.vavisa.masafah.login.LoginResponse;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.tap_add.companies.CompanyModel;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.tap_profile.TermsAndCondition.TermsModel;
import com.vavisa.masafah.tap_profile.profile.model.EditProfileModel;
import com.vavisa.masafah.tap_profile.profile.model.UpdateProfileResponseM;
import com.vavisa.masafah.verify_phone_number.model.User;
import com.vavisa.masafah.verify_phone_number.model.VerifyResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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

    @GET("public/api/user/getCategories")
    Call<ArrayList<CategoryModel>> getCategoriesCall(@Header("Authorization") String authorization);

    @GET("public/api/user/getCompanies")
    Call<ArrayList<CompanyModel>> getCompaniesCall(@Header("Authorization") String authorization);

    @GET("public/api/user/getProfile")
    Call<User> getProfileCall(@Header("Authorization") String authorization);

    @PUT("public/api/user/updateProfile")
    Call<UpdateProfileResponseM> updateProfileCall(@Header("Authorization") String authorization, @Body EditProfileModel editProfileModel);

    @PATCH("public/api/user/changeMobileNumber")
    Call<LoginResponse> changeMobileNumberCall(@Header("Authorization") String authorization, @Body HashMap<String, String> mobile);

    @PATCH("public/api/user/updateMobileNumber")
    Call<VerifyResponseModel> updateMobileNumberCall(@Header("Authorization") String Authorization,@Body Login login);

    @GET("public/api/user/logout")
    Call<HashMap<String,String>> logoutCall(@Header("Authorization") String Authorization);


}
