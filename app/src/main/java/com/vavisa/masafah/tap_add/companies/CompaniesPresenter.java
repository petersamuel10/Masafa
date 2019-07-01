package com.vavisa.masafah.tap_add.companies;

import android.util.Log;

import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyModel;
import com.vavisa.masafah.util.Preferences;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class CompaniesPresenter extends BasePresenter<CompaniesView> {

    public void getCompanies() {

        getView().showProgress();
        APIManager.getInstance().getAPI().getCompaniesCall(Preferences.getInstance().getString("access_token")).enqueue(new Callback<ArrayList<CompanyModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CompanyModel>> call, Response<ArrayList<CompanyModel>> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().companies(response.body());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<ArrayList<CompanyModel>> call, Throwable t) {
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
