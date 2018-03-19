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

import io.searchbox.annotations.JestId;

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
    private String ownerId;
    private String providerUsername;
    private double maximumBid;
    private double currentBid;
    private String category;
    private BidList bidList;
    private Place location;            // TODO change to geolocation variable
    private boolean taskCompleted;
    private String status;

    @JestId
    private String id;



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

        //this.taskId = UUID.randomUUID().toString();
        //Log.i("uuid for task: ", this.taskId);
    }

    // minimum information needed to create a new task
    public Task(String name, String description, String ownerUsername, String ownerId, String category) {
        // TODO: length checking for name & description
        this.taskTitle = name;
        this.description = description;
        this.ownerUsername = ownerUsername;
        this.ownerId = ownerId;

        // set default values for a new task
        this.providerUsername = null;
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

    public String getOwnerId() { return this.ownerId; }

    public void setOwnerId(String id) { this.ownerId = id; }

    // TODO change this.provider to providers username string
    public String getProvider() {return this.providerUsername;}

    public void setProvider(String newProvider) {
        this.providerUsername = newProvider;
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
        else if (this.providerUsername != null) {
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

    public String getId() {
        // Use for elastic search and cache file.
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Place getLocation() {
        return this.location;
    }

    public void setLocation(Place location) {
        this.location = location;
    }

    public void updateTask(){
        // TODO: check if status has changed, update bid list if one was accepted, etc
    }

    public String toString() {

        return this.taskTitle + "\n" + this.description;
    }

    //TODO: this point on, not really sure what is going on with the below methods, waiting for more clarification

    public void addBidder(Bid bid) {this.bidList.addBid(bid); }

    public void updateBidder(User user, double bid) {}

    public void cancelBidder(User user) {}

}
