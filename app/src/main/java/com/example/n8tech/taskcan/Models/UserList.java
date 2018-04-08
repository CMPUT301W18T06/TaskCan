package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * UserList contains an iterable ArrayList of User objects and related methods.
 *
 * @author CMPUT301W18T06
 * @see User
 */

public class UserList implements Iterable<User> {
    private ArrayList<User> userList;

    /** Creates a new instance of UserList, creating a new ArrayList of Users. */
    public UserList() {
        this.userList = new ArrayList<User>();
    }

    /** @param user the user to be added */
    public void addUser(User user) { this.userList.add(user); }

    /**
     * @param i integer representing list index
     * @return user at the corresponding index
     */
    public User getUser(int i) {
        return this.userList.get(i);
    }

    /**
     * @param user user in the user list
     * @return integer represnting the index of the specified user
     */
    public int getUserIndex(User user) { return this.userList.indexOf(user); }

    /**
     * @param user the user to be removed from the list
     * @return true if user was removed, otherwise false
     */
    public Boolean delUser(User user) {
        for(User myUser : userList) {
            if(user.getId().equals(myUser.getId())) {
                userList.remove(myUser);
                return true;
            }
        }
        return false;
    }

    /** @return integer representing user list size */
    public int getSize() {
        return userList.size();
    }

    /** Creates a UserList iterator. */
    public Iterator<User> iterator() {
        return new UsersIterator();
    }

    public ArrayList<User> getUsers() {
        return userList;
    }

    /**
     * Iterator object over UserList.
     * @throws UnsupportedOperationException If remove() method is called.
     */
    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class UsersIterator implements Iterator<User> {

        private int index = 0;

        public boolean hasNext() {
            return (this.index < userList.size());
        }

        public User next() {
            return userList.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");
        }
    }
}
