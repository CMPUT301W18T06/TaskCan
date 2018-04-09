package com.example.n8tech.taskcan.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Controller.NotificationController;
import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.BiddedTask;
import com.example.n8tech.taskcan.Models.BiddedTaskList;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.NotificationContent;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by AlanJ on 2018-03-22.
 */

public class NotificationService extends IntentService {
    private boolean newNotif = false;
    private User currentUser;
    private User onlineUser;
    private User user = CurrentUserSingleton.getUser();
    private boolean online;

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
        while(true) {
            //  put to sleep to make sure the android device does not donote
            //  all of its resources here
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.currentUser = CurrentUserSingleton.getUser();
            this.onlineUser = new User();
            this.online = false;

            ElasticsearchController.GetUser getUser = new ElasticsearchController.GetUser();
            getUser.execute(user.getId());
            try {
                this.onlineUser = getUser.get();
                Log.i("Got user", onlineUser.getUsername());
                this.online = true;
            } catch (Exception e) {
                Log.i("Error", String.valueOf(e));
            }

            if(online) {
                BidList onlineBidList;
                BidList currentBidList;
                BidList totalBidList = new BidList();
                for (Task task : this.onlineUser.getMyTaskList()) {
                    int i = this.currentUser.getMyTaskList().getIndexOfTask(task);
                    onlineBidList = this.onlineUser.getMyTaskList().getTaskAtIndex(i).getBidList();
                    currentBidList = this.currentUser.getMyTaskList().getTaskAtIndex(i).getBidList();
                    for (Bid bid : onlineBidList) {
                        Log.i("onlineBidlist", bid.getBidId());
                        if (totalBidList.getBidIndex(bid) == -1) {
                            totalBidList.addBid(bid);
                        }
                    }
                    for (Bid bid : currentBidList) {
                        Log.i("current", bid.getBidId());
                        if (totalBidList.getBidIndex(bid) == -1) {
                            totalBidList.addBid(bid);
                        }
                    }
                    for (Bid bid : totalBidList) {
                        Log.i("Bid Username", bid.getBidId());
                        //Bid cancellation not used, disable to reduce risk
                        /*
                        if (onlineBidList.getBidIndex(bid) == -1) {
                            //Check for bid cancellation
                            Log.i("what", String.valueOf(onlineBidList.getBidIndex(bid)));
                            NotificationContent content = new NotificationContent(getApplicationContext(), NotificationController.ANDROID_CHANNEL_ID,
                                    "Cancelled Bid", bid.getBidUsername() + " has cancelled their bid on " + task.getTaskTitle());
                            NotificationController controller = new NotificationController(content);
                            controller.alert(1);
                        } else
                            */
                        if (currentBidList.getBidIndex(bid) == -1) {
                            //Check for bid addition
                            NotificationContent content = new NotificationContent(getApplicationContext(), NotificationController.ANDROID_CHANNEL_ID,
                                    "Added Bid", bid.getBidUsername() + " has bid "
                                    + String.format(Locale.CANADA,"$%.2f",bid.getBidAmount()) + " on " + task.getTaskTitle());
                            NotificationController controller = new NotificationController(content);
                            controller.alert(1);
                        } else {
                            Bid oldBid = currentBidList.getBid(currentBidList.getBidIndex(bid));
                            Bid onlineBid = onlineBidList.getBid(onlineBidList.getBidIndex(bid));
                            if (oldBid.getBidAmount() != onlineBid.getBidAmount()) {
                                //Check for bid update
                                NotificationContent content = new NotificationContent(getApplicationContext(), NotificationController.ANDROID_CHANNEL_ID,
                                        "Updated Bid", bid.getBidUsername()
                                        + " has updated their bid to " + String.format(Locale.CANADA,"$%.2f",bid.getBidAmount())
                                        + " on " + task.getTaskTitle());
                                NotificationController controller = new NotificationController(content);
                                controller.alert(1);
                            }
                        }
                    }
                }
                CurrentUserSingleton.setUser(this.onlineUser);
            }
        }
    }
}
