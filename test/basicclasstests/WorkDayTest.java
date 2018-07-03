/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicclasstests;

import basicclasses.Task;
import basicclasses.WorkDay;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author MARCI
 */
public class WorkDayTest {
    
    WorkDay workDay;
    Task task;
    
    public WorkDayTest() {
    }
    
    @Before
    public void setUp() {
        workDay = new WorkDay();
        task = new Task("TK-0101", "10:15", "10:45", "Import data to excel tables");
    }
    
    @Test
    public void addTaskTest() {
        workDay.addTask(task);
        assertTrue(workDay.getTasks().contains(task));
    }
    
}
