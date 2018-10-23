package com.szollosi.timelogger.root.objects;

import com.szollosi.timelogger.root.exceptions.DayAdditionException;
import com.szollosi.timelogger.root.exceptions.EmptyListException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class WorkMonthTest {

    private WorkMonth workMonth;
    private WorkDay workDay;
    private Task task;

    @Before
    public void setUp() {
        workMonth = new WorkMonth(2014, 10);
        workDay = new WorkDay(2014, 10 ,10);
        task = new Task("LT-5252", "12:00", "13:00", "Testing...");
    }

    @Test(expected = DayAdditionException.class)
    public void addWorkDay() {
        workMonth.addWorkDay(workDay, false);
        assertTrue(workMonth.areThereAnyDays());
        workMonth.addWorkDay(workDay, false);
        WorkDay weekEndDay = new WorkDay(2014, 10, 11);
        workMonth.addWorkDay(weekEndDay, false);
    }

    @Test
    public void addTaskByWorkDay() {
        workMonth.addWorkDay(workDay, false);
        workMonth.addTaskByWorkDay(0, task);
        assertTrue(workMonth.getDays().get(0).areThereAnyTasks());
    }

    @Test(expected = EmptyListException.class)
    public void removeTaskByWorkDay() {
        WorkDay workDay = new WorkDay(2014, 10, 10);
        workMonth.addWorkDay(workDay, false);
        workMonth.addTaskByWorkDay(0, task);
        assertTrue(workMonth.getDays().get(0).areThereAnyTasks());
        workMonth.removeTaskByWorkDay(0, 0);
        workMonth.getDays().get(0).areThereAnyTasks();
    }

    @Test
    public void setTaskByWorkDay() {
        workMonth.addWorkDay(workDay, false);
        workMonth.addTaskByWorkDay(0, task);
        Task taskToChange = new Task("5352", "13:00", "14:00", "Testing...");
        workMonth.setTaskByWorkDay(0, 0, taskToChange);
        assertTrue(workMonth.getDays().get(0).getTasks().size() == 1);
        assertFalse(workMonth.getDays().get(0).getTasks().contains(task));
        assertTrue(workMonth.getDays().get(0).getTasks().contains(taskToChange));
    }

    @Test
    public void getSumPerMonth() {
        workDay.addTask(task);
        WorkDay secondWorkDay = new WorkDay(2014, 10, 9);
        Task secondTask = new Task("5321", "13:30", "16:00", "Testing...");
        secondWorkDay.addTask(secondTask);
        workMonth.addWorkDay(workDay, false);
        workMonth.addWorkDay(secondWorkDay, false);
        assertTrue(workMonth.getSumPerMonth() == 210);
    }

    @Test(expected = DayAdditionException.class)
    public void isNewDate() {
        workMonth.addWorkDay(workDay, false);
        WorkDay secondWorkDay = new WorkDay(2014, 10, 9);
        assertTrue(workMonth.isNewDate(secondWorkDay));
        workMonth.isNewDate(workDay);
    }

    @Test(expected = EmptyListException.class)
    public void areThereAnyDays() {
        workMonth.addWorkDay(workDay, false);
        assertTrue(workMonth.areThereAnyDays());
        workMonth.getDays().remove(0);
        workMonth.areThereAnyDays();
    }
}