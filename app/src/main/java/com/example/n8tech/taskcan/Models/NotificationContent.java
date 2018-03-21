package com.example.n8tech.taskcan.Models;

import android.content.Context;

/**
 * Created by AlanJ on 2018-03-21.
 */

public class NotificationContent {
    private Context context;
    private String channelID;
    private CharSequence title;
    private CharSequence description;

    public NotificationContent(Context c, String channelID, String title, String description) {
        
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
