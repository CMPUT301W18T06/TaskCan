package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * BidList contains an iterable ArrayList of Bid objects as well as
 * methods that check for a specific bidder, return specific bidder indeces
 * and the lowest bid on the list based on bid amount.
 *
 * @author CMPUT301W18T06
 * @see Bid
 */

public class BidList implements Iterable<Bid> {
    private ArrayList<Bid> bids;
    private Bid acceptedBid = null;

    /**
     * Creates an instance of BidList, creating a new ArrayList of Bids.
     */
    public BidList() {
        this.bids = new ArrayList<Bid>();
    }

    /**
     * Adds a new bid to the list.
     * If a bid exists with the same bidder, the bid is updated with the new bid amount.
     * @param bid Bid to be added to the BidList
     */
    public void addBid(Bid bid) { this.bids.add(bid); }

    /**
     * Updates a bid when the bidder creates a new bid on the same task.
     * Removes the previous bid made by the bidder and creates a new bid with a different bid amount.
     * @param bid New bid object with updated details
     * @param i Integer representing bid index of bid to be removed
     */
    public void updateBid(Bid bid, int i) {
        this.bids.remove(i);
        this.bids.add(bid);
    }

    /**
     * @param bid the bid to be removed
     * @throws IllegalArgumentException If bidder does not exist on the list.
     */
    public void removeBid(Bid bid){
        this.bids.remove(bid);
    }

    /**
     * @param i integer representing BidList index
     * @return bid with the corresponding index
     */
    public Bid getBid(int i) {
        return this.bids.get(i);
    }

    /**
     * Returns the index of a bid on the list made by the bidder.
     * @param bid task provider that has created a bid
     * @return i BidList index
     * @throws NoSuchElementException If bidder does not exist in the BidList.
     */
    public int getBidIndex(Bid bid) { return this.bids.indexOf(bid); }

    /**
     * Takes a user ID as a parameter and returns the bid index.
     * Returns error code if bidder does not exist in the bid list.
     * @param userId user ID
     * @return integer representing the index of the user's bid
     */
    public int getBidUserIndex(String userId) {
        for (Bid bid : this.bids) {
            if(bid.getBidId() == userId) {
                return this.bids.indexOf(bid);
            }
        }
        return -1;
    }

    public void replaceAtIndex(int index, Bid bid){
        this.bids.set(index, bid);
    }

    public Bid getAcceptedBid() {
        return acceptedBid;
    }

    public void setAcceptedBid(Bid acceptedBid) {
        this.acceptedBid = acceptedBid;
    }

    // when requester cancels a bid, clear assignee
    public void clearAcceptedBid(){
        this.acceptedBid = null;

    }

    /** @return integer representing list size */
    public int getSize() {
        return this.bids.size();
    }

    /**
     * Checks for the lowest bid in the list based on bidAmount value.
     * @return bid
     */
    public Bid getLowestBid() {
        int lowestBidIndex = 0;
        for (int currentBidIndex = 0; currentBidIndex < this.getSize(); currentBidIndex++) {
            if (this.bids.get(currentBidIndex).getBidAmount() <
                    this.bids.get(lowestBidIndex).getBidAmount())
                lowestBidIndex = currentBidIndex;
        }
        return this.bids.get(lowestBidIndex);
    }

    /** Creates a BidList iterator. */
    public Iterator<Bid> iterator() {
        return new BidsIterator();
    }

    /**
     * Iterator object over BidList.
     * @throws UnsupportedOperationException If remove() method is called
     */
    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class BidsIterator implements Iterator<Bid> {

        private int index = 0;

        public boolean hasNext() {
            return (this.index < bids.size());
        }

        public Bid next() {
            return bids.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");
        }
    }
}
