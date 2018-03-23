package com.example.n8tech.taskcan.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.n8tech.taskcan.Controller.NotificationController;
import com.example.n8tech.taskcan.Models.NotificationContent;

/**
 * Created by AlanJ on 2018-03-22.
 */

public class NotificationService extends IntentService {
    private final String TITLE = "New Bid";
    private String description;
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
            if(this.haveNewBids()) {
                NotificationContent content = new NotificationContent(getApplicationContext(), NotificationController.ANDROID_CHANNEL_ID,
                        this.TITLE, this.description);
                NotificationController controller = new NotificationController(content);
                controller.alert(1);
            }
        }
    }

    private boolean haveNewBids() {
        /*
        * if (have new bids) {
        *   description = bidder name offers you bid amount for task title
        * }
        * */
        return false;
    }
}
