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

package com.example.n8tech.taskcan;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Task represents a task from a requester
 * and stores task details
 */

public class Task {
    private String name; // task title
    private String description;
    private User owner;
    private User provider;
    private double maximumBid;
    private double currentBid;
    private String category;
    private String status;
    private ArrayList<Bid> bidderList; // TODO need a class for this
    private String location;            // TODO change to geolocation variable


    public Task(){
        // test task
        bidderList = new ArrayList<>();
    }


    // minimum information needed to create a new task
    public Task(String name, String description, User owner) {
        // TODO: length checking for name & description
        this.name = name;
        this.description = description;
        this.owner = owner;

        // set default values for a new task
        this.provider = null;
        this.maximumBid = Double.POSITIVE_INFINITY;
        // this.currentBid = ?? set here to a default value or leave alone
        this.category = "Other";
        this.status = "Requested";
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

    public ArrayList<Bid> getBidderList() {
        return bidderList;
    }

    public void setBidderList(ArrayList<Bid> bidderList) {
        this.bidderList = bidderList;
    }

    public ArrayList<User> getUserBidList() {
        //Needs to seperate out users
        ArrayList<User> userList = new ArrayList<User>();
        return userList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addBidder(Bid bidder) {this.bidderList.add(bidder); }

    public void addBidder(User user, double bid) {}

    public void updateBidder(User user, double bid) {}

    public void cancelBidder(User user) {}


}
