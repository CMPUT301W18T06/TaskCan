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

import com.google.android.gms.location.places.Place;

import java.util.UUID;

/**
 * Task represents a task from a requester
 * and stores task details.
 */

public class Task {
    final private int MAX_TITLE_NAME_LENGTH = 30;
    final private int MAX_DESCRIPTION_LENGTH = 300;

    private String taskTitle;
    private String description;
    private String ownerUsername;
    private User provider;
    private double maximumBid;
    private double currentBid;
    private String category;
    private BidList bidList;
    private Place location;            // TODO change to geolocation variable
    private boolean taskCompleted;
    private String taskUUID;                 // TODO need unique task UUID
    private String status;



    public Task(){
        // default constructor
        this.taskTitle="";
        this.description="";
        this.maximumBid = -1;
        this.currentBid = -1;
        this.category = "Other";
        this.bidList = new BidList();
        this.location = null;
        this.taskCompleted = false;
        this.status = "Requested";

        // TODO create a unique task id. check elastic search to ensure its unique?
<<<<<<< HEAD
        //this.taskId = UUID.randomUUID().toString();
        //Log.i("uuid for task: ", this.taskId);
=======
        this.taskUUID = UUID.randomUUID().toString();
        Log.i("uuid for task: ", this.taskUUID);
>>>>>>> d3c35295d39b071b08ad155a14f5c838ae62ada2
    }

    // minimum information needed to create a new task
    public Task(String name, String description, String ownerUsename, String category) {
        // TODO: length checking for name & description
        this.taskTitle = name;
        this.description = description;
        this.ownerUsername = ownerUsername;


        // set default values for a new task
        this.provider = null;
        this.maximumBid = -1;
        this.currentBid = -1;
        this.category = category;
        this.bidList = new BidList();
        this.location = null;
        this.taskCompleted = false;     // ie requested
        this.status = "Requested";

        // TODO create a unique task id. check elastic search to ensure its unique?
        //this.taskUUID = UUID.randomUUID().toString();
        //Log.i("uuid for task: ", this.taskUUID);
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

    public String getOwner() {
        return this.ownerUsername;
    }

    public void setOwner(String owner) {
        this.ownerUsername = owner;
    }

    // TODO change this.provider to providers username string
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

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        if (this.getTaskCompleted()) {
            this.status = "Done";

        }
        else if (this.provider != null) {
            this.status = "Assigned";

        }
        else if (this.bidList.getSize() == 0) {
            this.status = "Requested";
        }
        else if (this.bidList.getSize() > 0) {
            this.status = "Bidded";
        }
        else {
            throw new IllegalStateException(); //TODO: make sure this is the right exception
        }
        return this.status;
    }

    public BidList getBidList() {
        return this.bidList;
    }

    public void setBidList(BidList bidList) {
        this.bidList = bidList;
    }

    //TODO: this point on, not really sure what is going on withe the below methods, waiting for more clarification


    public String getTaskUUID() {
        // Use for elastic search and cache file.
        return taskUUID;
    }


    public Place getLocation() {
        return this.location;
    }

    public void setLocation(Place location) {
        this.location = location;
    }

    public void addBidder(Bid bid) {this.bidList.addBid(bid); }

    public void updateBidder(User user, double bid) {}

    public void cancelBidder(User user) {}


    public void updateTask(){
        // TODO: check if status has changed, update bid list if one was accepted, etc

    }

}
