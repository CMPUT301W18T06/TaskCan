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

package com.example.n8tech.taskcan.Models;


/**
 * Bid represents a task provider that has made a bid
 * on a task. Stores the bidder's user information
 * and bid amount.
 */

public class Bid {
    private User bidder;
    private double bidAmount;

    public Bid(){
        // empty constructor for method signatures
        this.bidder = new User();
        this.bidAmount = 0;
    }

    public Bid(User bidder, double bidAmount) {
        this.bidder = bidder;
        if (bidAmount < 0.01){
            throw new IllegalArgumentException();
        }
        this.bidAmount = bidAmount;
    }

    public User getBidder() {
        return this.bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = this.bidder.getUsername() == null ? bidder : this.bidder;
    }

    public double getBidAmount() {
        return this.bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        if (bidAmount < 0.01){
            if (bidAmount < 0.01){
                throw new IllegalArgumentException();
            }
        }
        this.bidAmount = this.bidAmount == 0 ? bidAmount : this.bidAmount;
    }
}
