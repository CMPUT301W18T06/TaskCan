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

import com.example.n8tech.taskcan.Models.BiddedTask;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Views.SignInActivity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Unit testing for User class.
 *
 * @see User
 * @author CMPUT301W18T06
 */

public class UserTest {

    public UserTest(){
    }

    @Test
    public void testAddUser() {
        //Tests that a user retains set information.
        User user1 = new User();
        user1.setProfileName("Joe");
        user1.setUsername("joe12345");
        user1.setPassword("7355608");
        user1.setEmail("joe@n8tech.com");
        user1.setPhoneNumber("123-456-7890");

        assertEquals(user1.getProfileName(), "Joe");
        assertEquals(user1.getUsername(), "joe12345");
        assertEquals(user1.getPassword(), "7355608");
        assertEquals(user1.getEmail(), "joe@n8tech.com");
        assertEquals(user1.getPhoneNumber(), "123-456-7890");
    }

    @Test
    public void testUserAddBidTask() {
        //Tests that it correctly updates its bid tasks
        User user1 = new User("Joe", "joe12345", "7355608", "joe@n8tech.com", "123-456-7890");
        BiddedTask biddedTask1 = new BiddedTask();
        BiddedTask biddedTask2 = new BiddedTask();
        biddedTask1.setTaskTitle("Task 1");
        biddedTask2.setTaskTitle("Task 2");
        biddedTask1.setTaskId("1");
        biddedTask2.setTaskId("2");

        user1.addBidTask(biddedTask1);
        user1.addBidTask(biddedTask2);
        ArrayList<BiddedTask> bidTaskList = new ArrayList<>();
        bidTaskList.add(biddedTask1);
        bidTaskList.add(biddedTask2);
        for (int i = 0; i < bidTaskList.size(); i++){
            assertEquals(user1.getBidTaskList().getBiddedTaskAtIndex(i),  bidTaskList.get(i));
        }

        user1.removeBidTask(biddedTask2);
        bidTaskList.remove(1);
        for (int i = 0; i < bidTaskList.size(); i++){
            assertEquals(user1.getBidTaskList().getSize(), bidTaskList.size());
            assertEquals(user1.getBidTaskList().getBiddedTaskAtIndex(i), bidTaskList.get(i));
        }
    }

    @Test
    public void testUserAddTask() {
        //Tests that it correctly updates its tasks
        User user1 = new User("Joe", "joe12345", "7355608", "joe@n8tech.com", "123-456-7890");
        Task task1 = new Task("Walk my Dog", "Walk Fluffy", user1.getUsername(), "9876541", "Category1");
        Task task2 = new Task("Walk my Cat", "Walk Furry", user1.getUsername(), "134658", "Category2");

        user1.addTask(task1);
        user1.addTask(task2);
        task1.setId("1");
        task2.setId("2");

        ArrayList<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);
        taskList.add(task2);

        for (int i = 0; i < taskList.size(); i++) {
            assertEquals(user1.getMyTaskList().getTaskAtIndex(i), taskList.get(i));
        }
        taskList.remove(1);
        user1.removeTask(task2);

        for (int i = 0; i < taskList.size(); i++) {
            assertEquals(user1.getMyTaskList().getTaskAtIndex(i), taskList.get(i));
        }
    }

}
