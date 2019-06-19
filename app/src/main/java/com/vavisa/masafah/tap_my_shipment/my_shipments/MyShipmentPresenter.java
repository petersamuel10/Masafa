package com.vavisa.masafah.tap_my_shipment.my_shipments;

import android.util.Log;

import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MyShipmentPresenter extends BasePresenter<MyShipmentsView> {

    public void getShipment(String user_access_token) {

        getView().showProgress();
        APIManager.getInstance().getAPI().getShipmentCall(user_access_token).enqueue(new Callback<ShipmentModel>() {
            @Override
            public void onResponse(Call<ShipmentModel> call, Response<ShipmentModel> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().Shipments(response.body());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<ShipmentModel> call, Throwable t) {
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
                getView().hideProgress();
            }
        });

    }
}
