package basicclasses;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MARCI
 */
public class WorkMonth {

    private List<WorkDay> days = new ArrayList<>();
    private YearMonth date;
    private long sumPerMonth;
    private long requiredMinPerMonth;

    public WorkMonth(int year, int month) {
        this.date = YearMonth.of(year, month);
    }

    public long getExtraMinPerMonth() {
        return sumPerMonth - requiredMinPerMonth;
    }

    public boolean isNewDate(WorkDay workDay) {
        return !days.contains(workDay);
    }

    public boolean isSameMonth(WorkDay workDay) {
        return this.getDate().getMonth() == workDay.getActualDay().getMonth();
    }

    public void addWorkDay(WorkDay wd, boolean isWeekendEnabled) {
        if (isWeekendEnabled && isNewDate(wd) && isSameMonth(wd)
            || !isWeekendEnabled && Util.isWeekday(wd) && isNewDate(wd) && isSameMonth(wd)) {
            days.add(wd);
        }
    }

    public void addWorkDay(WorkDay wd) {
        if (Util.isWeekday(wd) && isNewDate(wd) && isSameMonth(wd)) {
            days.add(wd);
        }
    }

    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() {
        return sumPerMonth;
    }

    public long getRequiredMinPerMonth() {
        return requiredMinPerMonth;
    }

}
