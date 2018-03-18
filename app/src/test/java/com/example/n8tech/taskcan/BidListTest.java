package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.User;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by nbelayne on 3/18/18.
 */

public class BidListTest {

    public BidListTest(){

    }

    @Test
    // ensure you can add Bids to BidList using both Bid constructors &  every bid needs a defined User
    public void addBidToBidList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny@n8tech.com", "iloveshopping", "781-113-1001");
        Bid bid1 = new Bid(user1, 1.00);
        Bid bid2 = new Bid(user2, 12.00);
        Bid bid3 = new Bid(user3, 14.80);
        Bid bid4 = new Bid(user4, 17.68);
        Bid bid5 = new Bid(user5, 159.47);
        Bid bid6 = new Bid();
        bid6.setBidder(user6);
        Bid bid7 = new Bid();
        bid7.setBidAmount(100.00);
        bid7.setBidder(user7);
        Bid bid8 = new Bid();
        bid8.setBidAmount(300.00);
        BidList newList = new BidList();
        newList.addBid(bid1);
        newList.addBid(bid2);
        newList.addBid(bid3);
        newList.addBid(bid4);
        newList.addBid(bid5);
        newList.addBid(bid6);
        newList.addBid(bid7);
        try{
            newList.addBid(bid8);
        }
        catch(NullPointerException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        assertEquals(newList.getBid(0), bid1);
        assertEquals(newList.getBid(1), bid2);
        assertEquals(newList.getBid(2), bid3);
        assertEquals(newList.getBid(3), bid4);
        assertEquals(newList.getBid(4), bid5);
        assertEquals(newList.getBid(5), bid6);
        assertEquals(newList.getBid(6), bid7);
        try{
            assertEquals(newList.getBid(7), bid8);
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
    // ensure Bid's are retrieved from BidList in correct order
    public void getBidFromBidList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro@n8tech.com", "iloveschool", "780-123-0000");
        Bid bid1 = new Bid(user1, 1.00);
        Bid bid2 = new Bid(user2, 12.00);
        Bid bid3 = new Bid(user3, 14.80);
        Bid bid4 = new Bid(user4, 17.68);
        Bid bid5 = new Bid(user5, 159.47);
        Bid bid6 = new Bid(user6, 0.05);
        BidList newList = new BidList();
        newList.addBid(bid1);
        newList.addBid(bid3);
        newList.addBid(bid5);
        newList.addBid(bid6);
        newList.addBid(bid4);
        newList.addBid(bid2);
        assertEquals(newList.getBid(0), bid1);
        assertEquals(newList.getBid(1), bid3);
        assertEquals(newList.getBid(2), bid5);
        assertEquals(newList.getBid(3), bid6);
        assertEquals(newList.getBid(4), bid4);
        assertEquals(newList.getBid(5), bid2);
        try{
            assertEquals(newList.getBid(-1), bid1);
        }
        catch(IndexOutOfBoundsException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        try{
            assertEquals(newList.getBid(6), bid5);
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
    //
    public void getSizeOfBidList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro@n8tech.com", "iloveschool", "780-123-0000");
        Bid bid1 = new Bid(user1, 1.00);
        Bid bid2 = new Bid(user2, 12.00);
        Bid bid3 = new Bid(user3, 14.80);
        Bid bid4 = new Bid(user4, 17.68);
        Bid bid5 = new Bid(user5, 159.47);
        Bid bid6 = new Bid(user6, 0.05);
        BidList newList = new BidList();
        newList.addBid(bid1);
        newList.addBid(bid2);
        newList.addBid(bid3);
        newList.addBid(bid4);
        newList.addBid(bid5);
        newList.addBid(bid6);
        assertEquals(newList.getSize(), 6);
//        assertEquals(newList.getBid(1), bid3);
//        assertEquals(newList.getBid(2), bid5);
//        assertEquals(newList.getBid(3), bid6);
//        assertEquals(newList.getBid(4), bid4);
//        assertEquals(newList.getBid(5), bid2);
//        try{
//            assertEquals(newList.getBid(-1), bid1);
//        }
//        catch(IndexOutOfBoundsException e){
//            exceptionCatcher++;
//            assertEquals(true, true);
//        }
//        try{
//            assertEquals(newList.getBid(6), bid5);
//        }
//        catch(IndexOutOfBoundsException e){
//            exceptionCatcher++;
//            assertEquals(true, true);
//        }
//        if(exceptionCatcher < 2){
//            assertEquals(true, false);
//        }
    }
}
