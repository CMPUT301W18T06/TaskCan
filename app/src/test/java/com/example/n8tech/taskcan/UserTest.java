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

import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Views.SignInActivity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Created by cbinns on 2/22/2018.
 * Edited by msquigle on 2/23/2018.
 */

public class UserTest {

    public UserTest(){
    }

    @Test
    public void testAddUser() {
        //Tests that a user retains set information.
        User user1 = new User();
        user1.setUsername("Joe");
        user1.setPassword("7355608");
        user1.setEmail("joe@n8tech.com");
        user1.setPhoneNumber("123-456-7890");

        assertEquals(user1.getUsername(), "Joe");
        assertEquals(user1.getPassword(), "7355608");
        assertEquals(user1.getEmail(), "joe@n8tech.com");
        assertEquals(user1.getPhoneNumber(), "123-456-7890");
    }

    @Test
    public void testUserAddBidTask() {
        //Tests that it correctly updates its bid tasks
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        Task task1 = new Task();
        Task task2 = new Task();

        user1.addBidTask(task1);
        user1.addBidTask(task2);
        ArrayList<Task> bidTaskList = new ArrayList<>();
        bidTaskList.add(task1);
        bidTaskList.add(task2);
        for (int i = 0; i < bidTaskList.size(); i++){
            assertEquals(user1.getBidTaskList().getTaskAtIndex(0), bidTaskList.get(0));
        }

        user1.removeBidTask(task2);
        bidTaskList.remove(1);
        for (int i = 0; i < bidTaskList.size(); i++){
            assertEquals(user1.getBidTaskList().getTaskAtIndex(i), bidTaskList.get(i));
        }
    }

    @Test
    public void testUserAddTask() {
        //Tests that it correctly updates its tasks
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        Task task1 = new Task("Walk my Dog", "Walk Fluffy", user1, "DisplayName1", "Category1");
        Task task2 = new Task("Walk my Cat", "Walk Furry", user1, "DisplayName2", "Category2");;

        user1.addTask(task1);
        user1.addTask(task2);
        ArrayList<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);
        taskList.add(task2);
        for (int i = 0; i < taskList.size(); i++) {
            assertEquals(user1.getMyTaskList().getTaskAtIndex(i), taskList.get(i));
        }
        user1.removeBidTask(task2);
        taskList.remove(1);
        for (int i = 0; i < taskList.size(); i++) {
            assertEquals(user1.getMyTaskList().getTaskAtIndex(i), taskList.get(i));
        }
    }

}
