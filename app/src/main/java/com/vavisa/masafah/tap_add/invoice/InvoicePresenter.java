package com.vavisa.masafah.tap_add.invoice;

import android.util.Log;

import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.tap_add.AddShipmentModel;
import com.vavisa.masafah.util.Constants;
import com.vavisa.masafah.util.Preferences;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

class InvoicePresenter extends BasePresenter<InvoiceView> {

     void addShipment(AddShipmentModel addShipmentModel) {
        getView().showProgress();
        APIManager.getInstance().getAPI().addShipmentCall(Preferences.getInstance().getString("access_token"),
                addShipmentModel).enqueue(new Callback<HashMap<String, String>>() {
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

     void editShipment(AddShipmentModel addShipmentModel) {
        getView().showProgress();
        APIManager.getInstance().getAPI().editShipmentCall(Preferences.getInstance().getString("access_token"),
                addShipmentModel).enqueue(new Callback<HashMap<String, String>>() {
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

     void getPrice(String governorate_id_from, String governorate_id_to, String city_id_from, String city_id_to) {
        getView().showProgress();
        APIManager.getInstance().getAPI().priceCall(Preferences.getInstance().getString("access_token"),
                governorate_id_from, governorate_id_to, city_id_from, city_id_to)
                .enqueue(new Callback<HashMap<String, String>>() {
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
