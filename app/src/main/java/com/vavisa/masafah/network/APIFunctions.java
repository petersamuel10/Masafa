package com.vavisa.masafah.network;

import com.vavisa.masafah.helpers.OTP.CountryModel;
import com.vavisa.masafah.login.LoginModel;
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
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIFunctions {

    @GET("getCountries")
    Call<ArrayList<CountryModel>> countryCall();

    @GET("getGovernoratesByCountry/{country_id}")
    Call<ArrayList<CountryModel>> governCall(@Header("Authorization") String authorization,@Path("country_id") String country_id);

    @GET("getCitiesByGovernorate/{governorate_id}")
    Call<ArrayList<CountryModel>> cityCall(@Header("Authorization") String authorization,@Path("governorate_id") String governorate_id);

    @POST("login")
    Call<VerifyResponseModel> loginCall(@Body LoginModel login);

    @GET("getTermsAndConditions")
    Call<TermsModel> termsCall();

    @GET("getShipments")
    Call<HashMap<String, ArrayList<ShipmentModel>>> getShipmentCall(@Header("Authorization") String authorization);

    @GET("getCategories")
    Call<ArrayList<CategoryModel>> getCategoriesCall(@Header("Authorization") String authorization);

    @GET("getCompanies")
    Call<ArrayList<CompanyModel>> getCompaniesCall(@Header("Authorization") String authorization);

    @GET("getProfile")
    Call<User> getProfileCall(@Header("Authorization") String authorization);

    @PUT("updateProfile")
    Call<User> updateProfileCall(@Header("Authorization") String authorization, @Body EditProfileModel editProfileModel);

    @PUT("updateMobileNumber")
    Call<VerifyResponseModel> updateMobileNumberCall(@Header("Authorization") String Authorization, @Body LoginModel login);

    @POST("logout")
    Call<HashMap<String, String>> logoutCall(@Header("Authorization") String Authorization, @Body HashMap<String, String> player_id);

    @POST("addAddress")
    Call<AddressModel> addAddressCall(@Header("Authorization") String Authorization, @Body AddressModel addressModel);

    @POST("addShipment")
    Call<HashMap<String, String>> addShipmentCall(@Header("Authorization") String Authorization, @Body AddShipmentModel addShipmentModel);

    @PUT("editShipment")
    Call<HashMap<String, String>> editShipmentCall(@Header("Authorization") String Authorization, @Body AddShipmentModel addShipmentModel);

    @GET("getAddresses")
    Call<ArrayList<AddressModel>> getMyAddressesCall(@Header("Authorization") String Authorization);

    @GET("getAddressById/{id}")
    Call<AddressModel> getAddressDetailsCall(@Header("Authorization") String Authorization, @Path("id") String address_id);

    @PUT("editAddress")
    Call<AddressModel> editAddressCall(@Header("Authorization") String Authorization, @Body AddressModel addressModel);

    @DELETE("deleteAddressById/{address_id}")
    Call<HashMap<String, String>> deleteAddressCall(@Header("Authorization") String Authorization, @Path("address_id") String address_id);

    @GET("getShipmentHistory")
    Call<ArrayList<ShipmentModel>> getShipmentHistoryCall(@Header("Authorization") String Authorization);

    @DELETE("deleteShipmentById/{shipment_id}")
    Call<HashMap<String, String>> deleteShipmentCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @GET("getShipmentDetails/{shipment_id}")
    Call<ShipmentModel> getShipmentDetailsCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @GET("getCompanyDetailsById/{company_id}")
    Call<CompanyModel> getCompanyDetailsCall(@Header("Authorization") String Authorization, @Path("company_id") String company_id);

    @POST("rateCompany")
    Call<HashMap<String, String>> rateCompanyCall(@Header("Authorization") String Authorization, @Body RatingModel ratingModel);

    @GET("getShipmentPrice")
    Call<HashMap<String, String>> priceCall(@Header("Authorization") String Authorization);
}
