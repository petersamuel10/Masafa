package com.vavisa.masafah.tap_add.invoice;

import android.util.Log;

import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.util.Constants;
import com.vavisa.masafah.util.Preferences;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Presenter extends BasePresenter<View> {

    public void addShipment() {
        getView().showProgress();
        APIManager.getInstance().getAPI().addShipmentCall(Preferences.getInstance().getString("access_token"),
                Constants.addShipmentModel).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().handleAddShipment(response.body().get("message"));
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

    public void editShipment() {
        getView().showProgress();
        APIManager.getInstance().getAPI().editShipmentCall(Preferences.getInstance().getString("access_token"),
                Constants.addShipmentModel).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().handleAddShipment(response.body().get("message"));
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
