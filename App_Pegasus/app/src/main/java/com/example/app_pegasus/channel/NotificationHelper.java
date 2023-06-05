package com.example.app_pegasus.channel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.app_pegasus.R;

public class NotificationHelper extends ContextWrapper {

    private static final String CHANNEL_ID = "com.example.app_pegasus.channel";
    private static final String CHANNEL_NAME = "app_pegasus";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels(){
        NotificationChannel mNotificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        mNotificationChannel.enableLights(true);
        mNotificationChannel.enableVibration(true);
        mNotificationChannel.setLightColor(Color.GRAY);
        mNotificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(mNotificationChannel);
    }

    public NotificationManager getManager(){
        if(mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotification(String title, String body, PendingIntent intent, Uri soundUri){
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID).setContentTitle(title)
                .setContentText(body).setAutoCancel(true).setSound(soundUri).setContentIntent(intent)
                .setSmallIcon(R.drawable.ic_notification).setStyle(new Notification.BigTextStyle().bigText(body).setBigContentTitle(title));
    }
    public NotificationCompat.Builder getNotificationOldAPI(String title, String body, PendingIntent intent, Uri soundUri){
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID).setContentTitle(title)
                .setContentText(body).setAutoCancel(true).setSound(soundUri).setContentIntent(intent)
                .setSmallIcon(R.drawable.ic_notification).setStyle(new NotificationCompat.BigTextStyle().bigText(body).setBigContentTitle(title));
    }
}
