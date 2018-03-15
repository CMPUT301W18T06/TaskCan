package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by m_qui on 3/12/2018.
 */

public class Bids implements Iterable<Bid> {
    private ArrayList<Bid> bids;

    public Bids() {
        this.bids = new ArrayList<Bid>();
    }

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

    public Bid getBid(int i) {
        return this.bids.get(i);
    }

    public Iterator<Bid> iterator() {
        return new BidListIterator();
    }

    public Boolean bidderExists(User bidder) {
        for (Bid b : this.bids) {
            if(b.getBidder().getEmail().equals(bidder.getEmail()))
                return true;
        }
        return false;
    }

    public int indexOfBidContaining(User bidder) {
        for (int i = 0; i < this.bids.size(); i++) {
            if(getBid(i).getBidder().getEmail().equals(bidder.getEmail()))
                return i;
        }
        throw new NoSuchElementException();
    }

    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class BidListIterator implements Iterator<Bid> {

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
