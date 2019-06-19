package com.vavisa.masafah.verify_phone_number;

import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.login.Login;
import com.vavisa.masafah.login.LoginResponse;
import com.vavisa.masafah.network.APIManager;

import retrofit2.Call;
import retrofit2.Callback;
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
                else if (response.code() == 422 || response.code() == 417) {
                    getView().showMissingData(response);
                }
            }

            @Override
            public void onFailure(Call<VerifyResponseModel> call, Throwable t) {

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

            }
        });

    }
}
