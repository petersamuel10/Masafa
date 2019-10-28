package com.vavisa.masafah.verify_phone_number;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.login.LoginModel;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.util.Preferences;
import com.vavisa.masafah.verify_phone_number.model.VerifyResponseModel;

import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class VerifyPresenter extends BasePresenter<VerifyViews> {

    private FirebaseAuth mAuth;
    private LoginModel login_model;
    private Boolean isChangeMobile;


    VerifyPresenter(VerifyYourNumberActivity context, LoginModel loginModel, Boolean isChangeMobile) {
        this.login_model = loginModel;
        this.isChangeMobile = isChangeMobile;
        FirebaseApp.initializeApp(context);
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();
    }

    public void verifyOTP(String code) {
        getView().showProgress();
        try {
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(login_model.getVerification_id(), code);
            signInWithPhoneAuthCredential(phoneAuthCredential);
        } catch (Exception e) {
            getView().hideProgress();
            getView().showMessage(BaseApplication.error_msg);

        }
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((AppCompatActivity) getView(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                        mUser.getIdToken(true)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        String idToken = task1.getResult().getToken();
                                        login_model.setId_token(idToken);
                                        if (isChangeMobile) {
                                            isChangeMobile = false;
                                            update_mobile_verify();
                                        } else
                                            loginFun();
                                    } else {
                                        getView().clearEditText();
                                        getView().showMessage(BaseApplication.error_msg);
                                    }
                                });

                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            getView().hideProgress();
                            getView().clearEditText();
                            getView().showMessage(((AppCompatActivity) getView()).getString(R.string.wrong_code));
                        }
                    }
                });
    }

    public void loginFun() {

        APIManager.getInstance().getAPI().loginCall(login_model).enqueue(new Callback<VerifyResponseModel>() {
            @Override
            public void onResponse(Call<VerifyResponseModel> call, Response<VerifyResponseModel> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().userInfo(response.body());
                else if (response.code() == 422 || response.code() == 401)
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<VerifyResponseModel> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(BaseApplication.error_msg);
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }

            }
        });

    }

    public void update_mobile_verify() {

        getView().showProgress();
        APIManager.getInstance().getAPI().updateMobileNumberCall(Preferences.getInstance().getString("access_token"),
                login_model).enqueue(new Callback<VerifyResponseModel>() {
            @Override
            public void onResponse(Call<VerifyResponseModel> call, Response<VerifyResponseModel> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().userInfo(response.body());
                else {
                    getView().showMissingData(response);
                    getView().clearEditText();
                }
            }

            @Override
            public void onFailure(Call<VerifyResponseModel> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(BaseApplication.error_msg);
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }

            }
        });
    }

    public void resendOTP() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                login_model.getCountry_code() + login_model.getMobile(),
                30,
                TimeUnit.SECONDS,
                (AppCompatActivity) getView(),
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        //   signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException)
                            getView().showMessage(BaseApplication.getAppContext().getString(R.string.please_enter_valid_mobile_number));
                    }

                    @Override
                    public void onCodeSent(String verification_id, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verification_id, forceResendingToken);
                        login_model.setVerification_id(verification_id);
                    }
                }
        );

    }
}
