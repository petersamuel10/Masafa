package com.vavisa.masafah.tap_profile.TermsAndCondition;

import android.util.Log;

import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class TermsPresenter extends BasePresenter<TermsView> {

    public void getTerms() {
        getView().showProgress();
        APIManager.getInstance().getAPI().termsCall().enqueue(new Callback<TermsModel>() {
            @Override
            public void onResponse(Call<TermsModel> call, Response<TermsModel> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().Terms(response.body().getTerms_and_conditions());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<TermsModel> call, Throwable t) {
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
                getView().hideProgress();
            }
        });
    }
}
