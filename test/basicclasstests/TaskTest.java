package basicclasstests;

import org.junit.Test;
import basicclasses.Task;
import basicclasses.Util;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

/**
 *
 * @author MARCI
 */
public class TaskTest {

    private Task task;

    public TaskTest() {
    }

    @Before
    public void setUp() {
        task = new Task("0101", 11, 30, 13, 00, "Import data to excel tables");
    }

    @Test
    public void getMinPerTaskTest() {
        long resultTime = 90;
        long currentResult = task.getMinPerTask();
        assertEquals(resultTime, currentResult);

        resultTime = 60;
        assertFalse(resultTime == currentResult);
    }

    @Test
    public void isValidTeskIdTest() {
        assertTrue(task.isValidTaskId());
        task = new Task("05502", 11, 30, 13, 00, "Import data to excel tables");
        assertFalse(task.isValidTaskId());
        task = new Task("LT-A500", 11, 30, 13, 00, "Import data to excel tables");
        assertFalse(task.isValidTaskId());
    }
}
