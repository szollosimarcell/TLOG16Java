package com.szollosi.timelogger.root.objects;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.szollosi.timelogger.root.exceptions.EmptyListException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkDayTest {

    WorkDay workDay;
    Task task;
    Task secondTask;

    @Before
    public void setUp() {
        workDay = new WorkDay(2014, 10, 31);
        task = new Task("5432", "12:00", "13:00", "Testing...");
        secondTask = new Task("TL-5432", "16:00", "17:00", "Testing...");
    }

    @Test
    public void addTask() {
        workDay.addTask(task);
        assertTrue(workDay.areThereAnyTasks());
    }

    @Test(expected = EmptyListException.class)
    public void removeTask() {
        workDay.addTask(task);
        workDay.removeTask(0);
        workDay.areThereAnyTasks();
    }

    @Test
    public void setTask() {
        workDay.addTask(task);
        workDay.setTask(0, secondTask);
        assertTrue(workDay.getTasks().size() == 1);
        assertTrue(workDay.getTasks().contains(secondTask));
        assertFalse(workDay.getTasks().contains(task));
    }

    @Test
    public void getSumPerDay() {
        workDay.addTask(task);
        workDay.addTask(secondTask);
        assertTrue(workDay.getSumPerDay() == 120);
    }

    @Test
    public void latestTaskEndTime() {
        workDay.addTask(task);
        workDay.addTask(secondTask);
        assertEquals(workDay.latestTaskEndTime(), secondTask.getEndTime());
    }

    @Test(expected = EmptyListException.class)
    public void areThereAnyTasks() {
        workDay.areThereAnyTasks();
    }
}