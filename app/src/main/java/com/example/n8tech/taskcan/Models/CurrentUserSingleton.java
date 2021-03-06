package com.example.n8tech.taskcan.Models;

/**
 * CurrentUserSingleton represents the user of the app currently signed in.
 *
 * @author CMPUT301W18T06
 * @see User
 */

public class CurrentUserSingleton {
    private static CurrentUserSingleton instance = new CurrentUserSingleton();
    private static User currentUser = null;
    private static ImageList imageList = new ImageList();
    private static Boolean forceSync = false;

    /** @return the CurrentUserSingleton associated with the user signed in. */
    public static CurrentUserSingleton getInstance() {
        return instance;
    }

    /**
     * Returns the User object associated with the singleton.
     * If there is no user, a new user is created.
     * @return the current user
     */
    public static User getUser() {
        if (currentUser == null){
            return new User();
        }
        return currentUser;
    }

    /** @param newUser User object that represents the user signed in. */
    public static void setUser(User newUser) {
        currentUser = newUser;
    }

    /**
     * Sets the currentUser to NULL. This is called on logout.
     */
    public static void resetCurrentUser(){
        currentUser = null;
    }

    /** @return ImageList of user's images */
    public static ImageList getImageList() {
        return imageList;
    }

    /** @param imageList ImageList of user's images */
    public static void setImageList(ImageList imageList) {
        CurrentUserSingleton.imageList = imageList;
    }

    public static void setForceSync(Boolean pressed) {
        forceSync = pressed;
    }

    public static Boolean getForcedSync() {
        return forceSync;
    }

}
