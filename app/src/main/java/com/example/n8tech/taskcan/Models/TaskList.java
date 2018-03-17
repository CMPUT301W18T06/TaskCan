package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by m_qui on 3/12/2018.
 */

public class TaskList implements Iterable<Task> {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public void removeTask(Task task) {this.taskList.remove(task);}

    public Task getTaskAtIndex(int i) {
        return this.taskList.get(i);
    }

    public int getIndexOfTask(Task task){
        return this.taskList.indexOf(task);
    }

    public void replaceAtIndex(int index, Task task){
        this.taskList.set(index, task);
    }

    public int getSize() {
        return this.taskList.size();
    }

    public Iterator<Task> iterator() {
        return new TasksIterator();
    }

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
