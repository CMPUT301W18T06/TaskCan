package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by nbelayne on 3/19/18.
 */

public class CurrentUserSingletonTest {

    public CurrentUserSingletonTest(){

    }

    @Test
    // ensures that the getUser, setUser, and resetCurrentUser methods work
    public void testResetCurrentUser(){
        User user1 = new User("Joe", "joe123", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan123", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        CurrentUserSingleton single = new CurrentUserSingleton();
        assertEquals(single.getUser().getUsername(), null);
        single.setUser(user1);
        assertEquals(single.getUser(), user1);
        single.resetCurrentUser();
        assertNotEquals(single.getUser(), user1);
        assertEquals(single.getUser().getUsername(), null);
        single.setUser(user2);
        assertEquals(single.getUser(), user2);
        single.resetCurrentUser();
        assertNotEquals(single.getUser(), user2);
        assertEquals(single.getUser().getUsername(), null);
        single.setUser(user3);
        assertEquals(single.getUser(), user3);
    }

    @Test
    // test to see if getInstance method works
    public void testGetInstance(){
        CurrentUserSingleton single = new CurrentUserSingleton();
        assertEquals(single.getInstance().getClass(), CurrentUserSingleton.class);
        assertEquals(single.getInstance(), single.getInstance());
    }
}
