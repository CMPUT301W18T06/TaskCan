package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.BiddedTask;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit testing for BiddedTask class.
 *
 * @see BiddedTask
 * @author CMPUT301W18T06
 */
public class BiddedTaskTest {

    public BiddedTaskTest(){
        }

        @Test
        public void testMakeBiddedTask() {
            User user1 = new User("Joe", "joe12345", "7355608", "joe@n8tech.com", "123-456-7890");
            User user2 = new User("Bill", "bill12345", "1111", "bill@n8tech.com", "098-765-4321");
            User user3 = new User("Mary", "mary12345", "1234", "mary@n8tech.com", "312-893-8293");
            Bid bid1 = new Bid(user3.getUsername(), "1", 18.91);
            Task task1 = new Task();
            BiddedTask biddedTask1 = new BiddedTask();

            task1.setTaskTitle("Walk my cat");
            task1.setDescription("Around the block");
            task1.setOwnerUsername(user1.getUsername());
            task1.setMaximumBid(20.00);
            task1.setCategory("Pet Care");

            biddedTask1 = biddedTask1.makeBiddedTask(task1, bid1);

            assertEquals(biddedTask1.getTaskTitle(), "Walk my cat");
            assertEquals(biddedTask1.getDescription(), "Around the block");
            assertEquals(biddedTask1.getTaskId(), task1.getId());
            assertEquals(biddedTask1.getOwnerUsername(), user1.getUsername());
            assertEquals(biddedTask1.getOwnerId(), task1.getOwnerId());
            assertEquals(biddedTask1.getCategory(), "Pet Care");
            assertEquals(biddedTask1.getMyBidAmount(), 18.91, 0.00);
            assertEquals(biddedTask1.getMaximumBid(), 20.00, 0.00);
            assertEquals(biddedTask1.isTaskCompleted(), false);
            assertEquals(biddedTask1.getStatus(), task1.getStatus());

        }

        @Test
        public void testOverwrites() {
            /*
             * Tests how different members of a task can be overwritten.
             */
            User user1 = new User("Joe", "joe12345", "7355608", "joe@n8tech.com", "123-456-7890");
            User user2 = new User("Bill", "bill12345", "1111", "bill@n8tech.com", "098-765-4321");
            User user3 = new User("Mary", "mary12345", "1234", "mary@n8tech.com", "312-893-8293");
            BiddedTask biddedTask1 = new BiddedTask();
            Bid bid1 = new Bid(user3.getUsername(), "1", 18.91);
            Task task1 = new Task();
            biddedTask1.makeBiddedTask(task1, bid1);


            biddedTask1.setTaskTitle("Walk my dog");
            biddedTask1.setDescription("Around the block");
            biddedTask1.setOwnerUsername(user1.getUsername());
            biddedTask1.setMaximumBid(20.00);
            biddedTask1.setCategory("Pet Care");
            //task1.setLocation("Edmonton");

            //Test that the name can be changed
            biddedTask1.setTaskTitle("Brush my teeth");
            assertEquals(biddedTask1.getTaskTitle(), "Brush my teeth");

            //Test that the description can be changed.
            biddedTask1.setDescription("Extremely Lazy");
            assertEquals(biddedTask1.getDescription(), "Extremely Lazy");

            //Test that the description can be set to null.
            biddedTask1.setDescription("");
            assertEquals(biddedTask1.getDescription(), "");

            //Test that the max bid can be increased.
            biddedTask1.setMaximumBid(25.00);
            assertEquals(biddedTask1.getMaximumBid(), 25.00, 0.00);

            //Test that the max bid can be decreased.
            biddedTask1.setMaximumBid(10.00);
            assertEquals(biddedTask1.getMaximumBid(), 10.00, 0.00);

            //Test that the category can be changed to an allowable option.
            biddedTask1.setCategory("Other");
            assertEquals(biddedTask1.getCategory(), "Other");

        }
    }


