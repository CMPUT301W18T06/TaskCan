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
 * on a task. This class stores the bidder's user information
 * and bid amount.
 *
 * @author CMPUT301W18T06
 */

public class Bid {
    private String bidUsername;
    private String bidId;
    private double bidAmount;

    /** Empty constructor used for method signatures */
    public Bid(){
        this.bidUsername = null;
        this.bidId = null;
        this.bidAmount = -1;
    }

    /**
     * Creates an instance of bid that sets the bidder and the bid amount
     * @param bidUsername stores the user information of the task provider that has made the bid
     * @param bidId stores the user id
     * @param bidAmount the bid amount set by the bidder
     * @throws IllegalArgumentException If bidAmount is less than 0.01
     */
    public Bid(String bidUsername, String bidId, double bidAmount) {
        this.bidUsername = bidUsername;
        this.bidId = bidId;

        if (bidAmount < 0.01){
            throw new IllegalArgumentException();
        }
        this.bidAmount = bidAmount;
    }

    /** @return bidUsername username of the bidder */
    public String getBidUsername() {
        return this.bidUsername;
    }

    /**
     * Sets the bidder only when not previously set; bidder is then immutable when set
     * @param bidUsername task provider that has made the bid
     */
    // https://stackoverflow.com/questions/14146182/how-to-create-a-variable-that-can-be-set-only-once-but-isnt-final-in-java
    public void setBidUsername(String bidUsername) {
        if (this.bidUsername == null) {
            this.bidUsername = bidUsername;
        }
    }

    /** @return bid ID */
    public String getBidId() { return this.bidId; }

    /** @param bidId bid ID */
    public void setBidId(String bidId) {
        if (this.bidId == null) {
            this.bidId = bidId;
        }
    }

    /** @return bidAmount */
    public double getBidAmount() {
        return this.bidAmount;
    }

    /**
     * Sets the bidAmount only when not previously set; bidAmount is then immutable when set
     * @param bidAmount the amount to be set to the bid
     * @throws IllegalArgumentException If bid amount is less than 0.01
     */
    public void setBidAmount(double bidAmount) {
        if (bidAmount < 0.01){
            if (bidAmount < 0.01){
                throw new IllegalArgumentException();
            }
        }
        this.bidAmount = this.bidAmount == -1 ? bidAmount : this.bidAmount;
    }
}
