package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Unit testing for BiddedTaskList class.
 *
 * @see TaskList
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
        Task task1 = new Task("Walk the dog", "Walk dog around the corner", user1.getUsername(), "6543210", "Pets");
        Task task2 = new Task("Vaccuum my bedroom", "Vaccuum tough to get spots", user2.getUsername(), "1596874", "Housework");
        Task task3 = new Task("Cut the grass", "Mow my lawn", user3.getUsername(), "7536548", "Outdoors");
        Task task4 = new Task("Paint my walls", "Paint walls red", user4.getUsername(), "1973645", "Painting");
        Task task5 = new Task("Drive me to school", "Be my limo driver", user5.getUsername(), "5971350", "Driving");
        Task task6 = new Task("Guard my treasure", "Guard my diamonds", user6.getUsername(), "4682913", "Security");
        Task task7 = new Task("Fix my car", "Give me a new engine", user7.getUsername(), "3192546", "Auto");
        TaskList newList = new TaskList();
        newList.addTask(task1);
        newList.addTask(task2);
        newList.addTask(task3);
        newList.addTask(task4);
        newList.addTask(task5);
        newList.addTask(task6);
        assertEquals(newList.getTaskAtIndex(0), task1);
        assertEquals(newList.getTaskAtIndex(1), task2);
        assertEquals(newList.getTaskAtIndex(2), task3);
        assertEquals(newList.getTaskAtIndex(3), task4);
        assertEquals(newList.getTaskAtIndex(4), task5);
        assertEquals(newList.getTaskAtIndex(5), task6);
        try{
            assertEquals(newList.getTaskAtIndex(6), task7);
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
    // ensure you can remove Tasks from TaskList and not Tasks that aren't in the TaskList
    public void removeTaskFromTaskList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny12345","jenny@n8tech.com", "iloveshopping", "781-113-1001");
        Task task1 = new Task("Walk the dog", "Walk dog around the corner", user1.getUsername(), "6543210", "Pets");
        Task task2 = new Task("Vaccuum my bedroom", "Vaccuum tough to get spots", user2.getUsername(), "1596874", "Housework");
        Task task3 = new Task("Cut the grass", "Mow my lawn", user3.getUsername(), "7536548", "Outdoors");
        Task task4 = new Task("Paint my walls", "Paint walls red", user4.getUsername(), "1973645", "Painting");
        Task task5 = new Task("Drive me to school", "Be my limo driver", user5.getUsername(), "5971350", "Driving");
        Task task6 = new Task("Guard my treasure", "Guard my diamonds", user6.getUsername(), "4682913", "Security");
        Task task7 = new Task("Fix my car", "Give me a new engine", user7.getUsername(), "3192546", "Auto");
        TaskList newList = new TaskList();
        newList.addTask(task1);
        newList.addTask(task2);
        newList.addTask(task3);
        newList.addTask(task4);
        newList.addTask(task5);
        newList.addTask(task6);
        assertEquals(newList.getTaskAtIndex(0), task1);
        assertEquals(newList.getTaskAtIndex(1), task2);
        assertEquals(newList.getTaskAtIndex(2), task3);
        assertEquals(newList.getTaskAtIndex(3), task4);
        assertEquals(newList.getTaskAtIndex(4), task5);
        assertEquals(newList.getTaskAtIndex(5), task6);
        newList.removeTask(task2);
        newList.removeTask(task4);
        newList.removeTask(task6);
        assertEquals(newList.getTaskAtIndex(0), task1);
        assertEquals(newList.getTaskAtIndex(1), task3);
        assertEquals(newList.getTaskAtIndex(2), task5);
        newList.removeTask(task7);
        assertEquals(newList.getTaskAtIndex(0), task1);
        assertEquals(newList.getTaskAtIndex(1), task3);
        assertEquals(newList.getTaskAtIndex(2), task5);
        try{
            assertEquals(newList.getTaskAtIndex(3), task7);
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
    // ensure Tasks are retrieved from TaskList in correct order
    public void getTaskFromTaskList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny12345","jenny@n8tech.com", "iloveshopping", "781-113-1001");
        Task task1 = new Task("Walk the dog", "Walk dog around the corner", user1.getUsername(), "6543210", "Pets");
        Task task2 = new Task("Vaccuum my bedroom", "Vaccuum tough to get spots", user2.getUsername(), "1596874", "Housework");
        Task task3 = new Task("Cut the grass", "Mow my lawn", user3.getUsername(), "7536548", "Outdoors");
        Task task4 = new Task("Paint my walls", "Paint walls red", user4.getUsername(), "1973645", "Painting");
        Task task5 = new Task("Drive me to school", "Be my limo driver", user5.getUsername(), "5971350", "Driving");
        Task task6 = new Task("Guard my treasure", "Guard my diamonds", user6.getUsername(), "4682913", "Security");
        Task task7 = new Task("Fix my car", "Give me a new engine", user7.getUsername(), "3192546", "Auto");
        TaskList newList = new TaskList();
        task1.setId("1");
        task2.setId("2");
        task3.setId("3");
        task4.setId("4");
        task5.setId("5");
        task6.setId("6");
        newList.addTask(task1);
        newList.addTask(task2);
        newList.addTask(task3);
        newList.addTask(task4);
        newList.addTask(task5);
        newList.addTask(task6);
        assertEquals(newList.getTaskAtIndex(0), task1);
        assertEquals(newList.getTaskAtIndex(1), task2);
        assertEquals(newList.getTaskAtIndex(2), task3);
        assertEquals(newList.getTaskAtIndex(3), task4);
        assertEquals(newList.getTaskAtIndex(4), task5);
        assertEquals(newList.getTaskAtIndex(5), task6);
        assertEquals(newList.getIndexOfTask(task6), 5);
        assertEquals(newList.getIndexOfTask(task4), 3);
        assertEquals(newList.getIndexOfTask(task2), 1);
        assertEquals(newList.getIndexOfTask(task1), 0);
        assertEquals(newList.getIndexOfTask(task3), 2);
        assertEquals(newList.getIndexOfTask(task5), 4);
        assertEquals(newList.getIndexOfTask(task7), -1);
        try{
            assertEquals(newList.getTaskAtIndex(-1), user1);
        }
        catch(IndexOutOfBoundsException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        try{
            assertEquals(newList.getTaskAtIndex(6), user6);
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
    // ensure that tasks can be replaced in a TaskList
    public void replaceTask(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny12345","jenny@n8tech.com", "iloveshopping", "781-113-1001");
        Task task1 = new Task("Walk the dog", "Walk dog around the corner", user1.getUsername(), "6543210", "Pets");
        Task task2 = new Task("Vaccuum my bedroom", "Vaccuum tough to get spots", user2.getUsername(), "1596874", "Housework");
        Task task3 = new Task("Cut the grass", "Mow my lawn", user3.getUsername(), "7536548", "Outdoors");
        Task task4 = new Task("Paint my walls", "Paint walls red", user4.getUsername(), "1973645", "Painting");
        Task task5 = new Task("Drive me to school", "Be my limo driver", user5.getUsername(), "5971350", "Driving");
        Task task6 = new Task("Guard my treasure", "Guard my diamonds", user6.getUsername(), "4682913", "Security");
        Task task7 = new Task("Fix my car", "Give me a new engine", user7.getUsername(), "3192546", "Auto");
        TaskList newList = new TaskList();
        newList.addTask(task1);
        newList.addTask(task2);
        newList.addTask(task3);
        newList.addTask(task4);
        newList.addTask(task5);
        newList.addTask(task6);
        assertEquals(newList.getTaskAtIndex(0), task1);
        assertEquals(newList.getTaskAtIndex(1), task2);
        assertEquals(newList.getTaskAtIndex(2), task3);
        assertEquals(newList.getTaskAtIndex(3), task4);
        assertEquals(newList.getTaskAtIndex(4), task5);
        assertEquals(newList.getTaskAtIndex(5), task6);
        newList.replaceAtIndex(5, task7);
        assertEquals(newList.getTaskAtIndex(5), task7);
        newList.replaceAtIndex(3, task2);
        assertEquals(newList.getTaskAtIndex(3), task2);
        newList.replaceAtIndex(1, task6);
        assertEquals(newList.getTaskAtIndex(1), task6);
        try{
            newList.replaceAtIndex(-1, task1);
            assertEquals(newList.getTaskAtIndex(-1), user1);
        }
        catch(IndexOutOfBoundsException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        try{
            newList.replaceAtIndex(6, task7);
            assertEquals(newList.getTaskAtIndex(6), task7);
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
    public void getSizeOfTaskList() {
        User user1 = new User("Joe", "joe12345", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan12345", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan123", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt12345", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex12345", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro12345", "caro@n8tech.com", "iloveschool", "780-123-0000");
        Task task1 = new Task("Walk the dog", "Walk dog around the corner", user1.getUsername(), "6543210", "Pets");
        Task task2 = new Task("Vaccuum my bedroom", "Vaccuum tough to get spots", user2.getUsername(), "1596874", "Housework");
        Task task3 = new Task("Cut the grass", "Mow my lawn", user3.getUsername(), "7536548", "Outdoors");
        Task task4 = new Task("Paint my walls", "Paint walls red", user4.getUsername(), "1973645", "Painting");
        Task task5 = new Task("Drive me to school", "Be my limo driver", user5.getUsername(), "5971350", "Driving");
        Task task6 = new Task("Guard my treasure", "Guard my diamonds", user6.getUsername(), "4682913", "Security");
        TaskList newList = new TaskList();
        newList.addTask(task1);
        newList.addTask(task2);
        newList.addTask(task3);
        newList.addTask(task4);
        newList.addTask(task5);
        assertEquals(newList.getSize(), 5);
        newList.removeTask(task5);
        assertEquals(newList.getSize(), 4);
        newList.removeTask(task1);
        newList.removeTask(task2);
        assertEquals(newList.getSize(), 2);
        newList.removeTask(task6);
        assertEquals(newList.getSize(), 2);
        newList.addTask(task6);
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
        Task task1 = new Task("Walk the dog", "Walk dog around the corner", user1.getUsername(), "6543210", "Pets");
        Task task2 = new Task("Vaccuum my bedroom", "Vaccuum tough to get spots", user2.getUsername(), "1596874", "Housework");
        Task task3 = new Task("Cut the grass", "Mow my lawn", user3.getUsername(), "7536548", "Outdoors");
        Task task4 = new Task("Paint my walls", "Paint walls red", user4.getUsername(), "1973645", "Painting");
        Task task5 = new Task("Drive me to school", "Be my limo driver", user5.getUsername(), "5971350", "Driving");
        Task task6 = new Task("Guard my treasure", "Guard my diamonds", user6.getUsername(), "4682913", "Security");
        TaskList newList = new TaskList();
        newList.addTask(task1);
        newList.addTask(task2);
        newList.addTask(task3);
        newList.addTask(task4);
        newList.addTask(task5);
        newList.addTask(task6);
        ArrayList<Task> TaskArrayList = new ArrayList<Task>();
        TaskArrayList.add(task1);
        TaskArrayList.add(task2);
        TaskArrayList.add(task3);
        TaskArrayList.add(task4);
        TaskArrayList.add(task5);
        TaskArrayList.add(task6);
        Iterator<Task> TaskItr = newList.iterator();
        while(TaskItr.hasNext()) {
            for(int i = 0; i < TaskArrayList.size(); i++){
                Task element = TaskItr.next();
                assertEquals(element, TaskArrayList.get(i));
            }
        }
    }
}
