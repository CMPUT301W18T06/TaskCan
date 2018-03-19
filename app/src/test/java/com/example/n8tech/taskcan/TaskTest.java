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

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Views.SignInActivity;
import com.google.android.gms.location.places.Place;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Unit testing for Task class.
 *
 * @see Task
 * @author CMPUT301W18T06
 */

public class TaskTest{

    public TaskTest(){
    }
    // TODO Tests to make sure certain things cannot be overwritten some things can be

    @Test
    public void testAddTask() {
        /*
         * Test basic functionality, being able to set each field and guaranteeing that it is written correctly.
         */
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        User user2 = new User("Bill", "1111", "bill@n8tech.com", "098-765-4321");
        User user3 = new User("Mary", "1234", "mary@n8tech.com", "312-893-8293");
        Bid bid1 = new Bid(user3, 18.91);
        Task task1 = new Task();

        task1.setTaskTitle("Walk my cat");
        task1.setDescription("Around the block");
        task1.setOwner(user1.getUsername());
        task1.setMaximumBid(20.00);
        task1.setCategory("Pets");
        task1.addBidder(bid1);
        task1.setProvider(user2.getUsername());
        //task1.setLocation("Edmonton");

        assertEquals(task1.getTaskTitle(), "Walk my cat");
        assertEquals(task1.getDescription(), "Around the block");
        assertEquals(task1.getOwner(), user1.getUsername());
        assertEquals(task1.getProvider(), user2.getUsername());
        assertEquals(task1.getMaximumBid(), 20.00, 0.00);
        assertEquals(task1.getCategory(), "Pets");
        assertEquals(task1.getBidList().getBid(0), bid1);
       // assert(task1.getLocation() == "Edmonton");
    }

    @Test
    public void testAddMinTask() {
        /*
         * Ensures that when a task with minimal information is added that all other fields are assigned correctly.
         */
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        Task task1 = new Task("Walk my dog", "Walk dog around the block", user1.getUsername(), "1345679", "Category1");

        assertEquals(task1.getProvider(), null);
        assertEquals(task1.getMaximumBid(),-1, 0.00);
        assertEquals(task1.getCategory(), "Category1");
        assertEquals(task1.getStatus(), "Requested");
        assertEquals(task1.getBidList().getSize(), 0);
        assertEquals(task1.getLocation(), null);
    }

    @Test
    public void testBidListManipulation() {
        /*
         * Tests how a bidList will change throughout it's lifetime
         */

        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        User user2 = new User("Bill", "1111", "bill@n8tech.com", "098-765-4321");
        User user3 = new User("Mary", "1234", "mary@n8tech.com", "312-893-8293");
        User user4 = new User("Jill", "5678", "jill@n8tech.com", "932-232-6753");
        User user5 = new User("Tom", "9999", "tom@n8tech.com", "723-999-9999");
        User user6 = new User("Pam", "1212", "pam@n8tech.com", "000-111-2222");
        Bid bid1 = new Bid(user1, 23.23);
        Bid bid2 = new Bid(user2, 15.32);
        Bid bid3 = new Bid(user3, 12.89);
        Bid bid4 = new Bid(user4, 67.55);
        Bid bid5 = new Bid(user5, 54.33);
        Bid bid6 = new Bid(user6, 17.84);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);

        ArrayList<Bid> bidList = new ArrayList<Bid>();
        bidList.add(bid1);
        bidList.add(bid2);
        bidList.add(bid3);
        bidList.add(bid4);
        bidList.add(bid5);
        bidList.add(bid6);

        Task task1 = new Task();
        task1.addBidder(bid1);
        task1.addBidder(bid2);
        task1.addBidder(bid3);
        task1.addBidder(bid4);
        task1.addBidder(bid5);
        task1.addBidder(bid6);

        //Check if lists after adding are correct.
        assertEquals(task1.getBidList().getSize(), 6);
        //assertEquals(task1.getUserBidList().getSize(), 6);
        for(int i = 0; i < bidList.size(); i++) {
            assertEquals(task1.getBidList().getBid(i), bidList.get(i));
        }

        task1.cancelBidder(user3);
        userList.remove(userList.indexOf(user3));
        bidList.remove(bidList.indexOf(bid3));

        //Test if cancelling a bidder is done correctly
        assertEquals(task1.getBidList().getSize(), 5);
        for(int i = 0; i < bidList.size(); i++) {
            assertEquals(task1.getBidList().getBid(i), bidList.get(i));
        }

        //TODO: updateBidder() has not been implemented yet
//        task1.updateBidder(user5, 11.76);
//        bidList.set(3,new Bid(user5, 11.76));
//
//        //Test if updating a bidder is done correctly.
//        assertEquals(task1.getBidList().getSize(), 5);
//        for(int i = 0; i < bidList.size(); i++) {
//            assertEquals(task1.getBidList().getBid(i), bidList.get(i));
//        }
    }

    @Test
    public void testTaskStatuses() {
        /*
         * Test restrictions on adding bids depending on status
         */
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        User user2 = new User("Bill", "1111", "bill@n8tech.com", "098-765-4321");
        User user3 = new User("Mary", "1234", "mary@n8tech.com", "312-893-8293");
        User user4 = new User("Jill", "5678", "jill@n8tech.com", "932-232-6753");
        User user5 = new User("Tom", "9999", "tom@n8tech.com", "723-999-9999");
        User user6 = new User("Pam", "1212", "pam@n8tech.com", "000-111-2222");
        Bid bid1 = new Bid(user1, 23.23);
        Bid bid2 = new Bid(user2, 15.32);
        Bid bid3 = new Bid(user3, 12.89);
        Bid bid4 = new Bid(user4, 67.55);
        Bid bid5 = new Bid(user5, 54.33);
        Bid bid6 = new Bid(user6, 17.84);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        ArrayList<Bid> bidList = new ArrayList<Bid>();
        bidList.add(bid1);
        bidList.add(bid2);
        bidList.add(bid3);

        Task task1 = new Task();
        task1.addBidder(bid1);
        task1.addBidder(bid2);
        task1.addBidder(bid3);
        //Ensure added correctly.
        assertEquals(task1.getBidList().getSize(), 3);
        //assertEquals(task1.getUserBidList().getSize(), 3);  //not yet implemented
        for(int i = 0; i < bidList.size(); i++){
            assertEquals(task1.getBidList().getBid(i), bidList.get(i));
        }
        //assertEquals(task1.getUserBidList(), userList);     //not yet implemented

        task1.setStatus("Assigned");
        task1.addBidder(bid4);
        //Test that bids are not added when assigned.
        assertEquals(task1.getBidList().getSize(), 3);
        for(int i = 0; i < bidList.size(); i++) {
            assertEquals(task1.getBidList().getBid(i), bidList.get(i));
        }


        task1.setStatus("Requested");
        task1.addBidder(bid4);
        userList.add(user4);
        bidList.add(bid4);
        //Test status changed back to requested and it adds correctly.
        assertEquals(task1.getBidList().getSize(), 4);
        for(int i = 0; i < bidList.size(); i++) {
            assertEquals(task1.getBidList().getBid(i), bidList.get(i));
        }

        task1.setStatus("Completed");
        task1.addBidder(bid5);
        //Test status being completed and that bids are not added.
        assertEquals(task1.getBidList().getSize(), 4);
        for(int i = 0; i < bidList.size(); i++) {
            assertEquals(task1.getBidList().getBid(i), bidList.get(i));
        }

        //Test to make sure once a job is completed it's completed.
        task1.setStatus("Requested");
        assertEquals(task1.getStatus(), "Completed");
    }

    @Test
    public void testOverwrites() {
        /*
         * Tests how different members of a task can be overwritten.
         */
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        User user2 = new User("Bill", "1111", "bill@n8tech.com", "098-765-4321");
        User user3 = new User("Mary", "1234", "mary@n8tech.com", "312-893-8293");
        Bid bid1 = new Bid(user3, 18.91);
        Task task1 = new Task();

        task1.setTaskTitle("Walk my dog");
        task1.setDescription("Around the block");
        task1.setOwner(user1.getUsername());
        task1.setProvider(user2.getUsername());
        task1.setMaximumBid(20.00);
        task1.setCategory("Pets");
        task1.addBidder(bid1);
        //task1.setLocation("Edmonton");

        //Test that the name can be changed
        task1.setTaskTitle("Brush my teeth");
        assertEquals(task1.getTaskTitle(), "Brush my teeth");

        //Test that name cannot be set to null.
        task1.setTaskTitle("");
        assertEquals(task1.getTaskTitle(), "Brush my teeth");

        //Test that the description can be changed.
        task1.setDescription("Extremely Lazy");
        assertEquals(task1.getDescription(), "Extremely Lazy");

        //Test that the description can be set to null.
        task1.setDescription("");
        assertEquals(task1.getDescription(), "");

        //Test that the owner of a task cannot be changed.
        task1.setOwner(user3.getUsername());
        assertEquals(task1.getOwner(), user1.getUsername());

        //Test that the owner cannot be null.
        task1.setOwner(null);
        assertEquals(task1.getOwner(), user1.getUsername());

        //Test that the provider can be changed.
        task1.setProvider(user3.getUsername());
        assertEquals(task1.getProvider(), user3);

        //Test that the provider can be null.
        task1.setProvider(null);
        assertEquals(task1.getProvider(), null);

        //Test that the max bid can be increased.
        task1.setMaximumBid(25.00);
        assertEquals(task1.getMaximumBid(), 25.00, 0.00);

        //Test that the max bid can be decreased.
        task1.setMaximumBid(10.00);
        assertEquals(task1.getMaximumBid(), 10.00, 0.00);

        //Test that the category can be changed to an allowable option.
        task1.setCategory("Personal");
        assertEquals(task1.getCategory(), "Personal");

        //Test that the category cannot be set to a non-set category.
        task1.setCategory("asdf");
        assertEquals(task1.getCategory(), "Personal");

        //Test that the location can be changed.
        //task1.setLocation("Calgary");
        //assert(task1.getLocation() == "Calgary");

        //Test that geographical coordinates will be converted.
        //task1.setLocation("53.5444, 113.4909");
        //assert(task1.getLocation() == "Edmonton");

        //Test that geographical location can be set to null.
        task1.setLocation(null);
        assertEquals(task1.getLocation(), null);
    }
}
