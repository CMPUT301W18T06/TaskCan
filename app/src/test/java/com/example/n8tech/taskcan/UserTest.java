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

public class UserTest extends ActivityInstrumentationTestCase2 {

    public UserTest(){
        super(SignInActivity.class);
    }

    public void testAddUser() {
        //Tests that a user retains set information.
        User user1 = new User();
        user1.setUsername("Joe");
        user1.setPassword("7355608");
        user1.setEmail("joe@n8tech.com");
        user1.setContactInformation("123-456-7890");

        assert(user1.getUsername() == "Joe");
        assert(user1.getPassword() == "7355608");
        assert(user1.getEmail() == "joe@n8tech.com");
        assert(user1.getContactInformation() == "123-456-7890");
    }

    public void testUserAddBidTask() {
        //Tests that it correctly updates its bid tasks
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        Task task1 = new Task();
        Task task2 = new Task();

        user1.addBidTask(task1);
        user1.addBidTask(task2);
        ArrayList<Task> bidTaskList = new ArrayList<Task>();
        bidTaskList.add(task1);
        bidTaskList.add(task2);
        assert(user1.getBidTaskList().equals(bidTaskList));

        user1.removeBidTask(task2);
        bidTaskList.remove(1);
        assert(user1.getBidTaskList().equals(bidTaskList));
    }

    public void testUserAddTask() {
        //Tests that it correctly updates its tasks
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        Task task1 = new Task();
        Task task2 = new Task();

        user1.addTask(task1);
        user1.addTask(task2);
        ArrayList<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);
        taskList.add(task2);
        assert(user1.getTaskList() == taskList);

        user1.removeBidTask(task2);
        taskList.remove(1);
        assert(user1.getBidTaskList().equals(taskList));
    }

}
