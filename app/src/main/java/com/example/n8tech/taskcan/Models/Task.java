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

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.Arrays;

import io.searchbox.annotations.JestId;

/**
 * Task represents a task posted by a requester and stores task details.
 * This class encapsulates fields and methods related to the task object.
 *
 * @author CMPUT301W18T06
 * @see BidList
 */

public class Task {

    private String taskTitle;
    private String description;
    private String ownerUsername;
    private String ownerId;
    private String providerUsername;
    private String providerId;
    private double maximumBid;
    private double currentBid;
    private String category;
    private BidList bidList;
    private BidList acceptedBidList;
    private Bid acceptedBid = null;
    private LatLng location;            // TODO change to geolocation variable
    private ArrayList<String> imageListId;
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
        this.imageListId = new ArrayList<>();
        this.taskCompleted = false;
        this.status = "Requested";
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

        this.taskTitle = name;
        this.description = description;
        this.ownerUsername = ownerUsername;
        this.ownerId = ownerId;
        this.providerUsername = null;
        this.providerId = null;
        this.maximumBid = -1;
        this.currentBid = -1;
        this.category = category;
        this.bidList = new BidList();
        this.location = null;
        this.imageListId = new ArrayList<>();
        this.taskCompleted = false;     // ie requested
        this.status = "Requested";

    }
    /** @return true if task has been completed, otherwise false */
    public boolean getTaskCompleted() {
        return this.taskCompleted;
    }

    /** @param completed true if task has been completed */
    public void setTaskCompleted(boolean completed) {
        if (!this.taskCompleted) this.taskCompleted = completed;
    }

    /** @return task name */
    public String getTaskTitle() {
        return this.taskTitle;
    }

    /**
     * Sets the task name and checks for name length.
     * @param taskTitle name of the task
     */
    public void setTaskTitle(String taskTitle) {
        if (taskTitle != "") {
            this.taskTitle = taskTitle;
        }
    }

    /** @return task description */
    public String getDescription() { return this.description; }

    /**
     * Sets the task description and checks for description length.
     * @param description
     */
    public void setDescription(String description) { this.description = description; }

    /** @return username of the task requester */
    public String getOwnerUsername() {
        return this.ownerUsername;
    }

    /** @param owner username of the task requester */
    public void setOwnerUsername(String owner) {
        if (this.ownerUsername == null) {
            this.ownerUsername = owner;
        }
    }

    /** @return task requester ID */
    public String getOwnerId() { return this.ownerId; }

    /** @param id task requester ID */
    public void setOwnerId(String id) { this.ownerId = id; }

    /** @return username of the task provider */
    public String getProviderUsername() {return this.providerUsername;}

    /** @param newProvider username of the task provider */
    public void setProviderUsername(String newProvider) {
        this.providerUsername = newProvider;
        this.status = "Assigned";
    }

    /** @return task provider ID */
    public String getProviderId() { return this.providerId; }

    /** @param providerId user ID of the task provider */
    public void setProviderId(String providerId) { this.providerId = providerId; }

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
        if(this.status.intern() == "Done"){
            this.status = status.intern() == "Done" ? "Done" : this.status;
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
    public String getStatus() { return this.status; }

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
    public LatLng getLocation() {
        return this.location;
    }

    /**  @param location location of the task */
    public void setLocation(LatLng location) {
        this.location = location;
    }

    public ArrayList<String> getImageListId() { return this.imageListId; }

    public ImageList getImageList() throws ExecutionException, InterruptedException {
        ImageList il = new ImageList();
        ElasticsearchController.GetImage ec = new ElasticsearchController.GetImage();
        for (String id : this.imageListId) {
            ec.execute(id);
            Image i = ec.get();
            i.recreateRecycledBitmap();
            il.addImage(i);
        }
        return il;
    }

    public void setImageListId(ArrayList<String> imageListId) { this.imageListId = imageListId; }

    public void setImageListId(ImageList imageList) throws ExecutionException, InterruptedException {
        String id;
        for (Image i : imageList.getImages()) {
            ElasticsearchController.AddImage ec = new ElasticsearchController.AddImage();
            if (i.getId() == null) {
                ec.execute(i);
                if ((id = ec.get()) != "NetworkError") {
                    i.setId(id);
                }
            }
            this.imageListId.add(i.getId());
        }
    }

    public void addImage(Image image) {
        this.imageListId.add(image.getId());
    }

    /** @return task name and description string */
    public String toString() {

        return this.taskTitle + "\n" + this.description;
    }

    /** Adds a bid to the task bid list only when task status is either "Requested" or "Bidded".
     * @param bid bid to be added to task bid list
     */
    public void addBidder(Bid bid) {
        if(this.getStatus().intern() == "Assigned" || this.getStatus().intern() == "Completed"){}
        else{
            this.bidList.addBid(bid);
        }
    }

    /** @param userId bidder of bid to be removed from bid list */
    public void cancelBidder(String userId) {
        if (this.bidList.getBidUserIndex(userId) != -1) {
            int i = this.bidList.getBidUserIndex(userId);
            Bid cancelBid = this.bidList.getBid(i);
            this.bidList.removeBid(cancelBid);
        }
    }

    public void replaceBidAtIndex(int index, Bid bid){
        this.bidList.replaceAtIndex(index, bid);
    }

    /**
     * Checks if task status has changed and updates the task details.
     */
    public void updateTask(){

        // TODO: check if status has changed, update bid list if one was accepted, etc
    }

    /**
     * Updates the bid amount on a previous bid made by the same user.
     * @param user task bidder
     * @param bidAmount amount set on a bid
     */
    public void updateBidder(User user, double bidAmount) {
        if (this.bidList.getBidUserIndex(user.getId()) != -1) {
            int i = this.bidList.getBidUserIndex(user.getId());
            Bid bid = this.bidList.getBid(i);
            bid.setBidAmount(bidAmount);
            this.bidList.updateBid(bid, i);
        }
    }

    public BidList getAcceptedBidList() {
        return acceptedBidList;
    }

    public void setAcceptedBidList(BidList acceptedBidList) {
        this.acceptedBidList = acceptedBidList;
    }

    public Bid getAcceptedBid() {
        return acceptedBid;
    }

    public void setAcceptedBid(Bid acceptedBid) {
        this.acceptedBid = acceptedBid;
    }
}
