package com.example.n8tech.taskcan.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
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
    private String TITLE;
    private String description;
    private boolean newNotif = false;
    private User currentUser;
    private User user = CurrentUserSingleton.getUser();
    private TaskList prevTaskList = new TaskList();
    private TaskList currentTaskList = new TaskList();
    private BidList prevBidList;



    public NotificationService() {
        super("NotificationService");
    }

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
        User currentUser = CurrentUserSingleton.getUser(); // change to ES
        while(true) {
            //  put to sleep to make sure the android device does not donote
            //  all of its resources here
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Task task : currentUser.getMyTaskList()){
                currentTaskList.addTask(task);
            }

            prevTaskList = user.getMyTaskList();

            checkUpdates();

            if(newNotif) {
                NotificationContent content = new NotificationContent(getApplicationContext(), NotificationController.ANDROID_CHANNEL_ID,
                        this.TITLE, this.description);
                NotificationController controller = new NotificationController(content);
                controller.alert(1);
            }
        }
    }

    private void checkUpdates() {
        haveNewBids();
        haveUpdatedBids();
        taskStatusChanged();
    }

    private void haveNewBids() {
        for (Task task : currentTaskList) {
            int i = currentTaskList.getIndexOfTask(task);
            prevBidList = prevTaskList.getTaskAtIndex(i).getBidList();
            if (task.getBidList().getSize() != prevBidList.getSize()) {
                String taskTitle = task.getTaskTitle();
                String newBidder = task.getBidList().getBid(task.getBidList().getSize() - 1).getBidUsername();
                String newBidAmount = String.valueOf(task.getBidList().getBid(task.getBidList().getSize() - 1).getBidAmount());
                TITLE = "New Bid";
                description = String.format("%s offers you %s for %s.", newBidder, newBidAmount, taskTitle);
                newNotif = true;
            }
        }
    }


    private boolean haveUpdatedBids() {
        for (Task task : currentTaskList) {
            if ((task.getBidList().getSize() == prevBidList.getSize()) && (task.getBidList().equals(prevBidList) != true)) {
                for (Bid bid : task.getBidList()) {
                    if (bid != prevBidList.getBid(task.getBidList().getBidIndex(bid))) {
                        String taskTitle = task.getTaskTitle();
                        String newBidder = bid.getBidUsername();
                        String newBidAmount = String.valueOf(bid.getBidAmount());
                        TITLE = "New Bid";
                        description = String.format("%s offers you %s for %s.", newBidder, newBidAmount, taskTitle);
                        newNotif = true;
                    }
                }
            }
        }
    }


    private boolean taskStatusChanged() {
        for Ta

    }
}
