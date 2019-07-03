package com.vavisa.masafah.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.vavisa.masafah.network.InternetConnectionListener;
import com.vavisa.masafah.util.dialogs.ConnectionMessage;
import com.vavisa.masafah.util.dialogs.FailedMessage;
import com.vavisa.masafah.util.dialogs.ProgressDialog;

import retrofit2.Response;

public class BaseActivity extends AppCompatActivity implements BaseView, InternetConnectionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void start(Class<? extends BaseActivity> activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void showErrorConnection() {
        ConnectionMessage.getInstance().show(this);
        Log.d("here", "adini btl3 al progress");
    }

    @Override
    public void hideErrorConnection() {
        ConnectionMessage.getInstance().dismiss();
    }

    @Override
    public void showMissingData(Response response) {
        FailedMessage.getInstance().show(this, response);

    }

    @Override
    public void hideMissingData() {
        FailedMessage.getInstance().dismiss();
    }

    @Override
    public void showProgress() {
        ProgressDialog.getInstance().show(this);
    }

    @Override
    public void hideProgress() {
        ProgressDialog.getInstance().dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onInternetUnavailable() {
        runOnUiThread(() -> {
            ConnectionMessage.getInstance().show(this);
        });
    }
}
