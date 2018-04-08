package com.example.n8tech.taskcan.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Controller.NetworkConnectionController;
import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;

import java.util.concurrent.ExecutionException;

/**
 * Created by AlanJ on 2018-03-23.
 */

public class SyncService extends IntentService {
    public static final String SYNC_SERVICE_NUM_OF_SYNC = "SYNC_SERVICE_NUM_OF_SYNC";
    private final long sync_time = 30000;
    private boolean connectionStatus;
    private FileIO fileIO;

    public SyncService() {
        super("SyncService");

        // Causes java.lang.NullPointerException
        // this.connectionStatus = NetworkConnectionController.isConnected(this);
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
        long start = System.currentTimeMillis(), end = 0, duration = 0;
        while (true) {
            if (duration >= sync_time || CurrentUserSingleton.getForcedSync()) {
                if (NetworkConnectionController.isConnected(this) && !this.connectionStatus) {
                    UserList cacheList = this.fileIO.loadFromFile(getApplicationContext());
                    for (User u : cacheList.getUsers()) {
                        if (u.getId().equals(CurrentUserSingleton.getUser().getId())) {
                            for (int i = 0; i < u.getMyTaskList().getSize(); i++) {
                                ElasticsearchController.GetTask ec = new ElasticsearchController.GetTask();
                                ec.execute(u.getMyTaskList().getTaskAtIndex(i).getId());
                                try {
                                    Task t_ec = ec.get();
                                    if (u.getMyTaskList().getTaskAtIndex(i).getEditCount() <= t_ec.getEditCount()) {
                                        u.getMyTaskList().setTaskAtIndex(i, t_ec);
                                    }
                                    else {
                                        ElasticsearchController.UpdateTask ecu = new ElasticsearchController.UpdateTask();
                                        ecu.execute(u.getMyTaskList().getTaskAtIndex(i));
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                            CurrentUserSingleton.setUser(u);
                            break;
                        }
                    }
                }
                start = System.currentTimeMillis();
                CurrentUserSingleton.setForceSync(false);
            }
            end = System.currentTimeMillis();
            duration = end - start;
        }
    }
}
