package com.alllinkshare.pushnotifications;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

public class PN {
    private static final String TAG = "PN/Core";

    public static final String NEWS = "als_news";
    public static final String DELIVERY = "als_delivery";

    public static OnTokenReadyListener listener = null;

    public static void init(OnTokenReadyListener listener){
        FirebaseMessaging.getInstance().subscribeToTopic(PN.NEWS)
                .addOnCompleteListener(task -> {
                    String message = PN.NEWS+" channel subscribed successfully.";
                    if (!task.isSuccessful()) {
                        message = PN.NEWS+" channel subscription failed";
                    }
                    Log.d(TAG, message);
                });

        FirebaseMessaging.getInstance().subscribeToTopic(PN.DELIVERY)
                .addOnCompleteListener(task -> {
                    String message = PN.DELIVERY+" channel subscribed successfully.";
                    if (!task.isSuccessful()) {
                        message = PN.DELIVERY+" channel subscription failed";
                    }
                    Log.d(TAG, message);
                });

        PN.listener = listener;
    }

    public static void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    PN.listener.onTokenReady(token);

                    // Log and toast
                    String msg = "New token created: "+token;
                    Log.d(TAG, msg);
                });
    }

    public interface OnTokenReadyListener{
        void onTokenReady(String token);
    }
}