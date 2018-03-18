package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by m_qui on 3/12/2018.
 */

public class UserList implements Iterable<User> {

    private ArrayList<User> userList;

    public UserList() {
        this.userList = new ArrayList<User>();
    }

    public void addUser(User user) { this.userList.add(user); }

    public User getUser(int i) {
        return this.userList.get(i);
    }

    public int getUserIndex(User user) { return this.userList.indexOf(user); }

    public Boolean delUser(User user) { return this.userList.remove(user); }

    public int getSize() {
        return userList.size();
    }

    public Iterator<User> iterator() {
        return new UsersIterator();
    }

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
