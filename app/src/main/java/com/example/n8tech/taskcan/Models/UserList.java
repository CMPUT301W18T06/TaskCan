package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by m_qui on 3/12/2018.
 */

public class UserList implements Iterable<User> {

    private ArrayList<User> userList;

    public UserList() {
        userList = new ArrayList<User>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User getUser(int i) {
        return userList.get(i);
    }

    public Iterator<User> iterator() {
        return new UserListIterator();
    }

    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class UserListIterator implements Iterator<User> {

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
