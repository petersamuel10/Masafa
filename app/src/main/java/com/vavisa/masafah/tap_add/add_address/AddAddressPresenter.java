package com.vavisa.masafah.tap_add.add_address;

import android.util.Log;

import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.util.Preferences;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class AddAddressPresenter extends BasePresenter<AddAddressView> {

    public void AddAddress(AddressModel addressModel) {
        getView().showProgress();
        APIManager.getInstance().getAPI().addAddressCall(Preferences.getInstance().getString("access_token"), addressModel)
                .enqueue(new Callback<AddressModel>() {
                    @Override
                    public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().addNewAddress(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<AddressModel> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                        if (t instanceof HttpException) {
                            ResponseBody body = ((HttpException) t).response().errorBody();
                            Log.d("error", body.toString());
                        }
                    }
                });
    }

    public void getAddressById(String address_id) {
        getView().showProgress();
        APIManager.getInstance().getAPI().getAddressDetailsCall(Preferences.getInstance().getString("access_token"), address_id)
                .enqueue(new Callback<AddressModel>() {
                    @Override
                    public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().getAddressDetails(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<AddressModel> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                        if (t instanceof HttpException) {
                            ResponseBody body = ((HttpException) t).response().errorBody();
                            Log.d("error", body.toString());
                        }
                    }
                });
    }

    public void editAddress(AddressModel addressModel) {
        getView().showProgress();
        APIManager.getInstance().getAPI().editAddressCall(Preferences.getInstance().getString("access_token"), addressModel)
                .enqueue(new Callback<AddressModel>() {
                    @Override
                    public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().getEditAddress(addressModel);
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<AddressModel> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                        if (t instanceof HttpException) {
                            ResponseBody body = ((HttpException) t).response().errorBody();
                            Log.d("error", body.toString());
                        }
                    }
                });
    }
}
