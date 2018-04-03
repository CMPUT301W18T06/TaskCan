package com.example.n8tech.taskcan.Models;

import android.content.Context;

/**
 * Notification Content stores notification details: context, channel ID, title, and description.
 *
 * @author CMPUT301W18T06
 */

public class NotificationContent {
    private Context context;
    private String channelID;
    private CharSequence title;
    private CharSequence description;

    public NotificationContent(Context c, String channelID, String title, String description) {
        this.context = c;
        this.channelID = channelID;
        this.title = title;
        this.description = description;
    }

    public Context getContext() {
        return this.context;
    }

    public String getChannelID() {
        return this.channelID;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public CharSequence getDescription() {
        return this.description;
    }
}
