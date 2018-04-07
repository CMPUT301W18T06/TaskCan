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

    /**
     * Creates an instance of NotificationContent
     * @param c Context object
     * @param channelID String representing the notification channel ID
     * @param title String representing the Notification Title
     * @param description String representing the Notification description
     */
    public NotificationContent(Context c, String channelID, String title, String description) {
        this.context = c;
        this.channelID = channelID;
        this.title = title;
        this.description = description;
    }

    /** @return Context */
    public Context getContext() {
        return this.context;
    }

    /** @return String representing channel ID */
    public String getChannelID() {
        return this.channelID;
    }

    /** @return String representing Notification title */
    public CharSequence getTitle() {
        return this.title;
    }

    /** @return String representing Notification description */
    public CharSequence getDescription() {
        return this.description;
    }
}
