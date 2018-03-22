package com.example.n8tech.taskcan.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.example.n8tech.taskcan.Models.NotificationContent;
import com.example.n8tech.taskcan.R;

/**
 * Created by AlanJ on 2018-03-21.
 *
 * example of usage:
 NotificationContent content = new NotificationContent(v.getContext(), NotificationController.ANDROID_CHANNEL_ID,
 "Category Chosen", "You have chosen: " + position);
 NotificationController controller = new NotificationController(content);
 controller.alert(1);
 */

public class NotificationController {
    public static final String ANDROID_CHANNEL_ID = "com.example.n8tech.taskcan";
    public static final String ANDROID_CHANNEL_NAME = "TASKCAN NOTIFICATION CHANNEL";

    private NotificationContent nContent;
    private NotificationCompat.Builder nBuilder;
    private NotificationManager nManager;

    public NotificationController(NotificationContent notification) {
        this.nContent = notification;
        this.createManager();
        this.createChannel();
        this.createBuilder();
    }

    private void createManager() {
        this.nManager = (NotificationManager) this.nContent.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createBuilder() {
        this.nBuilder = new NotificationCompat.Builder(this.nContent.getContext(), this.nContent.getChannelID())
                .setSmallIcon(R.drawable.alert)
                .setContentTitle(this.nContent.getTitle())
                .setContentText(this.nContent.getDescription())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            androidChannel.enableLights(true);
            androidChannel.enableVibration(true);
            androidChannel.setLightColor(Color.GREEN);
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(androidChannel);
        }
    }

    private NotificationManager getManager() {
        return this.nManager;
    }

    public NotificationCompat.Builder getBuilder() {
        return this.nBuilder;
    }

    public Notification build() {
        return this.nBuilder.build();
    }

    public void alert(int notificationID) {
        this.nManager.notify(notificationID, this.build());
    }
}
