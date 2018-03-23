package com.example.n8tech.taskcan.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by AlanJ on 2018-03-23.
 */

public class SyncService extends IntentService {
    public SyncService() {
        super("SyncService");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SyncService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        
    }
}
