package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * BidList contains an iterable ArrayList of Bid objects as well as
 * methods that check for a specific bidder, return specific bidder indeces
 * as well the lowest bid on the list based on bidAmount.
 *
 * @author CMPUT301W18T06
 * @see Bid
 */

public class BidList implements Iterable<Bid> {
    private ArrayList<Bid> bids;

    /**
     * Creates an instance of BidList creating a new ArrayList of Bids.
     */
    public BidList() {
        this.bids = new ArrayList<Bid>();
    }

    /**
     * Adds a new bid to the list.
     * If a bid exists with the same bidder, the bid is updated with the new bid amount.
     * @param bid Bid to be added to the BidList
     */
    public void addBid(Bid bid) {
        User bidder = bid.getBidder();
        if (this.bidderExists(bidder)) {
            int i = indexOfBidContaining(bidder);
            this.bids.set(i, bid);
        }
        else {
            this.bids.add(bid);
        }
    }

    /**
     * @param i integer representing BidList index
     * @return bid with the corresponding index
     */
    public Bid getBid(int i) {
        return this.bids.get(i);
    }

    /**
     * Creates a BidList iterator.
     */
    public Iterator<Bid> iterator() {
        return new BidsIterator();
    }

    /**
     * Checks if the bidder has made a bid on the list.
     * @param bidder task provider that has created a bid
     * @return true if bidder exists on the list, otherwise false
     */
    public Boolean bidderExists(User bidder) {
        for (Bid b : this.bids) {
            if(b.getBidder().getEmail().equals(bidder.getEmail()))
                return true;
            else if(bidder.getEmail().equals(null))
                break;
        }
        return false;
    }

    /**
     * Returns the index of a bid on the list made by the bidder.
     * @param bidder task provider that has created a bid
     * @return i BidList index
     * @throws NoSuchElementException If bidder does not exist in the BidList.
     */
    public int indexOfBidContaining(User bidder) {
        for (int i = 0; i < this.getSize(); i++) {
            if(getBid(i).getBidder().getEmail().equals(bidder.getEmail()))
                return i;
            else if(bidder.getEmail().equals(null))
                break;
        }
        throw new NoSuchElementException();
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
