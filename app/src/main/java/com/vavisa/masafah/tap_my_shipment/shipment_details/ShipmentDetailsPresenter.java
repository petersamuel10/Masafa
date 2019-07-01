package com.vavisa.masafah.tap_my_shipment.shipment_details;

import android.util.Log;

import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.util.Preferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ShipmentDetailsPresenter extends BasePresenter<ShipmentDetailsViews> {

    public void getShipmentDetails(String shipment_id) {
        getView().showProgress();
        APIManager.getInstance().getAPI().getShipmentDetailsCall(Preferences.getInstance().getString("access_token"), shipment_id).
                enqueue(new Callback<ShipmentModel>() {
                    @Override
                    public void onResponse(Call<ShipmentModel> call, Response<ShipmentModel> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().displayShipmentDetails(response.body());
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
    }
}
