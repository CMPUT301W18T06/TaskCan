package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by m_qui on 3/12/2018.
 */

public class BidderList implements Iterable<Bidder> {
    private ArrayList<Bidder> bidderList;

    public BidderList() {
        bidderList = new ArrayList<Bidder>();
    }

    public void addBidder(Bidder bidder) {
        bidderList.add(bidder);
    }

    public Bidder getBidder(int i) {
        return bidderList.get(i);
    }

    public Iterator<Bidder> iterator() {
        return new BidderListIterator();
    }

    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class BidderListIterator implements Iterator<Bidder> {

        private int index = 0;

        public boolean hasNext() {
            return (this.index < bidderList.size());
        }

        public Bidder next() {
            return bidderList.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");
        }
    }
}
