package basicclasses;

import java.util.List;

/**
 *
 * @author MARCI
 */
public class TimeLogger {
    
    private List<WorkMonth> months;
    
    public boolean isNewMonth(WorkMonth workMonth) {
        return !months.contains(workMonth);
    }
    
    public void addMonth(WorkMonth workMonth) {
        if (isNewMonth(workMonth)) {
            months.add(workMonth);
        }
    }

    public List<WorkMonth> getMonths() {
        return months;
    }
}
