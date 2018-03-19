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
 * Task represents a task posted by a requester and stores task details.
 * This class encapsulates fields and methods related to the task object.
 *
 * @author CMPUT301W18T06
 * @see BidList
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


    /** Empty constructor */
    public Task(){
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

    /** Creates a Task object with the minimum details as specified by requirements.
     * A new BidList is created, task status is set to "Requested", task completed is set to false
     * and default values are set.
     * @param name name of the task with a maximum length of 30 characters
     * @param description brief description of the task with a maximum length of 300 characters
     * @param ownerUsername username of the task requester
     * @param ownerId task requester ID
     * @param category category the task belongs to
     */
    public Task(String name, String description, String ownerUsername, String ownerId, String category) {
        // TODO: length checking for name & description
        this.taskTitle = name;
        this.description = description;
        this.ownerUsername = ownerUsername;
        this.ownerId = ownerId;

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

    /** @param completed true if task has been completed */
    public void setTaskCompleted(boolean completed) {
        if (!this.taskCompleted) this.taskCompleted = completed;
    }

    /** @return true if task has been completed, otherwise false */
    public boolean getTaskCompleted() {
        return this.taskCompleted;
    }

    /** @return task name */
    public String getTaskTitle() {
        return this.taskTitle;
    }

    /**
     * Sets the task name and checks for name length.
     * @param taskTitle name of the task
     * @throws IllegalArgumentException If task name exceeds 30 characters.
     */
    public void setTaskTitle(String taskTitle) {
        if (taskTitle.length() <= this.MAX_TITLE_NAME_LENGTH) this.taskTitle = taskTitle;
        else throw new IllegalArgumentException();
    }

    /** @return task description */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the task description and checks for description length.
     * @param description
     * @throws IllegalArgumentException If task description exceeds 300 characters.
     */
    public void setDescription(String description) {
        if (description.length() <= this.MAX_DESCRIPTION_LENGTH) this.description = description;
        else throw new IllegalArgumentException();
    }

    /** @return username of the task requester */
    public String getOwner() {
        return this.ownerUsername;
    }

    /** @param owner username of the task requester */
    public void setOwner(String owner) {
        this.ownerUsername = owner;
    }

    /** @return task requester ID */
    public String getOwnerId() { return this.ownerId; }

    /** @param id task requester ID */
    public void setOwnerId(String id) { this.ownerId = id; }

    /** @return username of the task provider */
    public String getProvider() {return this.providerUsername;}

    /** @param newProvider username of the task provider */
    public void setProvider(String newProvider) {
        this.providerUsername = newProvider;
    }

    /** @return maximum bid placed on the task */
    public double getMaximumBid() {
        return this.maximumBid;
    }

    /** @param maximumBid maximum bid placed on the task */
    public void setMaximumBid(double maximumBid) {
        this.maximumBid = maximumBid;
    }

    /** @return current bid set on the task */
    public double getCurrentBid() {
        return currentBid;
    }

    /** @param currentBid bid set on the task */
    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    /** @return category the task belongs to */
    public String getCategory() {
        return this.category;
    }

    /** @param category the category the task belongs to */
    public void setCategory(String category) {
        this.category = category;
    }

    /** @param status task status of completion */
    public void setStatus(String status) {
        if(this.status == "Completed"){
            this.status = status == "Completed" ? "Completed" : this.status;
        }
        else{
            this.status = status;
        }
    }

    /**
     * Returns the task status, one of:
     * <ul><li>Done - task has been completed.</li>
     * <li>Assigned - a task provider has been chosen.</li>
     * <li>Requested - task bid list has no bids.</li>
     * <li>Bidded - task bid list has at least one bid. </li></ul>
     * @return task status of completion
     * @throws IllegalStateException If none of these statuses are applicable.
     */
    public String getStatus() {
        if (this.getTaskCompleted() || this.status.equals("Completed")) {
            this.status = "Completed";

        }
        else if (this.providerUsername != null || this.status.equals("Assigned")) {
            this.status = "Assigned";

        }
        else if (this.bidList.getSize() == 0 || this.status.equals("Requested")) {
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

    /** @return task bid list */
    public BidList getBidList() {
        return this.bidList;
    }

    /** @param bidList task bid list */
    public void setBidList(BidList bidList) {
        this.bidList = bidList;
    }

    /** @return task ID */
    public String getId() {
        // to be used for ElasticSearch and cache file
        return this.id;
    }

    /** @param id task ID */
    public void setId(String id) {
        this.id = id;
    }

    /** @return location of the task */
    public Place getLocation() {
        return this.location;
    }

    /**  @param location location of the task */
    public void setLocation(Place location) {
        this.location = location;
    }

    /** @return task name and description string */
    public String toString() {

        return this.taskTitle + "\n" + this.description;
    }

    /** Adds a bid to the task bid list only when task status is either "Requested" or "Bidded".
     * @param bid bid to be added to task bid list
     */
    public void addBidder(Bid bid) {
        if(this.getStatus().equals("Assigned") || this.getStatus().equals("Completed")){}
        else{
            this.bidList.addBid(bid);
        }
    }

    /** @param user bidder of bid to be removed from bid list */
    public void cancelBidder(User user) {
        if (this.bidList.bidderExists(user)) {
            int i = this.bidList.indexOfBidContaining(user);
            Bid cancelBid = this.bidList.getBid(i);
            this.bidList.removeBid(cancelBid);
        }
    }
    
    /**
     * Checks if task status has changed and updates the task details.
     */
    public void updateTask(){
        // TODO: check if status has changed, update bid list if one was accepted, etc
    }

    //TODO: this point on, not really sure what is going on with the below methods, waiting for more clarification
    /**
     * @param user task bidder
     * @param bid amount set on a bid
     */
    public void updateBidder(User user, double bid) {}

}
