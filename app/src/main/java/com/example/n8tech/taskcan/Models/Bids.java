package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by m_qui on 3/12/2018.
 */

public class Bids implements Iterable<Bid> {
    private ArrayList<Bid> bidList;

    public Bids() {
        bidList = new ArrayList<Bid>();
    }

    public void addBidder(Bid bid) {
        bidList.add(bid);
    }

    public Bid getBidder(int i) {
        return bidList.get(i);
    }

    public Iterator<Bid> iterator() {
        return new BidderListIterator();
    }

    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class BidderListIterator implements Iterator<Bid> {

        private int index = 0;

        public boolean hasNext() {
            return (this.index < bidList.size());
        }

        public Bid next() {
            return bidList.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");
        }
    }
}
