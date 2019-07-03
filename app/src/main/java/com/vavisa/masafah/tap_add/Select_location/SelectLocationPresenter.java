package com.vavisa.masafah.tap_add.Select_location;

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

public class SelectLocationPresenter extends BasePresenter<SelectLocationViews> {

    public void getPrice() {
        getView().showProgress();
        APIManager.getInstance().getAPI().priceCall(Preferences.getInstance().getString("access_token")).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().displayPrice(response.body().get("price"));
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

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
