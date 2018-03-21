package com.example.n8tech.taskcan.Controller;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import com.example.n8tech.taskcan.Models.NotificationContent;
import com.example.n8tech.taskcan.R;

/**
 * Created by AlanJ on 2018-03-21.
 */

public class NotificationController {
    private NotificationContent nContent;
    private NotificationCompat.Builder nBuilder;

    public NotificationController(NotificationContent notification) {
        this.nContent = notification;
        this.nBuilder = new NotificationCompat.Builder(this.nContent.getContext(), this.nContent.getChannelID())
                .setSmallIcon(R.drawable.alert)
                .setContentTitle(this.nContent.getTitle())
                .setContentText(this.nContent.getDescription())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public NotificationCompat.Builder getBuilder() {
        return this.nBuilder;
    }

    public Notification build() {
        return this.nBuilder.build();
    }
}
