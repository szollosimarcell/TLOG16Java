package basicclasses;

import exceptions.EmptyListException;
import exceptions.RedundantMonthAdditionException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author MARCI
 */
@Getter
public class TimeLogger {

    private final List<WorkMonth> months = new ArrayList<>();

    /**
     * Adds a month to the time logger
     *
     * @param workMonth - the month that should be added
     */
    public void addMonth(WorkMonth workMonth) {
        if (isNewMonth(workMonth)) {
            months.add(workMonth);
        }
    }

    /**
     * Adds a workday to a given month.
     *
     * @param monthIndex - the index of the month where the workday should be added to
     * @param workDay - the workday that should be added
     * @param isWeekendEnabled - whether adding workday in weekend is enabled or not
     */
    public void addWorkDayByMonth(int monthIndex, WorkDay workDay, boolean isWeekendEnabled) {
        months.get(monthIndex).addWorkDay(workDay, isWeekendEnabled);
    }

    /**
     * Adds a task to a given month.
     *
     * @param monthIndex - the index of the month where the task should be added to
     * @param dayIndex - the index of the day where the task should be added to
     * @param task - the task that should be added
     */
    public void addTaskByMonth(int monthIndex, int dayIndex, Task task) {
        months.get(monthIndex).addTaskByWorkDay(dayIndex, task);
    }

    /**
     * Removes a task from a month
     *
     * @param monthIndex - the index of the month the task should be removed from
     * @param dayIndex - the index of the day the task should be removed from
     * @param taskIndex - the index of the task that should be removed
     */
    public void removeTaskByMonth(int monthIndex, int dayIndex, int taskIndex) {
        months.get(monthIndex).removeTaskByWorkDay(dayIndex, taskIndex);
    }

    /**
     * Replaces a task with a new task in a given month.
     *
     * @param monthIndex - the index of the month where the task should be replaced
     * @param dayIndex - the index if the day where the task should be replaced
     * @param taskIndex - the index of the task that should be replaced
     * @param newTask - the new task that should take the place of the old task
     */
    public void setTaskByMonth(int monthIndex, int dayIndex, int taskIndex, Task newTask) {
        months.get(monthIndex).setTaskByWorkDay(dayIndex, taskIndex, newTask);
    }

    /**
     * Checks whether the given month already exists in the time logger
     *
     * @param workMonth - the month that should be checked
     * @return - true, if the given month is true, false, if not
     */
    public boolean isNewMonth(WorkMonth workMonth) {
        if (months.stream().noneMatch(month -> (month.getDate().compareTo(workMonth.getDate()) == 0))) {
            return true;
        } else {
            throw new RedundantMonthAdditionException();
        }
    }

    /**
     * Checks whether the list of months is empty.
     *
     * @return - true, if the list of months is empty
     */
    public boolean areThereAnyMonths() {
        if (!months.isEmpty()) {
            return true;
        } else {
            throw new EmptyListException("TimeLogger is empty!");
        }
    }
}
