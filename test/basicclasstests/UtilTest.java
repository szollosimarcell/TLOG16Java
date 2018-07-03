/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicclasstests;

import basicclasses.Task;
import basicclasses.Util;
import basicclasses.WorkDay;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MARCI
 */
public class UtilTest {

    Task task;
    WorkDay workDay;

    public UtilTest() {
    }

    @Before
    public void setUp() {
        workDay = new WorkDay();
        task = new Task("TK-0101", "10:15", "10:45", "Import data to excel tables");
    }

    @Test
    public void isMultipleQuarterHourTest() {
        task = new Task("0101", 11, 30, 13, 00, "Import data to excel tables");
        assertTrue(Util.isMultipleQuarterHour(task.getMinPerTask()));
        task = new Task("0101", 11, 33, 13, 00, "Import data to excel tables");
        assertFalse(Util.isMultipleQuarterHour(task.getMinPerTask()));
    }

    @Test
    public void isWeekDayTest() {
        WorkDay weekDay = new WorkDay(2018, 07, 03);
        WorkDay notWeekDay = new WorkDay(2018, 07, 01);

        assertTrue(Util.isWeekday(weekDay));
        assertFalse(Util.isWeekday(notWeekDay));
    }

    @Test
    public void isSeperatedTimeTest() {
        workDay.addTask(task);

        Task overlappingTask = new Task("LT-2020", "10:30", "11:00", "Import data to excel tables");
        Task notOverlappingTask = new Task("LT-3030", "09:45", "10:00", "Import data to excel tables");

        assertFalse(Util.isSeperatedTime(overlappingTask, workDay.getTasks()));
        assertTrue(Util.isSeperatedTime(notOverlappingTask, workDay.getTasks()));
    }
}
