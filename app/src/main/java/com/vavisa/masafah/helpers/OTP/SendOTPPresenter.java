package com.vavisa.masafah.helpers.OTP;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.login.LoginActivity;
import com.vavisa.masafah.login.LoginModel;
import com.vavisa.masafah.network.APIManager;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class SendOTPPresenter extends BasePresenter<OTPViews> {

    public void sendOtp(Activity activity, String phone_number) {

        getView().showProgress();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,
                30,
                TimeUnit.SECONDS,
                activity,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        //   signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        getView().hideProgress();
                        if (e instanceof FirebaseAuthInvalidCredentialsException)
                            getView().showMessage(BaseApplication.getAppContext().getString(R.string.please_enter_valid_mobile_number));
                    }

                    @Override
                    public void onCodeSent(String verification_id, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verification_id, forceResendingToken);
                        getView().hideProgress();
                        getView().handleVerification_id(verification_id);
                    }
                }
        );

    }

    public void getCountries() {
        getView().showProgress();
        APIManager.getInstance().getAPI().countryCall().enqueue(new Callback<ArrayList<CountryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CountryModel>> call, Response<ArrayList<CountryModel>> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().countries(response.body());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<ArrayList<CountryModel>> call, Throwable t) {
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
