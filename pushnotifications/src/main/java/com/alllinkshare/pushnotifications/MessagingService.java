package com.alllinkshare.pushnotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "PN/Service";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        if(PN.listener != null){
            PN.listener.onTokenReady(s);
        }
        Log.d(TAG, "New token created: "+s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(
                remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody()
        );
    }

    private void showNotification(String title, String body){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    PN.NEWS, PN.NEWS, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PN.NEWS)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.icon)
                .setAutoCancel(true)
                .setContentText(body);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(111, builder.build());
    }
}