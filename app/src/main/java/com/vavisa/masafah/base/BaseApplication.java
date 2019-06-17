package com.vavisa.masafah.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.onesignal.OneSignal;
import com.vavisa.masafah.R;
import com.vavisa.masafah.util.Constants;

import io.fabric.sdk.android.Fabric;

public class BaseApplication extends Application {

    public static String error_msg;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this;

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
                        Constants.oneSignalToken = userId;
                        if (registrationId != null)
                            Log.d("debug", "registrationId:" + registrationId);
                    }
                });
    }

    public static Context getAppContext() {
        return context;
    }
}
