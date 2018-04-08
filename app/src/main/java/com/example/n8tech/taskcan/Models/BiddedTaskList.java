package com.example.n8tech.taskcan.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by m_qui on 4/8/2018.
 */

public class BiddedTaskList implements Iterable<BiddedTask> {
    private ArrayList<BiddedTask> biddedTaskList;

    /** Creates a new instance of TaskList, creating a new ArrayList of Tasks. */
    public BiddedTaskList() {
        this.biddedTaskList = new ArrayList<BiddedTask>();
    }

    /** @param biddedTask the task to be added */
    public void addBiddedTask(BiddedTask biddedTask) {
        this.biddedTaskList.add(biddedTask);
    }

    /** @param biddedTask the task to be removed */
    public void removeBiddedTask(BiddedTask biddedTask) {
        for(BiddedTask myTask : this.biddedTaskList) {
            if(biddedTask.getTaskId().equals(myTask.getTaskId())) {
                this.biddedTaskList.remove(myTask);
            }
        }
    }

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
     * @param biddedTask task in the task list
     * @return integer representing the index of the task in the list, -1 if task is not in the list
     */
    public int getIndexOfBiddedTask(BiddedTask biddedTask)
    {
        for(BiddedTask myTask : this.biddedTaskList) {
            if(myTask.getTaskId().equals(biddedTask.getTaskId())) {
                Log.i("Testing", "match id");
                return this.biddedTaskList.indexOf(myTask);
            }
        }
        return -1;
    }

    public int getIndexOfBiddedTask(Task task) {

        for(BiddedTask myTask : this.biddedTaskList) {
            if(myTask.getTaskId().equals(task.getId())) {
                return this.biddedTaskList.indexOf(myTask);
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

    /** @return size of task list */
    public int getSize() {
        return this.biddedTaskList.size();
    }

    /** @return task list as a string */
    public String toString() { return this.biddedTaskList.toString(); }

    /** Creates a TaskList iterator. */
    public Iterator<BiddedTask> iterator() {
        return new TasksIterator();
    }

    /** Clears the TaskList. */
    public void clear() { this.biddedTaskList.clear(); }

    /** Returns a copy of the current TaskList */
    public BiddedTaskList copy() {
        BiddedTaskList newBiddedTaskList = new BiddedTaskList();
        for (BiddedTask biddedTask : biddedTaskList){
            newBiddedTaskList.addBiddedTask(biddedTask);
        }
        return newBiddedTaskList;
    }

    /**
     * Iterator object over TaskList.
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
