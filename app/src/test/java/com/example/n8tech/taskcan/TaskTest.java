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

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by cbinns on 2/22/2018.
 * Edited by msquigle on 2/23/2018.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {

    public TaskTest(){
        super(SignInActivity.class);
    }

    public testAddTask() {
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        User user2 = new User("Bill", "1111", "bill@n8tech.com", "098-765-4321");
        User user3 = new User("Mary", "1234", "mary@n8tech.com", "312-893-8293");
        Bidder bidder1 = new Bidder(user3, 18.91);
        Task task1 = new Task();

        task1.setName("Walk my dog");
        task1.setDescription("Around the block");
        task1.setOwner(user1);
        task1.setProvider(user2);
        task1.setMaximumBid(20.00);
        task1.setCategory("Pets");
        task1.addBidder(user2, 15.32);
        task1.addBidder(bidder1);
        task1.setLocation("Edmonton");

        assert(task1.getName() == "Walk my dog");
        assert(task1.getDescription() == "Around the block");
        assert(task1.getOwner().equals(user1));
        assert(task1.getProvider().equals(user2));
        assert(task1.getMaximumBid() == 20.00);
        assert(task1.getCategory() == "Pets");
        assert(task1.getBidderList()[1].equals(bidder1));
        assert(task1.getLocation() == "Edmonton");
    }

    public void testAddMinTask() {
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        Task task1 = new Task("Walk my dog", "Around the block", user1);

        assert(task1.getProvider() == null);
        assert(task1.getMaximumBid() == Double.POSITIVE_INFINITY);
        assert(task1.getCategory() == "Other");
        assert(task1.getStatus() == "Requested");
        assert(task1.getBidderList().isEmpty());
        assert(task1.getLocation() == null);
    }

    public void testBidListManipulation() {
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        User user2 = new User("Bill", "1111", "bill@n8tech.com", "098-765-4321");
        User user3 = new User("Mary", "1234", "mary@n8tech.com", "312-893-8293");
        User user4 = new User("Jill", "5678", "jill@n8tech.com", "932-232-6753");
        User user5 = new User("Tom", "9999", "tom@n8tech.com", "723-999-9999");
        User user6 = new User("Pam", "1212", "pam@n8tech.com", "000-111-2222");
        Bidder bidder1 = new Bidder(user1, 23.23);
        Bidder bidder2 = new Bidder(user2, 15.32);
        Bidder bidder3 = new Bidder(user3, 12.89);
        Bidder bidder4 = new Bidder(user4, 67.55);
        Bidder bidder5 = new Bidder(user5, 54.33);
        Bidder bidder6 = new Bidder(user6, 17.84);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);

        ArrayList<Bidder> bidderList = new ArrayList<Bidder>();
        ArrayList<Bidder> bidderList2 = new ArrayList<Bidder>();
        bidderList.add(bidder1);
        bidderList.add(bidder2);
        bidderList.add(bidder3);
        bidderList.add(bidder4);
        bidderList.add(bidder5);
        bidderList.add(bidder6);

        Task task1 = new Task();
        task1.addBidder(user1, 23.23);
        task1.addBidder(user2, 15.32);
        task1.addBidder(user3, 12.89);
        task1.addBidder(user4, 67.55);
        task1.addBidder(user5, 54.33);
        task1.addBidder(user6, 17.84);
        assert(task1.getBidderList().size() == 6);
        assert(task1.getUserBidList().size() == 6);

        task1.cancelBidder(user3);
        userList.remove(userList.indexOf(user3));
        bidderList.remove(bidderList.indexOf(bidder3));
        assert(task1.getBidderList().size() == 5);
        assert(task1.getUserBidList().size() == 5);
        assert(task1.getBidderList().equals(bidderList));
        assert(task1.getUserBidList().equals(userList));

        task1.updateBidder(user5, 11.76);
        bidderList.set(3,new Bidder(user5, 11.76));
        assert(task1.getBidderList().size() == 5);
        assert(task1.getUserBidList().size() == 5);
        assert(task1.getBidderList().equals(bidderList));
        assert(task1.getUserBidList().equals(userList));
    }
}
