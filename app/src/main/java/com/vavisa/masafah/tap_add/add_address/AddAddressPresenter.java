package com.vavisa.masafah.tap_add.add_address;

import android.util.Log;

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
                .enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().Address_id((response.body()).get("address_id"));
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                        getView().hideProgress();
                        if (t instanceof HttpException) {
                            ResponseBody body = ((HttpException) t).response().errorBody();
                            Log.d("error", body.toString());
                        }
                    }
                });
    }
}
