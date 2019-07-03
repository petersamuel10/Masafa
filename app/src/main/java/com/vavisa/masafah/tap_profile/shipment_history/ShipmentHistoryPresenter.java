package com.vavisa.masafah.tap_profile.shipment_history;

import android.util.Log;

import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.util.Preferences;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ShipmentHistoryPresenter extends BasePresenter<ShipmentHistoryView> {

    public void getShipments() {
        getView().showProgress();
        APIManager.getInstance().getAPI().getShipmentHistoryCall(Preferences.getInstance().getString("access_token"))
                .enqueue(new Callback<ArrayList<ShipmentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ShipmentModel>> call, Response<ArrayList<ShipmentModel>> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().displayShipment(response.body());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<ArrayList<ShipmentModel>> call, Throwable t) {
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
