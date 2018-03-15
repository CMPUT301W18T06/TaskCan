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

/**
 * Task represents a task from a requester
 * and stores task details
 */

public class Task {
    private String name;
    private String description;
    private User owner;
    private String ownerDisplayName;
    private User provider;
    private double maximumBid;
    private double currentBid;
    private String category;
    private String status;
    private Bids bids;
    private String location;            // TODO change to geolocation variable


    public Task(){
        // test task
    }

    // minimum information needed to create a new task
    public Task(String name, String description, User owner, String ownerDisplayName) {
        // TODO: length checking for name & description
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.ownerDisplayName = ownerDisplayName;

        // set default values for a new task
        this.provider = null;
        //this.maximumBid = Double.POSITIVE_INFINITY;
        //Swapping since JSON can't handle infinity may swap back later
        this.maximumBid = -1;
        // this.currentBid = ?? set here to a default value or leave alone
        this.category = "Other";
        this.status = "Requested";
        this.bids = new Bids();
        this.location = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerDisplayName() { return ownerDisplayName; }

    public void setOwnerDisplayName(String ownerDisplayName) { this.ownerDisplayName = ownerDisplayName; }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public double getMaximumBid() {
        return maximumBid;
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
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bids getBids() {
        return bids;
    }

    public void setBids(Bids bids) {
        this.bids = bids;
    }

    public UserList getUserBidList() {
        //Needs to seperate out users
        UserList userList = new UserList();
        return userList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addBidder(Bid bid) {this.bids.addBid(bid); }

    public void addBidder(User user, double bid) {}

    public void updateBidder(User user, double bid) {}

    public void cancelBidder(User user) {}


}
