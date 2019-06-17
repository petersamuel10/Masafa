package com.vavisa.masafah.login;

import android.util.Log;

import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.network.APIManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<LoginView> {

    String otp;

    public void loginFun(Login login) {

        getView().showProgress();
        APIManager.getInstance().getAPI().loginCall(login).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                getView().hideProgress();
                if (response.code() == 200) {
                    otp = response.body().getOtp();
                    getView().LoginResult(otp);
                } else {

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("response ttttt", body.toString());
                }
                getView().hideProgress();
            }
        });

    }
}
