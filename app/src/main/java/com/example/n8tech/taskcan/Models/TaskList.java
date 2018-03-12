package com.example.n8tech.taskcan.Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by m_qui on 3/12/2018.
 */

public class TaskList implements Iterable<Task> {
    private ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }

    public Iterator<Task> iterator() {
        return new TaskListIterator();
    }

    //https://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
    class TaskListIterator implements Iterator<Task> {

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
