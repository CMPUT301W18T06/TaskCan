package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Unit testing for BidList class.
 *
 * @see BidList
 * @author CMPUT301W18T06
 */

public class BidListTest {

    public BidListTest(){

    }

    @Test
    // ensure you can add Bids to BidList using both Bid constructors &  every bid needs a defined User
    public void addBidToBidList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe123",  "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan","alan123", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan","nathan23", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt","matt123", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex","alex123", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro123","caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny123","jenny@n8tech.com", "iloveshopping", "781-113-1001");
        Bid bid1 = new Bid(user1.getUsername(), "1",  1.00);
        Bid bid2 = new Bid(user2.getUsername(), "2",12.00);
        Bid bid3 = new Bid(user3.getUsername(), "3",14.80);
        Bid bid4 = new Bid(user4.getUsername(), "4", 17.68);
        Bid bid5 = new Bid(user5.getUsername(), "5",159.47);
        Bid bid6 = new Bid();
        bid6.setBidUsername(user6.getUsername());
        Bid bid7 = new Bid();
        bid7.setBidAmount(100.00);
        bid7.setBidUsername(user7.getUsername());
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
    // ensure you can remove Bids from BidList and not Bids that aren't in the BidList
    public void removeBidFromBidList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe123",  "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan","alan123", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan","nathan23", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt","matt123", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex","alex123", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro123","caro@n8tech.com", "iloveschool", "780-123-0000");
        Bid bid1 = new Bid(user1.getUsername(), "1", 1.00);
        Bid bid2 = new Bid(user2.getUsername(),"2", 12.00);
        Bid bid3 = new Bid(user3.getUsername(),"3", 14.80);
        Bid bid4 = new Bid(user4.getUsername(),"4", 17.68);
        Bid bid5 = new Bid(user5.getUsername(),"5", 159.47);
        Bid bid6 = new Bid(user6.getUsername(),"6", 0.05);
        BidList newList = new BidList();
        newList.addBid(bid1);
        newList.addBid(bid2);
        newList.addBid(bid3);
        newList.addBid(bid4);
        newList.addBid(bid5);
        newList.removeBid(bid5);
        newList.removeBid(bid1);
        newList.removeBid(bid3);
        assertEquals(newList.getBid(0), bid2);
        assertEquals(newList.getBid(1), bid4);
        try{
            newList.removeBid(bid6);
        }
        catch(IllegalArgumentException e){
            exceptionCatcher++;
            assertEquals(true, true);
        }
        if(exceptionCatcher < 1){
            assertEquals(true, false);
        }
    }

    @Test
    // ensure Bid's are retrieved from BidList in correct order
    public void getBidFromBidList(){
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe123",  "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan","alan123", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan","nathan23", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt","matt123", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex","alex123", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro123","caro@n8tech.com", "iloveschool", "780-123-0000");
        Bid bid1 = new Bid(user1.getUsername(), "1", 1.00);
        Bid bid2 = new Bid(user2.getUsername(),"2", 12.00);
        Bid bid3 = new Bid(user3.getUsername(),"3", 14.80);
        Bid bid4 = new Bid(user4.getUsername(),"4", 17.68);
        Bid bid5 = new Bid(user5.getUsername(),"5", 159.47);
        Bid bid6 = new Bid(user6.getUsername(),"6", 0.05);
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
    // ensures that the getSize() method returns the correct size
    public void getSizeOfBidList() {
        int exceptionCatcher = 0;
        User user1 = new User("Joe", "joe123",  "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan","alan123", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan","nathan23", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt","matt123", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex","alex123", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro123","caro@n8tech.com", "iloveschool", "780-123-0000");
        Bid bid1 = new Bid(user1.getUsername(), "1", 1.00);
        Bid bid2 = new Bid(user2.getUsername(),"2", 12.00);
        Bid bid3 = new Bid(user3.getUsername(),"3", 14.80);
        Bid bid4 = new Bid(user4.getUsername(),"4", 17.68);
        Bid bid5 = new Bid(user5.getUsername(),"5", 159.47);
        Bid bid6 = new Bid(user6.getUsername(),"6", 0.05);
        BidList newList = new BidList();
        newList.addBid(bid1);
        newList.addBid(bid2);
        newList.addBid(bid3);
        newList.addBid(bid4);
        newList.addBid(bid5);
        assertEquals(newList.getSize(), 5);
        newList.removeBid(bid5);
        assertEquals(newList.getSize(), 4);
        newList.removeBid(bid1);
        newList.removeBid(bid3);
        assertEquals(newList.getSize(), 2);
        try {
            newList.removeBid(bid6);
        }
        catch (IllegalArgumentException e) {
            exceptionCatcher++;
            assertEquals(true, true);
        }
        newList.addBid(bid6);
        assertEquals(newList.getSize(), 3);
        if (exceptionCatcher < 1) {
            assertEquals(true, false);
        }
    }

    @Test
    // ensure getLowestBid() method can extract the correct lowest bid
    public void getLowestBidFromBidList () {
        User user1 = new User("Joe", "joe123",  "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan","alan123", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan","nathan23", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt","matt123", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex","alex123", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro123","caro@n8tech.com", "iloveschool", "780-123-0000");
        User user7 = new User("Jenny", "jenny123","jenny@n8tech.com", "iloveshopping", "781-113-1001");

        Bid bid1 = new Bid(user1.getUsername(), "1", 1.00);
        Bid bid2 = new Bid(user2.getUsername(),"2", 12.00);
        Bid bid3 = new Bid(user3.getUsername(),"3", 14.80);
        Bid bid4 = new Bid(user4.getUsername(),"4", 17.68);
        Bid bid5 = new Bid(user5.getUsername(),"5", 159.47);
        Bid bid6 = new Bid(user6.getUsername(),"6", 0.05);
        Bid bid7 = new Bid(user7.getUsername(), "7", 0.10);

        BidList newList = new BidList();
        newList.addBid(bid1);
        newList.addBid(bid2);
        newList.addBid(bid3);
        newList.addBid(bid4);
        newList.addBid(bid5);
        newList.addBid(bid6);
        assertEquals(newList.getLowestBid(), bid1);
        newList.removeBid(bid1);
        assertEquals(newList.getLowestBid(), bid3);
        newList.removeBid(bid3);
        newList.removeBid(bid4);
        assertEquals(newList.getLowestBid(), bid2);
        newList.addBid(bid7);
        assertEquals(newList.getLowestBid(), bid7);
    }

    @Test
    // ensure Iterator works
    public void testIterator(){
        User user1 = new User("Joe", "joe123",  "joe@n8tech.com", "7355608", "123-456-7890");
        User user2 = new User("Alan","alan123", "alan@n8tech.com", "ilovenate", "780-980-5623");
        User user3 = new User("Nathan","nathan23", "nathan@n8tech.com", "ilovealan", "780-750-5600");
        User user4 = new User("Matt","matt123", "matt@n8tech.com", "ilovefood", "783-126-8623");
        User user5 = new User("Alex","alex123", "alex@n8tech.com", "ilovecomputers", "780-888-5656");
        User user6 = new User("Caro", "caro123","caro@n8tech.com", "iloveschool", "780-123-0000");
        Bid bid1 = new Bid(user1.getUsername(), "1", 1.00);
        Bid bid2 = new Bid(user2.getUsername(),"2", 12.00);
        Bid bid3 = new Bid(user3.getUsername(),"3", 14.80);
        Bid bid4 = new Bid(user4.getUsername(),"4", 17.68);
        Bid bid5 = new Bid(user5.getUsername(),"5", 159.47);
        Bid bid6 = new Bid(user6.getUsername(),"6", 0.05);
        BidList newList = new BidList();
        newList.addBid(bid1);
        newList.addBid(bid2);
        newList.addBid(bid3);
        newList.addBid(bid4);
        newList.addBid(bid5);
        newList.addBid(bid6);
        ArrayList<Bid> bidArrayList = new ArrayList<Bid>();
        bidArrayList.add(bid1);
        bidArrayList.add(bid2);
        bidArrayList.add(bid3);
        bidArrayList.add(bid4);
        bidArrayList.add(bid5);
        bidArrayList.add(bid6);
        Iterator<Bid> bidsItr = newList.iterator();
        while(bidsItr.hasNext()) {
            for(int i = 0; i < bidArrayList.size(); i++){
                Bid element = bidsItr.next();
                assertEquals(element, bidArrayList.get(i));
            }
        }
    }
}
