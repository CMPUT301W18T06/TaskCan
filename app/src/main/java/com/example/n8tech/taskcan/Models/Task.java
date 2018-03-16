/*
 *  Copyright (c) 2018 Alexander Filbert, Carolyn Binns, Jeanna Somoza, JingMing Huang, Matthew Quigley, Nathanael Belayneh
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.n8tech.taskcan.Models;

import android.util.Log;
import java.util.UUID;

/**
 * Task represents a task from a requester
 * and stores task details.
 */

public class Task {
    final private int MAX_TITLE_NAME_LENGTH = 30;
    final private int MAX_DESCRIPTION_LENGTH = 30;

    private String taskTitle;
    private String description;
    private User owner; //TODO:  ******* possible recursive error ********* (SHOULD BE USERS UUID STRING)
    private String ownerDisplayName;
    private User provider;
    private double maximumBid;
    private String category;
    private BidList bidList;
    private String location;            // TODO change to geolocation variable
    private boolean taskCompleted;
    private String taskId;                 // TODO need unique task UUID
    private String taskStatus;



    public Task(){
        // default constructor
        this.taskTitle="";
        this.description="";
        this.maximumBid = -1;
        this.category = "Other";
        this.bidList = new BidList();
        this.location = null;
        this.taskCompleted = false;
        this.taskStatus = "Requested";

        // TODO create a unique task id. check elastic search to ensure its unique?
        this.taskId = UUID.randomUUID().toString();
        Log.i("uuid for task: ", this.taskId);
    }

    // minimum information needed to create a new task
    public Task(String name, String description, User owner, String ownerDisplayName, String category) {
        // TODO: length checking for name & description
        this.taskTitle = name;
        this.description = description;
        this.owner = owner;
        this.ownerDisplayName = ownerDisplayName;

        // set default values for a new task
        this.provider = null;
        //this.maximumBid = Double.POSITIVE_INFINITY;
        //Swapping since JSON can't handle infinity may swap back later
        this.maximumBid = -1;
        // this.currentBid = ?? set here to a default value or leave alone
        this.category = category;
        this.bidList = new BidList();
        this.location = null;
        this.taskCompleted = false;     // ie requested
        this.taskStatus = "Requested";

        // TODO create a unique task id. check elastic search to ensure its unique?
        this.taskId = UUID.randomUUID().toString();
        Log.i("uuid for task: ", this.taskId);
    }

    public void setTaskCompleted(boolean completed) {
        if (!this.taskCompleted) this.taskCompleted = completed;
    }

    public boolean getTaskCompleted() {
        return this.taskCompleted;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        if (taskTitle.length() <= this.MAX_TITLE_NAME_LENGTH) this.taskTitle = taskTitle;
        else throw new IllegalArgumentException();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if (description.length() <= this.MAX_DESCRIPTION_LENGTH) this.description = description;
        else throw new IllegalArgumentException();
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerDisplayName() { return this.ownerDisplayName; }

    public void setOwnerDisplayName(String ownerDisplayName) { this.ownerDisplayName = ownerDisplayName; }

    public User getProvider() {
        return this.provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public double getMaximumBid() {
        return this.maximumBid;
    }

    public void setMaximumBid(double maximumBid) {
        this.maximumBid = maximumBid;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        if (this.getTaskCompleted()) {
            return "Done";
        }
        if (this.provider != null) {
            return "Assigned";
        }
        if (this.bidList.getSize() == 0) {
            return "Requested";
        }
        if (this.bidList.getSize() > 0) {
            return "Bidded";
        }
        throw new IllegalStateException(); //TODO: make sure this is the right exception
    }

    public BidList getBidList() {
        return this.bidList;
    }

    public void setBidList(BidList bidList) {
        this.bidList = bidList;
    }

    //TODO: this point on, not really sure what is going on withe the below methods, waiting for more clarification

    public UserList getUserBidList() {
        //Needs to seperate out userList
        UserList userList = new UserList();
        return userList;
    }

    public String getTaskId() {
        // Use for elastic search and cache file.
        return taskId;
    }


    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addBidder(Bid bid) {this.bidList.addBid(bid); }

    public void addBidder(User user, double bid) {}

    public void updateBidder(User user, double bid) {}

    public void cancelBidder(User user) {}


    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void updateTask(){
        // TODO: check if status has changed, update bid list if one was accepted, etc

    }
}
