package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.BiddedTask;
import com.example.n8tech.taskcan.Models.BiddedTaskList;
import com.example.n8tech.taskcan.Models.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Unit testing for BiddedTaskList class.
 *
 * @see BiddedTaskList
 * @author CMPUT301W18T06
 */

public class BiddedTaskListTest {

    public BiddedTaskListTest(){

    }

    @Test
    // ensure you can add Task to TaskList
    public void addTaskToTaskList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny12345","jenny@n8tech.com", "iloveshopping", "781-113-1001");
        BiddedTask task1 = new BiddedTask("Walk the dog", "Walk dog around the corner", "1", user1.getUsername(), "6543210", "Pets");
        BiddedTask task2 = new BiddedTask("Vaccuum my bedroom", "Vaccuum tough to get spots", "2", user2.getUsername(), "1596874", "Housework");
        BiddedTask task3 = new BiddedTask("Cut the grass", "Mow my lawn","3", user3.getUsername(), "7536548", "Outdoors");
        BiddedTask task4 = new BiddedTask("Paint my walls", "Paint walls red","4", user4.getUsername(), "1973645", "Painting");
        BiddedTask task5 = new BiddedTask("Drive me to school", "Be my limo driver","5", user5.getUsername(), "5971350", "Driving");
        BiddedTask task6 = new BiddedTask("Guard my treasure", "Guard my diamonds", "6", user6.getUsername(), "4682913", "Security");
        BiddedTask task7 = new BiddedTask("Fix my car", "Give me a new engine", "7", user7.getUsername(), "3192546", "Auto");
        BiddedTaskList newList = new BiddedTaskList();
        newList.addBiddedTask(task1);
        newList.addBiddedTask(task2);
        newList.addBiddedTask(task3);
        newList.addBiddedTask(task4);
        newList.addBiddedTask(task5);
        newList.addBiddedTask(task6);
        assertEquals(newList.getBiddedTaskAtIndex(0), task1);
        assertEquals(newList.getBiddedTaskAtIndex(1), task2);
        assertEquals(newList.getBiddedTaskAtIndex(2), task3);
        assertEquals(newList.getBiddedTaskAtIndex(3), task4);
        assertEquals(newList.getBiddedTaskAtIndex(4), task5);
        assertEquals(newList.getBiddedTaskAtIndex(5), task6);
        try{
            assertEquals(newList.getBiddedTaskAtIndex(6), task7);
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
    // ensure you can remove BiddedTasks from BiddedTaskList and not BiddedTasks that aren't in the BiddedTaskList
    public void removeBiddedTaskFromBiddedTaskList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny12345","jenny@n8tech.com", "iloveshopping", "781-113-1001");
        BiddedTask task1 = new BiddedTask("Walk the dog", "Walk dog around the corner", "1", user1.getUsername(), "6543210", "Pets");
        BiddedTask task2 = new BiddedTask("Vaccuum my bedroom", "Vaccuum tough to get spots", "2", user2.getUsername(), "1596874", "Housework");
        BiddedTask task3 = new BiddedTask("Cut the grass", "Mow my lawn","3", user3.getUsername(), "7536548", "Outdoors");
        BiddedTask task4 = new BiddedTask("Paint my walls", "Paint walls red","4", user4.getUsername(), "1973645", "Painting");
        BiddedTask task5 = new BiddedTask("Drive me to school", "Be my limo driver","5", user5.getUsername(), "5971350", "Driving");
        BiddedTask task6 = new BiddedTask("Guard my treasure", "Guard my diamonds", "6", user6.getUsername(), "4682913", "Security");
        BiddedTask task7 = new BiddedTask("Fix my car", "Give me a new engine", "7", user7.getUsername(), "3192546", "Auto");
        BiddedTaskList newList = new BiddedTaskList();
        newList.addBiddedTask(task1);
        newList.addBiddedTask(task2);
        newList.addBiddedTask(task3);
        newList.addBiddedTask(task4);
        newList.addBiddedTask(task5);
        newList.addBiddedTask(task6);
        assertEquals(newList.getBiddedTaskAtIndex(0), task1);
        assertEquals(newList.getBiddedTaskAtIndex(1), task2);
        assertEquals(newList.getBiddedTaskAtIndex(2), task3);
        assertEquals(newList.getBiddedTaskAtIndex(3), task4);
        assertEquals(newList.getBiddedTaskAtIndex(4), task5);
        assertEquals(newList.getBiddedTaskAtIndex(5), task6);
        newList.removeBiddedTask(task2);
        newList.removeBiddedTask(task4);
        newList.removeBiddedTask(task6);
        assertEquals(newList.getBiddedTaskAtIndex(0), task1);
        assertEquals(newList.getBiddedTaskAtIndex(1), task3);
        assertEquals(newList.getBiddedTaskAtIndex(2), task5);
        newList.removeBiddedTask(task7);
        assertEquals(newList.getBiddedTaskAtIndex(0), task1);
        assertEquals(newList.getBiddedTaskAtIndex(1), task3);
        assertEquals(newList.getBiddedTaskAtIndex(2), task5);
        try{
            assertEquals(newList.getBiddedTaskAtIndex(3), task7);
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
    // ensure BiddedTasks are retrieved from BiddedTaskList in correct order
    public void getBiddedTaskFromBiddedTaskList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny12345","jenny@n8tech.com", "iloveshopping", "781-113-1001");
        BiddedTask task1 = new BiddedTask("Walk the dog", "Walk dog around the corner", "1", user1.getUsername(), "6543210", "Pets");
        BiddedTask task2 = new BiddedTask("Vaccuum my bedroom", "Vaccuum tough to get spots", "2", user2.getUsername(), "1596874", "Housework");
        BiddedTask task3 = new BiddedTask("Cut the grass", "Mow my lawn", "3", user3.getUsername(), "7536548", "Outdoors");
        BiddedTask task4 = new BiddedTask("Paint my walls", "Paint walls red", "4", user4.getUsername(), "1973645", "Painting");
        BiddedTask task5 = new BiddedTask("Drive me to school", "Be my limo driver", "5", user5.getUsername(), "5971350", "Driving");
        BiddedTask task6 = new BiddedTask("Guard my treasure", "Guard my diamonds", "6", user6.getUsername(), "4682913", "Security");
        BiddedTask task7 = new BiddedTask("Fix my car", "Give me a new engine", "7", user7.getUsername(), "3192546", "Auto");
        BiddedTaskList newList = new BiddedTaskList();
        task1.setTaskId("1");
        task2.setTaskId("2");
        task3.setTaskId("3");
        task4.setTaskId("4");
        task5.setTaskId("5");
        task6.setTaskId("6");
        newList.addBiddedTask(task1);
        newList.addBiddedTask(task2);
        newList.addBiddedTask(task3);
        newList.addBiddedTask(task4);
        newList.addBiddedTask(task5);
        newList.addBiddedTask(task6);
        assertEquals(newList.getBiddedTaskAtIndex(0), task1);
        assertEquals(newList.getBiddedTaskAtIndex(1), task2);
        assertEquals(newList.getBiddedTaskAtIndex(2), task3);
        assertEquals(newList.getBiddedTaskAtIndex(3), task4);
        assertEquals(newList.getBiddedTaskAtIndex(4), task5);
        assertEquals(newList.getBiddedTaskAtIndex(5), task6);
        assertEquals(newList.getIndexOfBiddedTask(task6), 5);
        assertEquals(newList.getIndexOfBiddedTask(task4), 3);
        assertEquals(newList.getIndexOfBiddedTask(task2), 1);
        assertEquals(newList.getIndexOfBiddedTask(task1), 0);
        assertEquals(newList.getIndexOfBiddedTask(task3), 2);
        assertEquals(newList.getIndexOfBiddedTask(task5), 4);
        assertEquals(newList.getIndexOfBiddedTask(task7), -1);
        try{
            assertEquals(newList.getBiddedTaskAtIndex(-1), user1);
        }
        catch(IndexOutOfBoundsException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        try{
            assertEquals(newList.getBiddedTaskAtIndex(6), user6);
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
    // ensure that tasks can be replaced in a BiddedTaskList
    public void replaceBiddedTask(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny12345","jenny@n8tech.com", "iloveshopping", "781-113-1001");
        BiddedTask task1 = new BiddedTask("Walk the dog", "Walk dog around the corner", "1", user1.getUsername(), "6543210", "Pets");
        BiddedTask task2 = new BiddedTask("Vaccuum my bedroom", "Vaccuum tough to get spots", "2", user2.getUsername(), "1596874", "Housework");
        BiddedTask task3 = new BiddedTask("Cut the grass", "Mow my lawn", "3", user3.getUsername(), "7536548", "Outdoors");
        BiddedTask task4 = new BiddedTask("Paint my walls", "Paint walls red", "4", user4.getUsername(), "1973645", "Painting");
        BiddedTask task5 = new BiddedTask("Drive me to school", "Be my limo driver", "5", user5.getUsername(), "5971350", "Driving");
        BiddedTask task6 = new BiddedTask("Guard my treasure", "Guard my diamonds", "6", user6.getUsername(), "4682913", "Security");
        BiddedTask task7 = new BiddedTask("Fix my car", "Give me a new engine", "7", user7.getUsername(), "3192546", "Auto");
        BiddedTaskList newList = new BiddedTaskList();
        newList.addBiddedTask(task1);
        newList.addBiddedTask(task2);
        newList.addBiddedTask(task3);
        newList.addBiddedTask(task4);
        newList.addBiddedTask(task5);
        newList.addBiddedTask(task6);
        assertEquals(newList.getBiddedTaskAtIndex(0), task1);
        assertEquals(newList.getBiddedTaskAtIndex(1), task2);
        assertEquals(newList.getBiddedTaskAtIndex(2), task3);
        assertEquals(newList.getBiddedTaskAtIndex(3), task4);
        assertEquals(newList.getBiddedTaskAtIndex(4), task5);
        assertEquals(newList.getBiddedTaskAtIndex(5), task6);
        newList.replaceAtIndex(5, task7);
        assertEquals(newList.getBiddedTaskAtIndex(5), task7);
        newList.replaceAtIndex(3, task2);
        assertEquals(newList.getBiddedTaskAtIndex(3), task2);
        newList.replaceAtIndex(1, task6);
        assertEquals(newList.getBiddedTaskAtIndex(1), task6);
        try{
            newList.replaceAtIndex(-1, task1);
            assertEquals(newList.getBiddedTaskAtIndex(-1), user1);
        }
        catch(IndexOutOfBoundsException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        try{
            newList.replaceAtIndex(6, task7);
            assertEquals(newList.getBiddedTaskAtIndex(6), task7);
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
    public void getSizeOfBiddedTaskList() {
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        BiddedTask task1 = new BiddedTask("Walk the dog", "Walk dog around the corner", "1", user1.getUsername(), "6543210", "Pets");
        BiddedTask task2 = new BiddedTask("Vaccuum my bedroom", "Vaccuum tough to get spots", "2", user2.getUsername(), "1596874", "Housework");
        BiddedTask task3 = new BiddedTask("Cut the grass", "Mow my lawn","3", user3.getUsername(), "7536548", "Outdoors");
        BiddedTask task4 = new BiddedTask("Paint my walls", "Paint walls red","4", user4.getUsername(), "1973645", "Painting");
        BiddedTask task5 = new BiddedTask("Drive me to school", "Be my limo driver","5", user5.getUsername(), "5971350", "Driving");
        BiddedTask task6 = new BiddedTask("Guard my treasure", "Guard my diamonds", "6", user6.getUsername(), "4682913", "Security");
        BiddedTaskList newList = new BiddedTaskList();
        newList.addBiddedTask(task1);
        newList.addBiddedTask(task2);
        newList.addBiddedTask(task3);
        newList.addBiddedTask(task4);
        newList.addBiddedTask(task5);
        assertEquals(newList.getSize(), 5);
        newList.removeBiddedTask(task5);
        assertEquals(newList.getSize(), 4);
        newList.removeBiddedTask(task1);
        newList.removeBiddedTask(task2);
        assertEquals(newList.getSize(), 2);
        newList.removeBiddedTask(task6);
        assertEquals(newList.getSize(), 2);
        newList.addBiddedTask(task6);
        assertEquals(newList.getSize(), 3);
    }

    @Test
    // ensure Iterator works
    public void testIterator(){
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        BiddedTask task1 = new BiddedTask("Walk the dog", "Walk dog around the corner", "1", user1.getUsername(), "6543210", "Pets");
        BiddedTask task2 = new BiddedTask("Vaccuum my bedroom", "Vaccuum tough to get spots", "2", user2.getUsername(), "1596874", "Housework");
        BiddedTask task3 = new BiddedTask("Cut the grass", "Mow my lawn","3", user3.getUsername(), "7536548", "Outdoors");
        BiddedTask task4 = new BiddedTask("Paint my walls", "Paint walls red","4", user4.getUsername(), "1973645", "Painting");
        BiddedTask task5 = new BiddedTask("Drive me to school", "Be my limo driver","5", user5.getUsername(), "5971350", "Driving");
        BiddedTask task6 = new BiddedTask("Guard my treasure", "Guard my diamonds", "6", user6.getUsername(), "4682913", "Security");
        BiddedTaskList newList = new BiddedTaskList();
        newList.addBiddedTask(task1);
        newList.addBiddedTask(task2);
        newList.addBiddedTask(task3);
        newList.addBiddedTask(task4);
        newList.addBiddedTask(task5);
        newList.addBiddedTask(task6);
        ArrayList<BiddedTask> BiddedTaskArrayList = new ArrayList<BiddedTask>();
        BiddedTaskArrayList.add(task1);
        BiddedTaskArrayList.add(task2);
        BiddedTaskArrayList.add(task3);
        BiddedTaskArrayList.add(task4);
        BiddedTaskArrayList.add(task5);
        BiddedTaskArrayList.add(task6);
        Iterator<BiddedTask> BiddedTaskItr = newList.iterator();
        while(BiddedTaskItr.hasNext()) {
            for(int i = 0; i < BiddedTaskArrayList.size(); i++){
                BiddedTask element = BiddedTaskItr.next();
                assertEquals(element, BiddedTaskArrayList.get(i));
            }
        }
    }
}
