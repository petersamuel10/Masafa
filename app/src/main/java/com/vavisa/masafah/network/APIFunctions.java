package com.vavisa.masafah.network;

import com.vavisa.masafah.login.CountryModel;
import com.vavisa.masafah.login.Login;
import com.vavisa.masafah.login.LoginResponse;
import com.vavisa.masafah.tap_add.AddShipmentModel;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyModel;
import com.vavisa.masafah.tap_my_shipment.company_details.RatingModel;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.tap_profile.TermsAndCondition.TermsModel;
import com.vavisa.masafah.tap_profile.profile.model.EditProfileModel;
import com.vavisa.masafah.verify_phone_number.model.User;
import com.vavisa.masafah.verify_phone_number.model.VerifyResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    Call<HashMap<String,ArrayList<ShipmentModel>>> getShipmentCall(@Header("Authorization") String authorization);

    @GET("public/api/user/getCategories")
    Call<ArrayList<CategoryModel>> getCategoriesCall(@Header("Authorization") String authorization);

    @GET("public/api/user/getCompanies")
    Call<ArrayList<CompanyModel>> getCompaniesCall(@Header("Authorization") String authorization);

    @GET("public/api/user/getProfile")
    Call<User> getProfileCall(@Header("Authorization") String authorization);

    @PUT("public/api/user/updateProfile")
    Call<User> updateProfileCall(@Header("Authorization") String authorization, @Body EditProfileModel editProfileModel);

    @PATCH("public/api/user/changeMobileNumber")
    Call<LoginResponse> changeMobileNumberCall(@Header("Authorization") String authorization, @Body HashMap<String, String> mobile);

    @PATCH("public/api/user/updateMobileNumber")
    Call<VerifyResponseModel> updateMobileNumberCall(@Header("Authorization") String Authorization,@Body Login login);

    @POST("public/api/user/logout")
    Call<HashMap<String,String>> logoutCall(@Header("Authorization") String Authorization,@Body HashMap<String, String> player_id);

    @POST("public/api/user/addAddress")
    Call<AddressModel> addAddressCall(@Header("Authorization") String Authorization, @Body AddressModel addressModel);

    @POST("public/api/user/addShipment")
    Call<HashMap<String, String>> addShipmentCall(@Header("Authorization") String Authorization, @Body AddShipmentModel addShipmentModel);

    @PUT("public/api/user/editShipment")
    Call<HashMap<String, String>> editShipmentCall(@Header("Authorization") String Authorization, @Body AddShipmentModel addShipmentModel);

    @GET("public/api/user/getAddresses")
    Call<ArrayList<AddressModel>> getMyAddressesCall(@Header("Authorization") String Authorization);

    @GET("public/api/user/getAddressById/{id}")
    Call<AddressModel> getAddressDetailsCall(@Header("Authorization") String Authorization, @Path("id") String address_id);

    @PUT("public/api/user/editAddress")
    Call<AddressModel> editAddressCall(@Header("Authorization") String Authorization, @Body AddressModel addressModel);

    @DELETE("public/api/user/deleteAddressById/{address_id}")
    Call<HashMap<String,String>> deleteAddressCall(@Header("Authorization") String Authorization, @Path("address_id") String address_id);

    @GET("public/api/user/getShipmentHistory")
    Call<ArrayList<ShipmentModel>> getShipmentHistoryCall(@Header("Authorization") String Authorization);

    @DELETE("public/api/user/deleteShipmentById/{shipment_id}")
    Call<HashMap<String,String>> deleteShipmentCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @GET("public/api/user/getShipmentDetails/{shipment_id}")
    Call<ShipmentModel> getShipmentDetailsCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @GET("public/api/user/getCompanyDetailsById/{company_id}")
    Call<CompanyModel> getCompanyDetailsCall(@Header("Authorization") String Authorization, @Path("company_id") String company_id);

    @POST("public/api/user/rateCompany")
    Call<HashMap<String,String>> rateCompanyCall(@Header("Authorization") String Authorization, @Body RatingModel ratingModel);

    @GET("public/api/user/getShipmentPrice")
    Call<HashMap<String,String>> priceCall(@Header("Authorization") String Authorization);
}
