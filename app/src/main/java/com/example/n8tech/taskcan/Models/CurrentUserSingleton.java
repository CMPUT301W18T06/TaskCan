package com.example.n8tech.taskcan.Models;

/**
 * Created by cbinns on 3/16/2018.
 */

public class CurrentUserSingleton {
    private static CurrentUserSingleton instance = new CurrentUserSingleton();
    private static User currentUser = null;


    public static CurrentUserSingleton getInstance() {
        return instance;
    }

    public static User getUser() {
        if (currentUser == null){
            return new User();
        }
        return currentUser;
    }

    public static void setUser(User newUser) {
        currentUser = newUser;
    }

    public static void resetCurrentUser(){
        // call on logout
        currentUser = null;
    }


}
