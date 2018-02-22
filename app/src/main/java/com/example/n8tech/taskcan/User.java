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

package com.example.n8tech.taskcan;


/**
 * A User is a Task Requester and or Task provider.
 * This class stores profile information and
 * the user's task's information.
 */

public class User {
    private String username;
    private String password;
    private String email;
    private String contactInformation;
    private MyTaskBidList myTaskBids;
    private MyTaskList myTasks;

    public User(){
        // test user
    }

    public User(String username, String password, String email, String contactInformation) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.contactInformation = contactInformation;
        this.myTaskBids = new MyTaskBidList();
        this.myTasks = new MyTaskList();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}