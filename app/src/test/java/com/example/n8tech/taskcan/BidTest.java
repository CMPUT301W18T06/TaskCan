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

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.User;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing for Bid class.
 *
 * @see Bid
 * @author CMPUT301W18T06
 */
public class BidTest {

    public BidTest() {

    }

    @Test
    //ensure that you can add a bidder and bid amount with both constructors
    public void testAddBidder() {
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        Bid bid1 = new Bid();
        bid1.setBidUsername(user1.getUsername());
        bid1.setBidAmount(12.21);
        assertEquals(bid1.getBidUsername(), user1);
        assertEquals(bid1.getBidAmount(), 12.21, 0.00);
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        Bid bid2 = new Bid(user2.getUsername(), "2", 150.00);
        assertEquals(bid2.getBidUsername(), user2);
        assertEquals(bid2.getBidAmount(), 150.00, 0.00);
    }

    @Test
    //ensure that you can only set the bidder once
    public void testSwitchingBidUser(){
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        Bid bid1 = new Bid(user1.getUsername(), "1", 12.21);
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        Bid bid2 = new Bid(user2.getUsername(), "2", 150.00);
        bid1.setBidUsername(user2.getUsername());
        assertEquals(bid1.getBidUsername(), user1);
        bid2.setBidUsername(user1.getUsername());
        assertEquals(bid2.getBidUsername(), user2);
    }

    @Test
    //ensure that you can only set the bid amount once
    public void testSwitchingBidAmount(){
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        Bid bid1 = new Bid(user1.getUsername(), "1", 12.21);
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        Bid bid2 = new Bid(user2.getUsername(), "2", 150.00);
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        Bid bid3 = new Bid(user3.getUsername(), "3", 0.01);
        bid1.setBidAmount(200.00);
        assertEquals(bid1.getBidAmount(), 12.21, 0.00);
        bid2.setBidAmount(300.00);
        assertEquals(bid2.getBidAmount(), 150.00, 0.00);
        bid1.setBidAmount(0.01);
        assertEquals(bid1.getBidAmount(), 12.21, 0.00);
        bid3.setBidAmount(2.00);
        assertEquals(bid3.getBidAmount(), 0.01, 0.00);
    }

    @Test
    //ensure that you can't set a bid to less than 1 cent
    public void testMinimumBidAmount(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        Bid bid1 = new Bid();
        bid1.setBidUsername(user1.getUsername());
        try{
            bid1.setBidAmount(0.00);
        }
        catch(IllegalArgumentException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        try {
            Bid bid2 = new Bid(user2.getUsername(), "2", -10.00);
        }
        catch(IllegalArgumentException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        if(exceptionCatcher < 2){
            assertEquals(true, false);
        }
    }
}
