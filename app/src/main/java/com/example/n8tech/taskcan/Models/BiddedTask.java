package com.example.n8tech.taskcan.Models;

import android.util.Log;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by m_qui on 4/8/2018.
 */

public class BiddedTask {

    private String taskTitle;
    private String description;
    private String taskId;
    private String ownerUsername;
    private String ownerId;
    private String category;
    private double myBidAmount;
    private double maximumBid;
    private boolean taskCompleted;
    private String status;
    private Integer editCount;

    /** Empty constructor */
    public BiddedTask(){
        this.taskTitle="";
        this.description="";
        this.taskId = "";
        this.ownerUsername = "";
        this.ownerId = "";
        this.category = "Other";
        this.myBidAmount = -1;
        this.maximumBid = -1;
        this.taskCompleted = false;
        this.status = "Bidded";
        this.editCount = 1;
    }

    /** Creates a Task object with the minimum details as specified by requirements.
     * A new BidList is created, task status is set to "Requested", task completed is set to false
     * and default values are set.
     * @param name name of the task with a maximum length of 30 characters
     * @param description brief description of the task with a maximum length of 300 characters
     * @param ownerUsername username of the task requester
     * @param ownerId task requester ID
     * @param category category the task belongs to
     */
    public BiddedTask(String name, String description, String taskId, String ownerUsername, String ownerId, String category) {

        this.taskTitle = name;
        this.description = description;
        this.taskId = taskId;
        this.ownerUsername = ownerUsername;
        this.ownerId = ownerId;
        this.category = category;
        this.myBidAmount = -1;
        this.maximumBid = -1;
        this.taskCompleted = false;
        this.status = "Bidded";
        this.editCount = 1;

    }

    public String getTaskTitle() { return this.taskTitle; }

    public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle; }

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public String getTaskId() { return this.taskId; }

    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getOwnerUsername() { return this.ownerUsername; }

    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }

    public String getOwnerId() { return this.ownerId; }

    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getCategory() { return this.category; }

    public void setCategory(String category) { this.category = category; }

    public double getMaximumBid() { return this.maximumBid; }

    public void setMaximumBid(double maximumBid) { this.maximumBid = maximumBid; }

    public double getMyBidAmount() { return this.myBidAmount; }

    public void setMyBidAmount(double myBidAmount) { this.myBidAmount = myBidAmount; }

    public boolean isTaskCompleted() { return this.taskCompleted; }

    public void setTaskCompleted(boolean taskCompleted) { this.taskCompleted = taskCompleted; }

    public String getStatus() { return this.taskTitle; }

    public void setStatus(String status) { this.status = status; }

    public Integer getEditCount() { return this.editCount; }

    public void setEditCount(Integer editCount) { this.editCount = editCount; }

    public BiddedTask makeBiddedTask(Task task, Bid bid) {
        BiddedTask newBiddedTask = new BiddedTask();
        newBiddedTask.setTaskTitle(task.getTaskTitle());
        newBiddedTask.setDescription(task.getDescription());
        newBiddedTask.setTaskId(task.getId());
        newBiddedTask.setOwnerUsername(task.getOwnerUsername());
        newBiddedTask.setOwnerId(task.getOwnerId());
        newBiddedTask.setCategory(task.getCategory());
        newBiddedTask.setMaximumBid(task.getMaximumBid());
        newBiddedTask.setMyBidAmount(bid.getBidAmount());
        newBiddedTask.setTaskCompleted(task.getTaskCompleted());
        newBiddedTask.setStatus(task.getStatus());
        newBiddedTask.setEditCount(task.getEditCount());

        return newBiddedTask;
    }

    public Task makeTask() {
        ElasticsearchController.GetTask getTask
                = new ElasticsearchController.GetTask();
        getTask.execute(this.taskId);
        Task task = new Task(this.taskTitle, this.description, this.ownerUsername, this.ownerId, this.category);
        task.setId(this.taskId);
        task.setMaximumBid(this.maximumBid);
        task.setTaskCompleted(this.taskCompleted);
        task.setStatus(this.status);
        task.setEditCount(this.editCount);
        try {
            task = getTask.get();
        } catch (Exception e) {
            Log.i("Testing", e.toString());
        }
        return task;
    }
}
