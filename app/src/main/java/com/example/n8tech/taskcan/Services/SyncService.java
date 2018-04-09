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
    private final long sync_time = 120000;
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
        fileIO = new FileIO();
        while (true) {
            if (duration >= sync_time || CurrentUserSingleton.getForcedSync()) {
                Intent i1 = new Intent(getApplicationContext(), NotificationService.class);
                stopService(i1);
                if (NetworkConnectionController.isConnected(this)) {
                    UserList cacheList = this.fileIO.loadFromFile(getApplicationContext());
                    for (User user : cacheList.getUsers()) {
                        if (user.getId().equals(CurrentUserSingleton.getUser().getId())) {
                            for (int i = 0; i < user.getMyTaskList().getSize(); i++) {
                                if (user.getMyTaskList().getTaskAtIndex(i).getId().matches("[0-9]+")) {
                                    ElasticsearchController.AddTask ec_addtask = new ElasticsearchController.AddTask();
                                    TaskList tl = user.getMyTaskList();
                                    Task t = tl.getTaskAtIndex(i);
                                    t.setId(null);
                                    ec_addtask.execute(t);
                                    try {
                                        ec_addtask.get();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    tl.setTaskAtIndex(i, t);
                                    user.setMyTaskList(tl);


                                    cacheList.delUser(user);
                                    cacheList.addUser(user);
                                    fileIO.saveInFile(getApplicationContext(), cacheList);
                                    ElasticsearchController.UpdateUser updateUser
                                            = new ElasticsearchController.UpdateUser();
                                    updateUser.execute(user);
                                    CurrentUserSingleton.setUser(user);
                                }
                                else {
                                    ElasticsearchController.GetTask ec = new ElasticsearchController.GetTask();
                                    ec.execute(user.getMyTaskList().getTaskAtIndex(i).getId());
                                    try {
                                        Task t_ec = ec.get();
                                        if (user.getMyTaskList().getTaskAtIndex(i).getEditCount() <= t_ec.getEditCount()) {
                                            user.getMyTaskList().setTaskAtIndex(i, t_ec);

                                            cacheList.delUser(user);
                                            cacheList.addUser(user);
                                            fileIO.saveInFile(getApplicationContext(), cacheList);
                                            CurrentUserSingleton.setUser(user);
                                        }
                                        else {
                                            ElasticsearchController.UpdateTask ecu = new ElasticsearchController.UpdateTask();
                                            TaskList taskList = user.getMyTaskList();
                                            Task task = taskList.getTaskAtIndex(i);
                                            task.setBidList(t_ec.getBidList());
                                            ecu.execute(task);
                                            ecu.get();

                                            cacheList.delUser(user);
                                            cacheList.addUser(user);
                                            fileIO.saveInFile(getApplicationContext(), cacheList);
                                            ElasticsearchController.UpdateUser updateUser
                                                    = new ElasticsearchController.UpdateUser();
                                            updateUser.execute(user);
                                            CurrentUserSingleton.setUser(user);
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                Log.i("sync service","====================> synced");
                start = System.currentTimeMillis();
                CurrentUserSingleton.setForceSync(false);

                Intent i2 = new Intent(getApplicationContext(), NotificationService.class);
                startService(i2);
            }
            end = System.currentTimeMillis();
            duration = end - start;
        }
    }
}
