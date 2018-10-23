package com.szollosi.timelogger.root.objects;

import com.szollosi.timelogger.root.exceptions.InvalidInputException;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

/**
 * This is the Util classes test class in which scanner related methods are being tested.
 * For this reason you have to run the test methods one by one. If you run this whole class the test will fail
 * because of the scanner related test methods.
 */
public class UtilTest {

    @Before
    public void setUp() {
    }

    @Test
    public void roundStartTime() {
        Task task = new Task("5432", "12:10", "13:00", "Testing...");
        Util.roundStartTime(task);
        assertTrue(task.getStartTime().equals(LocalTime.parse("12:15")));
        task.setStartTime(LocalTime.parse("11:55"));
        Util.roundStartTime(task);
        assertTrue(task.getStartTime().equals(LocalTime.parse("12:00")));
    }

    @Test
    public void roundEndTime() {
        Task task = new Task("5432", "12:00", "13:05", "Testing...");
        Util.roundEndTime(task);
        assertTrue(task.getEndTime().equals(LocalTime.parse("13:00")));
        task.setEndTime(LocalTime.parse("12:55"));
        Util.roundEndTime(task);
        assertTrue(task.getEndTime().equals(LocalTime.parse("13:00")));
    }

    @Test
    public void isMultipleQuarterHour() {
        int number = 20;
        assertFalse(Util.isMultipleQuarterHour(number));
        number = 15;
        assertTrue(Util.isMultipleQuarterHour(number));
    }

    @Test
    public void isWeekday() {
        WorkDay workDay = new WorkDay(2014, 10, 10);
        assertTrue(Util.isWeekday(workDay));
        workDay.setActualDay(LocalDate.parse("2014-10-11"));
        assertFalse(Util.isWeekday(workDay));
    }

    @Test
    public void isSeparatedTime() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task("5432", "12:00", "13:00", "Testing...");
        Task secondTask = new Task("TL-5432", "11:00", "12:00", "Testing...");
        tasks.add(task);
        assertTrue(Util.isSeparatedTime(secondTask, tasks));
        secondTask.setEndTime(LocalTime.parse("12:30"));
        assertFalse(Util.isSeparatedTime(secondTask, tasks));
        secondTask.setStartTime(LocalTime.parse("13:00"));
        secondTask.setEndTime(LocalTime.parse("14:00"));
        assertTrue(Util.isSeparatedTime(secondTask, tasks));
        secondTask.setStartTime(LocalTime.parse("12:30"));
        assertFalse(Util.isSeparatedTime(secondTask, tasks));
        task.setStartTime(LocalTime.parse("11:00"));
        assertFalse(Util.isSeparatedTime(secondTask, tasks));
    }

    @Test
    public void isSeparatedTimeOverloadedWithIndex() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task("5432", "12:00", "13:00", "Testing...");
        Task secondTask = new Task("TL-5432", "11:00", "12:00", "Testing...");
        tasks.add(task);
        tasks.add(secondTask);
        assertTrue(Util.isSeparatedTime(task, tasks, 0));
    }

    @Test
    public void checkInterval() {
        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);
        assertTrue(Util.checkInterval(1,10,true) == 4);
    }

    @Test(expected = InvalidInputException.class)
    public void checkIntervalWrongInterval() {
        ByteArrayInputStream in = new ByteArrayInputStream("10".getBytes());
        System.setIn(in);
        Util.checkInterval(0,9,false);
    }

    @Test
    public void inputIfNumeric() {
        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);
        assertTrue(Util.inputIfNumeric() == 5);
    }

    @Test(expected = InvalidInputException.class)
    public void inputIfNumericWrongAnswer() {
        ByteArrayInputStream in = new ByteArrayInputStream("asd".getBytes());
        System.setIn(in);
        Util.inputIfNumeric();
    }

    @Test
    public void inputTime() {
        ByteArrayInputStream in = new ByteArrayInputStream("12:00".getBytes());
        System.setIn(in);
        assertTrue(Util.inputTime().equals(LocalTime.parse("12:00")));
    }

    @Test(expected = InvalidInputException.class)
    public void inputTimeWrongAnswer() {
        ByteArrayInputStream in = new ByteArrayInputStream("asd".getBytes());
        System.setIn(in);
        Util.inputTime();
    }

    @Test
    public void isYesAnswerYes() {
        ByteArrayInputStream in = new ByteArrayInputStream("y".getBytes());
        System.setIn(in);
        assertTrue(Util.isYes());
    }

    @Test
    public void isYesAnswerNo() {
        ByteArrayInputStream in = new ByteArrayInputStream("n".getBytes());
        System.setIn(in);
        assertFalse(Util.isYes());
    }

    @Test(expected = InvalidInputException.class)
    public void isYesWrongAnswer() {
        ByteArrayInputStream in = new ByteArrayInputStream("asd".getBytes());
        System.setIn(in);
        Util.isYes();
    }
}