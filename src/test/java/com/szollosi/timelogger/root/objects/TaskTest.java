package com.szollosi.timelogger.root.objects;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaskTest {

    Task task;

    @Before
    public void setUp() {
        task = new Task("4646", "12:00", "13:00", "testing");
    }

    @Test
    public void getMinPerTaskTest() {
        long result = 60;
        assertTrue(task.getMinPerTask() == result);
        task.setEndTime(LocalTime.parse("14:00"));
        assertFalse(task.getMinPerTask() == result);
    }

    @Test
    public void isValidTaskIdTest() {
        assertTrue(task.isValidTaskId());
        task.setTaskId("LT-8521");
        assertTrue(task.isValidTaskId());
        task.setTaskId("TLR-4562");
        assertFalse(task.isValidTaskId());
        task.setTaskId("46464");
        assertFalse(task.isValidTaskId());
    }

    @Test
    public void isValidRedmineTaskIdTest() {
        assertTrue(task.isValidRedmineTaskId());
        task.setTaskId("LT-4646");
        assertFalse(task.isValidRedmineTaskId());
    }

    @Test
    public void isValidLTTaskIdTest() {
        assertFalse(task.isValidLTTaskId());
        task.setTaskId("LT-4646");
        assertTrue(task.isValidLTTaskId());
    }

    @Test
    public void isValidTimeTest() {
        assertTrue(task.isValidTime());
        task.setEndTime(LocalTime.parse("11:00"));
        assertFalse(task.isValidTime());
    }
}