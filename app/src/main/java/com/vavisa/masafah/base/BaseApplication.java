package com.vavisa.masafah.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.onesignal.OneSignal;
import com.vavisa.masafah.R;
import com.vavisa.masafah.util.Constants;
import com.vavisa.masafah.util.Preferences;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class BaseApplication extends Application {

    public static String error_msg;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this;
        Constants.LANGUAGE = Locale.getDefault().getDisplayLanguage();
        setupOnSignal();
        error_msg = context.getString(R.string.error_occurred);
    }

    private void setupOnSignal() {

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.idsAvailable(
                new OneSignal.IdsAvailableHandler() {
                    @Override
                    public void idsAvailable(String userId, String registrationId) {
                        Log.d("debug", "User:" + userId);
                        Constants.ONE_SIGNAL_TOKEN = userId;
                        if (registrationId != null)
                            Log.d("debug", "registrationId:" + registrationId);
                    }
                });
    }

    public static void preventAccess() {
        Preferences.getInstance().remove("access_token");
        Preferences.getInstance().remove("user_id");
        Preferences.getInstance().remove("mobile");
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static Context getAppContext() {
        return context;
    }
}
