package com.szollosi.timelogger.root.objects;

import com.szollosi.timelogger.root.exceptions.EmptyListException;
import com.szollosi.timelogger.root.exceptions.RedundantMonthAdditionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeLoggerTest {

    TimeLogger timeLogger;
    WorkMonth workMonth;
    WorkDay workDay;
    Task task;

    @Before
    public void setUp() {
        timeLogger = new TimeLogger();
        workMonth = new WorkMonth(2018, 10);
        workDay = new WorkDay(2018, 10, 4);
        task = new Task("TL-2014", "12:00", "13:00", "Testing");
    }

    @Test
    public void addMonth() {
        timeLogger.addMonth(workMonth);
        assertTrue(timeLogger.getMonths().contains(workMonth));
        assertEquals(timeLogger.getMonths().size(), 1);
    }

    @Test(expected = RedundantMonthAdditionException.class)
    public void addMonthExceptionTest() {
        timeLogger.addMonth(workMonth);
        timeLogger.addMonth(workMonth);
    }

    @Test
    public void addWorkDayByMonth() {
        timeLogger.addMonth(workMonth);
        timeLogger.addWorkDayByMonth(0, workDay,false);
        assertTrue(timeLogger.getMonths().get(0).getDays().contains(workDay));
        assertEquals(timeLogger.getMonths().get(0).getDays().size(), 1);
    }

    @Test
    public void addTaskByMonth() {
        timeLogger.addMonth(workMonth);
        timeLogger.addWorkDayByMonth(0, workDay, false);
        timeLogger.addTaskByMonth(0, 0, task);
        assertTrue(timeLogger.getMonths().get(0).getDays().get(0).getTasks().contains(task));
        assertEquals(timeLogger.getMonths().get(0).getDays().get(0).getTasks().size(), 1);
    }

    @Test
    public void removeTaskByMonth() {
        timeLogger.addMonth(workMonth);
        timeLogger.addWorkDayByMonth(0, workDay, false);
        timeLogger.addTaskByMonth(0, 0, task);
        timeLogger.removeTaskByMonth(0, 0, 0);
        assertFalse(timeLogger.getMonths().get(0).getDays().get(0).getTasks().contains(task));
        assertEquals(timeLogger.getMonths().get(0).getDays().get(0).getTasks().size(), 0);
    }

    @Test
    public void setTaskByMonth() {
        timeLogger.addMonth(workMonth);
        timeLogger.addWorkDayByMonth(0, workDay, false);
        timeLogger.addTaskByMonth(0, 0, task);
        Task newTask = new Task("4646", "13:00", "14:00", "Testing");
        timeLogger.setTaskByMonth(0, 0, 0, newTask);
        assertTrue(timeLogger.getMonths().get(0).getDays().get(0).getTasks().contains(newTask));
        assertFalse(timeLogger.getMonths().get(0).getDays().get(0).getTasks().contains(task));
        assertEquals(timeLogger.getMonths().get(0).getDays().get(0).getTasks().size(), 1);
    }

    @Test(expected = RedundantMonthAdditionException.class)
    public void isNewMonth() {
        assertTrue(timeLogger.isNewMonth(workMonth));
        timeLogger.addMonth(workMonth);
        assertFalse(timeLogger.isNewMonth(workMonth));
    }

    @Test(expected = EmptyListException.class)
    public void areThereAnyMonths() {
        assertFalse(timeLogger.areThereAnyMonths());
        timeLogger.addMonth(workMonth);
        assertTrue(timeLogger.areThereAnyMonths());
    }
}