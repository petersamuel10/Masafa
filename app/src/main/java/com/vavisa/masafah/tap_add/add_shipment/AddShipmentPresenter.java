package com.vavisa.masafah.tap_add.add_shipment;

import android.util.Log;

import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.util.Preferences;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class AddShipmentPresenter extends BasePresenter<AddShipmentView> {

    public void getCategories() {

        getView().showProgress();
        APIManager.getInstance().getAPI().getCategoriesCall(Preferences.getInstance().getString("access_token")).enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().categories(response.body());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {

                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
                getView().hideProgress();
            }
        });
    }
}
