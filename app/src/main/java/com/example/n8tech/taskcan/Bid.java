/*
 *  Copyright (c) 2018 Alexander Filbert, Carolyn Binns, Jeanna Somoza, 	JingMing Huang, Matthew Quigley, Nathanael Belayneh
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

import java.util.Calendar;
import java.util.Date;

/**
 * Bidder represents a task provider that has made a bid
 * on a task. Stores the bidder's user information
 * and bid amount.
 */

public class Bid {
    private User bidder;
    private double bidAmount;
    private Calendar datetimeofBid;
    private Task task;

    /**
     * datetimeofBid is automatically initialized to current user device's date and time
     * bidder and task is initialized here
     */
    public Bid(){
        // empty constructor for method signatures
        this.datetimeofBid = Calendar.getInstance();
        this.bidder = new User();
        this.task = new Task();
    }

    public Bid(User bidder, double bidAmount, Task task) {
        this.bidder = bidder;
        this.bidAmount = bidAmount;
        this.datetimeofBid = Calendar.getInstance();
        this.task = task;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Calendar getDatetimeofBid() { return this.datetimeofBid; }

    public Task getTask() { return this.task; }

    public void setTask(Task t) { this.task = t; }
}
