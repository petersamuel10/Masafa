package com.vavisa.masafah.verify_phone_number;

import android.util.Log;

import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.login.Login;
import com.vavisa.masafah.login.LoginResponse;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.util.Preferences;
import com.vavisa.masafah.verify_phone_number.model.VerifyResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class VerifyPresenter extends BasePresenter<VerifyViews> {

    public void verify_opt(Login login) {

        getView().showProgress();
        APIManager.getInstance().getAPI().verifyOtpCall(login).enqueue(new Callback<VerifyResponseModel>() {
            @Override
            public void onResponse(Call<VerifyResponseModel> call, Response<VerifyResponseModel> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().verify_opt(response.body());
                else {
                    getView().showMissingData(response);
                    getView().clearEditText();
                }
            }

            @Override
            public void onFailure(Call<VerifyResponseModel> call, Throwable t) {
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
                getView().hideProgress();
            }
        });
    }

    public void update_mobile_verify(Login login) {

        getView().showProgress();
        APIManager.getInstance().getAPI().updateMobileNumberCall(Preferences.getInstance().getString("access_token"),
                login).enqueue(new Callback<VerifyResponseModel>() {
            @Override
            public void onResponse(Call<VerifyResponseModel> call, Response<VerifyResponseModel> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().verify_opt(response.body());
                else {
                    getView().showMissingData(response);
                    getView().clearEditText();
                }
            }

            @Override
            public void onFailure(Call<VerifyResponseModel> call, Throwable t) {
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
                getView().hideProgress();
            }
        });
    }

    public void resendOTP(Login login) {

        getView().showProgress();
        APIManager.getInstance().getAPI().resendOtpCall(login).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().OTP(response.body().getOtp());
                else if (response.code() == 422) {
                    getView().showMissingData(response);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
                getView().hideProgress();
            }
        });

    }
}
