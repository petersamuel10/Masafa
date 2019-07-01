package com.vavisa.masafah.tap_my_shipment.my_shipments;

import android.util.Log;

import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.util.Preferences;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MyShipmentPresenter extends BasePresenter<MyShipmentsView> {

    public void getShipment() {

        getView().showProgress();
        APIManager.getInstance().getAPI().getShipmentCall(Preferences.getInstance().getString("access_token"))
                .enqueue(new Callback<HashMap<String, ArrayList<ShipmentModel>>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, ArrayList<ShipmentModel>>> call, Response<HashMap<String, ArrayList<ShipmentModel>>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().displayShipments(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, ArrayList<ShipmentModel>>> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                        if (t instanceof HttpException) {
                            ResponseBody body = ((HttpException) t).response().errorBody();
                            Log.d("error", body.toString());
                        }
                    }
                });

    }

    public void deleteShipment(int position, String shipment_id) {

        getView().showProgress();
        APIManager.getInstance().getAPI().deleteShipmentCall(Preferences.getInstance().getString("access_token"), shipment_id)
                .enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().deleteShipmentRes(position);
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

