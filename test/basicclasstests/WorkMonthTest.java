package basicclasstests;

import basicclasses.WorkDay;
import basicclasses.WorkMonth;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MARCI
 */
public class WorkMonthTest {
    
    WorkMonth workMonth;
    WorkDay weekDay;
    WorkDay notWeekDay;
    WorkDay badMonthsDay;
    
    public WorkMonthTest() {
    }
    
    @Before
    public void setUp() {
        workMonth = new WorkMonth(2018, 07);
        weekDay = new WorkDay(2018, 07, 03);
        notWeekDay = new WorkDay(2018, 07, 01);
        badMonthsDay = new WorkDay(2018, 06, 04);
    }
    
    @Test
    public void isNewDateTest() {
        assertTrue(workMonth.isNewDate(weekDay));
        WorkDay weekDayAlike = new WorkDay(500, 2018, 07, 03);
        workMonth.addWorkDay(weekDayAlike);
        assertFalse(workMonth.isNewDate(weekDayAlike));
    }
    
    @Test
    public void isSameMonthTest() {
        assertTrue(workMonth.isSameMonth(weekDay));
        assertFalse(workMonth.isSameMonth(badMonthsDay));
    }
    
    @Test
    public void addWorkDayWithBooleanTest() {
        workMonth.addWorkDay(notWeekDay, false);
        assertFalse(workMonth.getDays().contains(notWeekDay));
        
        workMonth.addWorkDay(notWeekDay, true);
        assertTrue(workMonth.getDays().contains(notWeekDay));
        
        workMonth.addWorkDay(weekDay, false);
        assertTrue(workMonth.getDays().contains(weekDay));
        
        workMonth.addWorkDay(badMonthsDay, true);
        assertFalse(workMonth.getDays().contains(badMonthsDay));
    }
    
    @Test
    public void addWorkDayTest() {
        workMonth.addWorkDay(weekDay);
        assertTrue(workMonth.getDays().contains(weekDay));
        
        workMonth.addWorkDay(notWeekDay);
        assertFalse(workMonth.getDays().contains(notWeekDay));
    }
}
