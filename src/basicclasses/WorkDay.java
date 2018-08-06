package basicclasses;

import exceptions.EmptyListException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MARCI
 */
public class WorkDay {

    private final List<Task> tasks = new ArrayList<>();
    private LocalDate actualDay = LocalDate.now();
    private long requiredMinPerDay = 450;

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

    public void addTask(Task t) {
        if (Util.isMultipleQuarterHour(t.getMinPerTask()) && Util.isSeperatedTime(t, tasks)) {
            tasks.add(t);
        }
    }

    protected void removeTask(int taskIndex) {
        tasks.remove(taskIndex);
    }

    protected void setTask(int taskIndex, Task newTask) {
        tasks.set(taskIndex, newTask);
    }

    public long getExtraMinPerDay() {
        return getSumPerDay() - requiredMinPerDay;
    }

    public long getSumPerDay() {
        long sum = 0;
        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                sum += task.getMinPerTask();
            }
        }
        return sum;
    }

    public LocalTime latestTaskEndTime() {
        LocalTime latestEndTime = LocalTime.of(0, 0);
        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                if (task.getEndTime().compareTo(latestEndTime) > 0) {
                    latestEndTime = task.getEndTime();
                }
            }
        }
        return latestEndTime;
    }

    public boolean AreThereAnyTasks() {
        if (!tasks.isEmpty()) {
            return true;
        } else {
            throw new EmptyListException("This day is empty!");
        }
    }

    @Override
    public String toString() {
        return actualDay.toString();
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

    public List<Task> getTasks() {
        return tasks;
    }

}
