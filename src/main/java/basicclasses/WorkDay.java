package basicclasses;

import exceptions.EmptyListException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MARCI
 */
@Getter
@Setter
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

    /**
     * Adds a task to the list of tasks
     *
     * @param t - the task that should be added
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Removes a task from the list a tasks
     *
     * @param taskIndex - the index of the task that should be removed
     */
    protected void removeTask(int taskIndex) {
        tasks.remove(taskIndex);
    }

    /**
     * Replaces a task to a new one in the list of tasks
     *
     * @param taskIndex - the index of the task that should be replaced
     * @param newTask - the new task that should take the place of the old one
     */
    protected void setTask(int taskIndex, Task newTask) {
        tasks.set(taskIndex, newTask);
    }

    /**
     * Gets the difference between the required minutes and the completed working minutes in a day
     *
     * @return - long - the difference in minutes
     */
    public long getExtraMinPerDay() {
        return getSumPerDay() - requiredMinPerDay;
    }

    /**
     * Gets the sum of the working minutes in a day
     *
     * @return - long - sum of the working minutes
     */
    public long getSumPerDay() {
        long sum = 0;
        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                sum += task.getMinPerTask();
            }
        }
        return sum;
    }

    /**
     * Gets the latest end time of the list of the tasks of a day.
     *
     * @return - the latest end time
     */
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

    /**
     * Checks whether the list of tasks is empty.
     *
     * @return - true, if the list of tasks is not empty
     */
    public boolean areThereAnyTasks() {
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
}
