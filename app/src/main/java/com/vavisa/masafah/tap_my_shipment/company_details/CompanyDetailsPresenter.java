package com.vavisa.masafah.tap_my_shipment.company_details;

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

public class CompanyDetailsPresenter extends BasePresenter<CompanyDetailsViews> {

    public void getCompanyDetails(String com_id) {
        getView().showProgress();
        APIManager.getInstance().getAPI().getCompanyDetailsCall(Preferences.getInstance().getString("access_token"), com_id).
                enqueue(new Callback<CompanyModel>() {
                    @Override
                    public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().displayCompanyDetails(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<CompanyModel> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                        if (t instanceof HttpException) {
                            ResponseBody body = ((HttpException) t).response().errorBody();
                            Log.d("error", body.toString());
                        }
                    }
                });
    }

    public void rateCompanyDetails(RatingModel ratingModel) {
        getView().showProgress();
        APIManager.getInstance().getAPI().rateCompanyCall(Preferences.getInstance().getString("access_token"), ratingModel).
                enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().rationResponse();
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
