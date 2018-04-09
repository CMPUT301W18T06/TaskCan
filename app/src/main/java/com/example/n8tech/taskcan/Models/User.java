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
 * A User is a Task Requester and/or Task Provider.
 * This class stores profile information, user's task's information and related methods.
 *
 * @author CMPUT301W18T06
 * @see TaskList
 */

public class User {
    private String profileName;             //persons name, ex "Carolyn Binns", doesnt have to be unique, cant be empty string
    private String username;                //unique username chosen by the user, ex  "cbinns"
    private String password;                //if not set, password is ""
    private String email;                   //not unique. check that "@" and "." exist and in order
    private String phoneNumber;             //7 numbers
    private BiddedTaskList myTaskBids;
    private TaskList myTasks;
    private Integer editCount;

    @JestId
    private String id;

    /** Empty constructor */
    public User(){
        // test user
        this.myTasks = new TaskList();
        this.myTaskBids = new BiddedTaskList();

    }

    /**
     * Creates a User object with contact details as specified by requirements.
     * Two new TaskLists are created representing the tasks requested by the user and
     * the tasks in which the user is a bidder.
     * @param username unique username chosen by the user
     * @param email non-unique email address
     * @param password password set by the user for log-in
     * @param phoneNumber user's phone number -- must be seven numbers
     */
    public User(String profileName, String username, String email, String password, String phoneNumber) {
        this.profileName = profileName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.myTaskBids = new BiddedTaskList();
        this.myTasks = new TaskList();
    }

    /** @return user ID */
    public String getId() {
        return id;
    }

    /** @param id user ID */
    public void setId(String id) {
        this.id = id;
    }

    /** @return nonunique name of the user displayed in their profile */
    public String getProfileName() {
        return profileName;
    }

    /** @param profileName nonunique name of the user displayed in their profile */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /** @return username chosen by the user */
    public String getUsername() {
        return this.username;
    }

    /** @param username unique username chosen by the user */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return password */
    public String getPassword() {
        return this.password;
    }

    /** @param password password set by the user */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @return user email address */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the user's email address.
     * Email Address must contain "@" and "." in that order to be valid.
     * @param email user email address
     * @throws IllegalArgumentException If email address is invalid.
     */
    public void setEmail(String email) {
        this.email = email;
        //if (email.contains("@")) this.email = email;
        //else throw new IllegalArgumentException();
    }

    /** @return user phone number */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Sets the user's phone number.
     * Phone number must be 10 characters in length to be valid.
     * @param phoneNumber user phone number
     * @throws IllegalArgumentException If phone number is invalid.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        //if (phoneNumber.length() == 10) this.phoneNumber = phoneNumber;
        //else throw new IllegalArgumentException();
    }

    public Integer getEditCount() { return this.editCount; }

    public void setEditCount(Integer editCount) { this.editCount = editCount; }

    /** @param biddedTask task the user has made a bid on */
    public void addBidTask(BiddedTask biddedTask) { this.myTaskBids.addBiddedTask(biddedTask); }

    /**
     * Replaces the task at the specified index with a new task.
     * @param index index of task to be replaced
     * @param task new task object to replace current task at the index
     */
    public void replaceTaskAtIndex(int index, Task task){
        this.myTasks.replaceAtIndex(index, task);
    }

    /** @param biddedTask task to be removed from My Task Bids */
    public void removeBidTask(BiddedTask biddedTask) {
        for(BiddedTask myTask : this.myTaskBids) {
            if(biddedTask.getTaskId().equals(myTask.getTaskId())) {
                this.myTaskBids.removeBiddedTask(myTask);
                break;
            }
        }
    }

    public void removeBidTask(String taskId) {
        for(BiddedTask myTask : this.myTaskBids) {
            if(taskId.equals(myTask.getTaskId())) {
                this.myTaskBids.removeBiddedTask(myTask);
            }
        }
    }



    /** @return list of tasks in which the user is a bidder */
    public BiddedTaskList getBidTaskList() { return this.myTaskBids; }

    public void replaceTaskBid(int index, BiddedTask biddedTask){
        this.myTaskBids.replaceAtIndex(index, biddedTask);
    }

    /** @param task task the user has requested */
    public void addTask(Task task) { this.myTasks.addTask(task); }

    /** @param task task to be removed from My Tasks */
    public void removeTask(Task task) { this.myTasks.removeTask(task);}

    /** @return list of tasks requested by the user */
    public TaskList getMyTaskList() { return this.myTasks; }

    /** @return empty string */
    public String toString() {
        return "";
    }

    public void setMyTaskList(TaskList myTaskList) {
        this.myTasks = myTaskList;
    }
}
