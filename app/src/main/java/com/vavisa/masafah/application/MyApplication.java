package com.vavisa.masafah.application;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.onesignal.OneSignal;
import com.vavisa.masafah.util.Constants;
import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        OneSignal.startInit(this)
                // .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                // .setNotificationReceivedHandler(new MyNotificationReceivedHandler())
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

        instance = this;
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }
}
