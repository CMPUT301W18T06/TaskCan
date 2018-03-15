/*
 *  Copyright (c) 2018 Alexander Filbert, Carolyn Binns, Jeanna Somoza, JingMing Huang, Matthew Quigley, Nathanael Belayneh
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

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

import io.searchbox.annotations.JestId;

/**
 * A User is a Task Requester and or Task provider.
 * This class stores profile information and
 * the user's task's information.
 */

public class User {
    private String username;
    private String password;
    private String email;
    private int phoneNumber;
    private ArrayList<Task> myTaskBids;
    private ArrayList<Task> myTasks;

    @JestId
    private String id;

    public User(){
        // test user
    }

    public User(String username, String email, String password, int phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.myTaskBids = new ArrayList<Task>();
        this.myTasks = new ArrayList<Task>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (email.contains("@")) this.email = email;
        else throw new IllegalArgumentException();
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        if (String.valueOf(phoneNumber).length() == 10) this.phoneNumber = phoneNumber;
        else throw new IllegalArgumentException();
    }

    public String getId() {return this.id; }

    public void setId(String id) {
        this.id = id;
    }

    public void addBidTask(Task task) { this.myTaskBids.add(task); }

    public void removeBidTask(Task task) {}

    public ArrayList<Task> getBidTaskList() { return this.myTaskBids; }

    public void addTask(Task task) { this.myTasks.add(task); }

    public void removeTask(Task task) {}

    public ArrayList<Task> getTaskList() { return this.myTasks; }
}
