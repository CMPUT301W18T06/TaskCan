package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * TaskList contains an iterable ArrayList of Task objects and related methods.
 *
 * @author CMPUT301W18T06
 * @see Task
 */

public class TaskList implements Iterable<Task> {
    private ArrayList<Task> taskList;

    /** Creates a new instance of TaskList, creating a new ArrayList of Tasks. */
    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    /** @param task the task to be added */
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    /** @param task the task to be removed */
    public void removeTask(Task task) {this.taskList.remove(task);}

    /**
     * @param i integer representing list index
     * @return task at the corresponding index
     */
    public Task getTaskAtIndex(int i) {
        return this.taskList.get(i);
    }

    /**
     * @param task task in the task list
     * @return integer representing the index of the task in the list, -1 if task is not in the list
     */
    public int getIndexOfTask(Task task)
    {
        int index = -1;
        for(Task myTask : this.taskList) {
            if(myTask.getId().equals(task.getId())) {
                index = this.taskList.indexOf(myTask);
            }
        }
        return index;
    }

    /**
     * Replaces the task at the specified index with a new task.
     * @param index index of task to be replaced
     * @param task new task object to replace current task at the index
     */
    public void replaceAtIndex(int index, Task task){
        this.taskList.set(index, task);
    }

    /** @return size of task list */
    public int getSize() {
        return this.taskList.size();
    }

    /** @return task list as a string */
    public String toString() { return this.taskList.toString(); }

    /** Creates a TaskList iterator. */
    public Iterator<Task> iterator() {
        return new TasksIterator();
    }

    /** Clears the TaskList. */
    public void clear() { this.taskList.clear(); }

    /** Returns a copy of the current TaskList */
    public TaskList copy() {
        TaskList newTaskList = new TaskList();
        for (Task task : taskList){
            newTaskList.addTask(task);
        }
        return newTaskList;
    }

    /**
     * Iterator object over TaskList.
     * @throws UnsupportedOperationException If remove() method is called.
     */
    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class TasksIterator implements Iterator<Task> {

        private int index = 0;

        public boolean hasNext() {
            return (this.index < taskList.size());
        }

        public Task next() {
            return taskList.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");
        }
    }
}
