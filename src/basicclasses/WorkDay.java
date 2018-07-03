package basicclasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MARCI
 */
public class WorkDay {

    private List<Task> tasks = new ArrayList<>();
    private LocalDate actualDay = LocalDate.now();
    private long requiredMinPerDay = 450;
    private long sumPerDay;

    public WorkDay(long requiredMinPerDay, int year, int month, int day) {
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = LocalDate.of(year, month, day);
    }

    public WorkDay(int year, int month, int day) {
        this.actualDay = LocalDate.of(year, month, day);
    }

    public WorkDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }

    public WorkDay() {
    }

    public long getExtraMinPerDay() {
        return sumPerDay - requiredMinPerDay;
    }

    public void addTask(Task t) {
        if (Util.isMultipleQuarterHour(t.getMinPerTask()) && Util.isSeperatedTime(t, tasks)) {
            tasks.add(t);
        }
    }

    public void setActualDay(int year, int month, int day) {
        this.actualDay = LocalDate.of(year, month, day);
    }

    public void setRequiredMinPerDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }
    
    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public long getSumPerDay() {
        return sumPerDay;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
