package com.example.app_pegasus.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.app_pegasus.R;
import com.example.app_pegasus.channel.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingClient extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");

        if(title != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                showNotificationApiOreo(title, body);
            } else{
                showNotification(title, body);
            }
        }
    }

    private void showNotification(String title, String body) {
        PendingIntent mPendingIntent = PendingIntent.getActivity(getBaseContext(), 0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notificationsound);
        NotificationHelper mNotificationHelper = new NotificationHelper(getBaseContext());
        NotificationCompat.Builder mBuilder = mNotificationHelper.getNotificationOldAPI(title, body, mPendingIntent, sound);
        mNotificationHelper.getManager().notify(1, mBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationApiOreo(String title, String body) {
        PendingIntent mPendingIntent = PendingIntent.getActivity(getBaseContext(), 0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        Uri sound =  Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notificationsound);
        NotificationHelper mNotificationHelper = new NotificationHelper(getBaseContext());
        Notification.Builder mBuilder = mNotificationHelper.getNotification(title, body, mPendingIntent, sound);
        mNotificationHelper.getManager().notify(1, mBuilder.build());
    }
}
