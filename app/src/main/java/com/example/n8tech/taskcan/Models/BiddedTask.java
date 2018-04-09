package com.example.n8tech.taskcan.Models;

import android.util.Log;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * BiddedTask represents a task bidded on by the current user.
 * This encapsulates fields and methods related to tasks the user has bidded on.
 *
 * @see Task
 * @see Bid
 * @author CMPUT301W18T06
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

    /**
     * Creates a BiddedTask object with the minimum details as specified by requirements.
     *
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

    /** @return Task name */
    public String getTaskTitle() { return this.taskTitle; }

    /** @param taskTitle String representing the task name */
    public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle; }

    /** @return Task description */
    public String getDescription() { return this.description; }

    /** @param description String representing the task description */
    public void setDescription(String description) { this.description = description; }

    /** @return Task ID */
    public String getTaskId() { return this.taskId; }

    /** @param taskId String representing Task ID to be set */
    public void setTaskId(String taskId) { this.taskId = taskId; }

    /** @return Task Requester Username */
    public String getOwnerUsername() { return this.ownerUsername; }

    /** @param ownerUsername String representing the owner username to be set */
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }

    /** @return Task requester user ID */
    public String getOwnerId() { return this.ownerId; }

    /** @param ownerId Task requester user ID to be set */
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    /** @return Task category */
    public String getCategory() { return this.category; }

    /** @param category Task category to be set */
    public void setCategory(String category) { this.category = category; }

    /** @return maximum bid value */
    public double getMaximumBid() { return this.maximumBid; }

    /** @param maximumBid The maximum bid amount to be set in dollar value */
    public void setMaximumBid(double maximumBid) { this.maximumBid = maximumBid; }

    /** @return bid amount made by the current user on the task */
    public double getMyBidAmount() { return this.myBidAmount; }

    /** @param myBidAmount bid amount made by the current user on the task */
    public void setMyBidAmount(double myBidAmount) { this.myBidAmount = myBidAmount; }

    /** @return true if task status is set to "Completed" */
    public boolean isTaskCompleted() { return this.taskCompleted; }

    /** @param taskCompleted true if Task is completed and status is to be changed*/
    public void setTaskCompleted(boolean taskCompleted) { this.taskCompleted = taskCompleted; }

    /** @return Task status */
    public String getStatus() { return this.status; }

    /** @param status Task status to be set */
    public void setStatus(String status) { this.status = status; }

    /** @return integer editCount */
    public Integer getEditCount() { return this.editCount; }

    /** @param editCount integer */
    public void setEditCount(Integer editCount) { this.editCount = editCount; }

    /**
     * Creates a new BiddedTask object based on the values of the task bidded on and the bid made.
     * @param task Task bidded on.
     * @param bid Bid made by the current user.
     * @return BiddedTask with task and bid details.
     */
    public void makeBiddedTask(Task task, Bid bid) {
        this.taskTitle = task.getTaskTitle();
        this.description = task.getDescription();
        this.taskId = task.getId();
        this.ownerUsername = task.getOwnerUsername();
        this.ownerId = task.getOwnerId();
        this.category = task.getCategory();
        this.maximumBid = task.getMaximumBid();
        this.myBidAmount = bid.getBidAmount();
        this.taskCompleted = task.getTaskCompleted();
        this.status = task.getStatus();
        this. editCount = task.getEditCount();
    }

    /**
      * Returns a task from Elastic Search.
      * @return Task object
     */
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
