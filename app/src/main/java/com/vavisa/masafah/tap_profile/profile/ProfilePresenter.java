package com.vavisa.masafah.tap_profile.profile;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.vavisa.masafah.base.BaseApplication;
import com.vavisa.masafah.base.BasePresenter;
import com.vavisa.masafah.login.LoginResponse;
import com.vavisa.masafah.network.APIManager;
import com.vavisa.masafah.tap_profile.profile.model.EditProfileModel;
import com.vavisa.masafah.util.Preferences;
import com.vavisa.masafah.verify_phone_number.model.User;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ProfilePresenter extends BasePresenter<ProfileView> {

    // to send to verify mobile to use with resend otp
    String updated_mobile;

    public void getUserProfile() {
        getView().showProgress();
        APIManager.getInstance().getAPI().getProfileCall(Preferences.getInstance().getString("access_token")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().user(response.body());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(BaseApplication.error_msg);
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
            }
        });
    }

    public void updateProfile(EditProfileModel editProfileModel) {
        getView().showProgress();
        APIManager.getInstance().getAPI().updateProfileCall(Preferences.getInstance().getString("access_token"),
                editProfileModel).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().updateProfileResponse(response.body());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(BaseApplication.error_msg);
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
            }
        });
    }

    public void changeMobile(HashMap<String, String> mobile) {

        updated_mobile = mobile.get("mobile");
        getView().showProgress();
        APIManager.getInstance().getAPI().changeMobileNumberCall(Preferences.getInstance()
                .getString("access_token"), mobile).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().changeMobileResponse(updated_mobile, response.body().getOtp());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(BaseApplication.error_msg);
                if (t instanceof HttpException) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Log.d("error", body.toString());
                }
            }
        });
    }

    public void logout() {

        getView().showProgress();
        APIManager.getInstance().getAPI().logoutCall(Preferences.getInstance()
                .getString("access_token")).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                getView().hideProgress();
                if (response.code() == 200) {
                    // to open again app with the selection language
                    String current_lan = Preferences.getInstance().getString("lan");
                    Preferences.getInstance().clear();
                    Preferences.getInstance().putString("lan", current_lan);
                    getView().logout();
                } else
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

    public String getImageBase64FromView(ImageView imageView) {
        if (imageView.getDrawable() != null) {
            Bitmap defaultPhotoBitMap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            defaultPhotoBitMap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return "";
    }

}
