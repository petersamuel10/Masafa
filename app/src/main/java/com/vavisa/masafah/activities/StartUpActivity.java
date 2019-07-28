package com.vavisa.masafah.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.login.LoginActivity;
import com.vavisa.masafah.util.Constants;
import com.vavisa.masafah.util.Preferences;

import java.util.Locale;

public class StartUpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLocalization();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        Constants.LANGUAGE = (Locale.getDefault().getDisplayLanguage().equals("English")) ? "en" : "ar";
        Thread timerThread =
                new Thread() {
                    public void run() {
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                        } finally {
                            if (Preferences.getInstance().isHasKey("access_token"))
                                startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                            else
                                startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
                        }
                    }
                };
        timerThread.start();
    }

    private void setLocalization() {
        String lang;
        if (Preferences.getInstance().isHasKey("lan"))
            lang = (Preferences.getInstance().getString("lan").equals("English")) ? "en" : "ar";
        else {
            lang = "en";
            Preferences.getInstance().putString("lan", "English");
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
