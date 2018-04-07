package com.example.n8tech.taskcan.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.n8tech.taskcan.Controller.NotificationController;
import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.NotificationContent;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;

import java.util.ArrayList;

/**
 * Created by AlanJ on 2018-03-22.
 */

public class NotificationService extends IntentService {
    private final String TITLE = "New Bid";
    private String description;
    public NotificationService() {
        super("NotificationService");
    }
    private User currentUser = CurrentUserSingleton.getUser();
    private TaskList prevTaskList = new TaskList();
    private TaskList currentTaskList;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while(true) {
            //  put to sleep to make sure the android device does not donote
            //  all of its resources here
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Task task : currentUser.getMyTaskList()){
                prevTaskList.addTask(task);
            }

            if(this.haveNewBids()) {
                NotificationContent content = new NotificationContent(getApplicationContext(), NotificationController.ANDROID_CHANNEL_ID,
                        this.TITLE, this.description);
                NotificationController controller = new NotificationController(content);
                controller.alert(1);
            }
        }
    }

    private boolean haveNewBids() {
        currentTaskList = currentUser.getMyTaskList();
        for (Task task : currentTaskList){
            if (task.getBidList().equals(prevTaskList.getTaskAtIndex(currentTaskList.getIndexOfTask(task)).getBidList()) != true){
                String taskTitle = task.getTaskTitle();
                String newBidder = task.getBidList().getBid(task.getBidList().getSize()-1).getBidUsername();
                String newBidAmount = String.valueOf(task.getBidList().getBid(task.getBidList().getSize()-1).getBidAmount());
                description = newBidder + " offers you $" + newBidAmount + " for " + taskTitle + ".";
                return true;
            }
        }
        return false;
    }
}
