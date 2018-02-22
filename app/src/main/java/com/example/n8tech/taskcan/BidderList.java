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

import java.util.ArrayList;

/**
 * BidderList contains all bidders on a task
 */

public class BidderList {
    private ArrayList<Bidder> bidders;


    public BidderList() {
        this.bidders = new ArrayList<Bidder>();
    }

    public ArrayList<Bidder> getBidders() {
        return bidders;
    }

    public void setBidders(ArrayList<Bidder> bidders) {
        this.bidders = bidders;
    }

    public void addBidder(Bidder newBidder){
        // TODO: add a bidder to the list
    }

    public void removeBidder(Bidder bidderToRemove){
        // TODO: remove a bidder from the list
    }

    public Bidder getBidder(int bidderIndex){
        // TODO: return bidder at index if exists

        return new Bidder();
    }



}
