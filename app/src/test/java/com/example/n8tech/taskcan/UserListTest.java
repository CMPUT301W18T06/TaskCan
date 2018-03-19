package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.Models.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by nbelayne on 3/19/18.
 */

public class UserListTest {

    public UserListTest(){

    }

    @Test
    // ensure you can add Users to UserList
    public void addUserToUserList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny@n8tech.com", "iloveshopping", "781-113-1001");
        UserList newList = new UserList();
        newList.addUser(user1);
        newList.addUser(user2);
        newList.addUser(user3);
        newList.addUser(user4);
        newList.addUser(user5);
        newList.addUser(user6);
        assertEquals(newList.getUser(0), user1);
        assertEquals(newList.getUser(1), user2);
        assertEquals(newList.getUser(2), user3);
        assertEquals(newList.getUser(3), user4);
        assertEquals(newList.getUser(4), user5);
        assertEquals(newList.getUser(5), user6);
        try{
            assertEquals(newList.getUser(6), user7);
        }
        catch(IndexOutOfBoundsException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        if(exceptionCatcher < 1){
            assertEquals(true, false);
        }
    }

    @Test
    // ensure you can remove Users from UserList and not Users that aren't in the UserList
    public void removeUserFromUserList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny@n8tech.com", "iloveshopping", "781-113-1001");
        UserList newList = new UserList();
        newList.addUser(user1);
        newList.addUser(user2);
        newList.addUser(user3);
        newList.addUser(user4);
        newList.addUser(user5);
        newList.addUser(user6);
        assertEquals(newList.getUser(0), user1);
        assertEquals(newList.getUser(1), user2);
        assertEquals(newList.getUser(2), user3);
        assertEquals(newList.getUser(3), user4);
        assertEquals(newList.getUser(4), user5);
        assertEquals(newList.getUser(5), user6);
        newList.delUser(user2);
        newList.delUser(user4);
        newList.delUser(user6);
        assertEquals(newList.getUser(0), user1);
        assertEquals(newList.getUser(1), user3);
        assertEquals(newList.getUser(2), user5);
        assertEquals(newList.delUser(user7), false);
    }

    @Test
    // ensure Users are retrieved from BidList in correct order
    public void getUserFromUserList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny@n8tech.com", "iloveshopping", "781-113-1001");
        UserList newList = new UserList();
        newList.addUser(user1);
        newList.addUser(user2);
        newList.addUser(user3);
        newList.addUser(user4);
        newList.addUser(user5);
        newList.addUser(user6);
        assertEquals(newList.getUser(0), user1);
        assertEquals(newList.getUser(1), user2);
        assertEquals(newList.getUser(2), user3);
        assertEquals(newList.getUser(3), user4);
        assertEquals(newList.getUser(4), user5);
        assertEquals(newList.getUser(5), user6);
        assertEquals(newList.getUserIndex(user6), 5);
        assertEquals(newList.getUserIndex(user4), 3);
        assertEquals(newList.getUserIndex(user2), 1);
        assertEquals(newList.getUserIndex(user1), 0);
        assertEquals(newList.getUserIndex(user3), 2);
        assertEquals(newList.getUserIndex(user5), 4);
        assertEquals(newList.getUserIndex(user7), -1);
        try{
            assertEquals(newList.getUser(-1), user1);
        }
        catch(IndexOutOfBoundsException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        try{
            assertEquals(newList.getUser(6), user6);
        }
        catch(IndexOutOfBoundsException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        if(exceptionCatcher < 2){
            assertEquals(true, false);
        }
    }

    @Test
    // ensures that the getSize() method returns the correct size
    public void getSizeOfUserList() {
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro@n8tech.com", "iloveschool", "780-123-0000");
        UserList newList = new UserList();
        newList.addUser(user1);
        newList.addUser(user2);
        newList.addUser(user3);
        newList.addUser(user4);
        newList.addUser(user5);
        assertEquals(newList.getSize(), 5);
        newList.delUser(user5);
        assertEquals(newList.getSize(), 4);
        newList.delUser(user1);
        newList.delUser(user2);
        assertEquals(newList.getSize(), 2);
        assertEquals(newList.delUser(user6), false);
        newList.addUser(user6);
        assertEquals(newList.getSize(), 3);
    }

    @Test
    // ensure Iterator works
    public void testIterator(){
        User user1 = new User("Joe", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro@n8tech.com", "iloveschool", "780-123-0000");
        UserList newList = new UserList();
        newList.addUser(user1);
        newList.addUser(user2);
        newList.addUser(user3);
        newList.addUser(user4);
        newList.addUser(user5);
        newList.addUser(user6);
        ArrayList<User> UserArrayList = new ArrayList<User>();
        UserArrayList.add(user1);
        UserArrayList.add(user2);
        UserArrayList.add(user3);
        UserArrayList.add(user4);
        UserArrayList.add(user5);
        UserArrayList.add(user6);
        Iterator<User> UserItr = newList.iterator();
        while(UserItr.hasNext()) {
            for(int i = 0; i < UserArrayList.size(); i++){
                User element = UserItr.next();
                assertEquals(element, UserArrayList.get(i));
            }
        }
    }
}
