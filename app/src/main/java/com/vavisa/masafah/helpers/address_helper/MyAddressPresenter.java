package com.vavisa.masafah.helpers.address_helper;

import android.util.Log;

import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.util.Preferences;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MyAddressPresenter extends BasePresenter<MyAddressView> {

    public void getAddresses() {
        getView().showProgress();
        APIManager.getInstance().getAPI().getMyAddressesCall(Preferences.getInstance().getString("access_token"))
                .enqueue(new Callback<ArrayList<AddressModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AddressModel>> call, Response<ArrayList<AddressModel>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().address(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AddressModel>> call, Throwable t) {
                        getView().hideProgress();
                        if (t instanceof HttpException) {
                            ResponseBody body = ((HttpException) t).response().errorBody();
                            Log.d("error", body.toString());
                        }
                    }
                });
    }

}
