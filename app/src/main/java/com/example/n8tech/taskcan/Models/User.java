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
    private String profileName;             //persons name, ex "Carolyn Binns", doesnt have to be unique, cant be empty string
    private String username;                //unique username chosen by the user, ex  "cbinns"
    private String password;                //if not set, password is ""
    private String email;                   //not unique. check that "@" and "." exist and in order
    private String phoneNumber;             //7 numbers
    private TaskList myTaskBids;
    private TaskList myTasks;

    @JestId
    private String id;

    public User(){
        // test user
        this.myTasks = new TaskList();
        this.myTaskBids = new TaskList();

    }

    public User(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.myTaskBids = new TaskList();
        this.myTasks = new TaskList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
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
        this.email = email;
        //if (email.contains("@")) this.email = email;
        //else throw new IllegalArgumentException();
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        //if (phoneNumber.length() == 10) this.phoneNumber = phoneNumber;
        //else throw new IllegalArgumentException();
    }


    public void addBidTask(Task task) { this.myTaskBids.addTask(task); }

    public void replaceTaskAtIndex(int index, Task task){
        this.myTasks.replaceAtIndex(index, task);
    }

    public void removeBidTask(Task task) {
        this.myTasks.removeTask(task);
    }

    public TaskList getBidTaskList() { return this.myTaskBids; }

    public void addTask(Task task) { this.myTasks.addTask(task); }

    public void removeTask(Task task) { this.myTasks.removeTask(task);}

    public TaskList getMyTaskList() { return this.myTasks; }

    public String toString() {
        return "";
    }

}
