package com.vavisa.masafah.application;

import android.app.Application;
import android.util.Log;

import com.onesignal.OneSignal;

public class MyApplication extends Application {

  private static MyApplication instance;

  @Override
  public void onCreate() {
    super.onCreate();
    OneSignal.startInit(this)
        // .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
        // .setNotificationReceivedHandler(new MyNotificationReceivedHandler())
        .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
        .unsubscribeWhenNotificationsAreDisabled(true)
        .init();

    //        OneSignal.idsAvailable(
    //                new OneSignal.IdsAvailableHandler() {
    //                    @Override
    //                    public void idsAvailable(String userId, String registrationId) {
    //                        Log.d("debug", "User:" + userId);
    //                        Constants.oneSignalToken = userId;
    //                        if (registrationId != null)
    //                            Log.d("debug", "registrationId:" + registrationId);
    //                    }
    //                });

    instance = this;
  }

  public static synchronized MyApplication getInstance() {
    return instance;
  }
}
