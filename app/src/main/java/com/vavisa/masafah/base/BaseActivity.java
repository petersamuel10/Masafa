package com.vavisa.masafah.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vavisa.masafah.util.dialogs.ConnectionMessage;
import com.vavisa.masafah.util.dialogs.FailedMessage;
import com.vavisa.masafah.util.dialogs.ProgressDialog;

public class BaseActivity extends AppCompatActivity implements BaseView {
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
    }

    @Override
    public void hideErrorConnection() {
        ConnectionMessage.getInstance().dismiss();
    }

    @Override
    public void showMissingData() {
        FailedMessage.getInstance().show(this);
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
    public void showMessage() {
        Toast.makeText(this, "enter mobile number", Toast.LENGTH_SHORT).show();
    }
}
