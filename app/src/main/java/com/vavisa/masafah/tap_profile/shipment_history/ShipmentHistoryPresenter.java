package com.vavisa.masafah.tap_profile.shipment_history;

import android.util.Log;

import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.util.Preferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ShipmentHistoryPresenter extends BasePresenter<ShipmentHistoryView> {

   /* public void getShipments() {
        getView().showProgress();
        APIManager.getInstance().getAPI().getShipmentHistoryCall(Preferences.getInstance().getString("access_token")
        ).enqueue(new Callback<ShipmentModel>() {
            @Override
            public void onResponse(Call<ShipmentModel> call, Response<ShipmentModel> response) {
                getView().hideProgress();
                if(response.code() == 200)
                    getView().displayShipment();
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<ShipmentModel> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(BaseApplication.error_msg);
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
            }
        });
    }*/
}
