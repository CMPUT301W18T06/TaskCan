package com.example.n8tech.taskcan.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.n8tech.taskcan.SyncDialogActivity;

/**
 * Created by AlanJ on 2018-03-23.
 */

public class SyncService extends IntentService {
    public static final String SYNC_SERVICE_NUM_OF_SYNC = "SYNC_SERVICE_NUM_OF_SYNC";

    private boolean connectionStatus;
    private ConnectivityManager connManager;
    private NetworkInfo mWifi;
    public SyncService() {
        super("SyncService");
        this.connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        this.mWifi = this.connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        this.connectionStatus = this.mWifi.isConnected();
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
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.mWifi.isConnected() && !this.connectionStatus) {
                this.connectionStatus = true;
                if (this.existsUnSyncedData())
                    this.launchDialogActivity();
            } else {
                this.connectionStatus = false;
            }
        }
    }

    private boolean existsUnSyncedData() {
        return false;
    }

    private void launchDialogActivity() {
        // https://stackoverflow.com/questions/3606596/android-start-activity-from-service
        Intent dialogIntent = new Intent(this, SyncDialogActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }
}
