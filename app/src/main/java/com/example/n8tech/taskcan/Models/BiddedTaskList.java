package com.example.n8tech.taskcan.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * BiddedTaskList represents a list of tasks in which the current user has placed a bid.
 *
 * @see BiddedTask
 * @author CMPUT301W18T06
 */

public class BiddedTaskList implements Iterable<BiddedTask> {
    private ArrayList<BiddedTask> biddedTaskList;

    /** Creates a new instance of BiddedTaskList, creating a new ArrayList of BiddedTasks. */
    public BiddedTaskList() {
        this.biddedTaskList = new ArrayList<BiddedTask>();
    }

    /** @param biddedTask the bidded task to be added */
    public void addBiddedTask(BiddedTask biddedTask) {
        this.biddedTaskList.add(biddedTask);
    }

    /** @param biddedTask the bidded task to be removed */
    public void removeBiddedTask(BiddedTask biddedTask) {
        for(BiddedTask myTask : this.biddedTaskList) {
            if(biddedTask.getTaskId().equals(myTask.getTaskId())) {
                this.biddedTaskList.remove(myTask);
                break;
            }
        }
    }

    /**
     * @param i integer representing list index to be set
     * @param t Bidded Task to be set in that index
     */
    public void setBiddedTaskList(int i, BiddedTask t) {
        this.biddedTaskList.set(i, t);
    }

    /**
     * @param i integer representing list index
     * @return task at the corresponding index
     */
    public BiddedTask getBiddedTaskAtIndex(int i) {
        return this.biddedTaskList.get(i);
    }

    /**
     * @param biddedTask bidded task in the bidded task list
     * @return integer representing the index of the bidded task in the list, -1 if bidded task is not in the list
     */
    public int getIndexOfBiddedTask(BiddedTask biddedTask)
    {
        for(int i = 0; i < this.biddedTaskList.size(); i++) {
            if(this.biddedTaskList.get(i).getTaskId().equals(biddedTask.getTaskId())) {
                Log.i("Testing", "match id");
                return i;
            }
        }
        return -1;
    }

    /**
     * @param task task in the bidded task list
     * @return integer representing the index of the task in the list, -1 if task is not in the list
     */
    public int getIndexOfBiddedTask(Task task) {
        for(int i = 0; i < this.biddedTaskList.size(); i++) {
            if(this.biddedTaskList.get(i).getTaskId().equals(task.getId())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Replaces the task at the specified index with a new task.
     * @param index index of task to be replaced
     * @param biddedTask new task object to replace current task at the index
     */
    public void replaceAtIndex(int index, BiddedTask biddedTask){
        this.biddedTaskList.set(index, biddedTask);
    }

    /** @return size of bidded task list */
    public int getSize() {
        return this.biddedTaskList.size();
    }

    /** @return bidded task list as a string */
    public String toString() { return this.biddedTaskList.toString(); }

    /** Creates a BiddedTaskList iterator. */
    public Iterator<BiddedTask> iterator() {
        return new TasksIterator();
    }

    /** Clears the BiddedTaskList. */
    public void clear() { this.biddedTaskList.clear(); }

    /** Returns a copy of the current BiddedTaskList */
    public BiddedTaskList copy() {
        BiddedTaskList newBiddedTaskList = new BiddedTaskList();
        for (BiddedTask biddedTask : biddedTaskList){
            newBiddedTaskList.addBiddedTask(biddedTask);
        }
        return newBiddedTaskList;
    }

    /**
     * Iterator object over BiddedTaskList.
     * @throws UnsupportedOperationException If remove() method is called.
     */
    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class TasksIterator implements Iterator<BiddedTask> {

        private int index = 0;

        public boolean hasNext() {
            return (this.index < biddedTaskList.size());
        }

        public BiddedTask next() {
            return biddedTaskList.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");
        }
    }
}
