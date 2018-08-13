package com.szollosi.timelogger.root.objects;

import com.szollosi.timelogger.root.exceptions.DayAdditionException;
import com.szollosi.timelogger.root.exceptions.EmptyListException;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MARCI
 */

@Getter
@Setter
public class WorkMonth {

    private final List<WorkDay> days = new ArrayList<>();
    private final YearMonth date;
    private final long requiredMinPerMonth = 9000;

    public WorkMonth(int year, int month) {
        this.date = YearMonth.of(year, month);
    }

    /**
     * Adds a workday to the list of workdays.
     *
     * @param workDay - the workday that should be added
     * @param isWeekendEnabled - decides whether adding workday to weekend is enabled or not
     */
    public void addWorkDay(WorkDay workDay, boolean isWeekendEnabled) {
        if (isWeekendEnabled && isNewDate(workDay) && isSameMonth(workDay) || !isWeekendEnabled && Util.isWeekday(workDay) && isNewDate(workDay) && isSameMonth(workDay)) {
            days.add(workDay);
        } else {
            if (!Util.isWeekday(workDay)) {
                throw new DayAdditionException("You can't add workday to weekend!");
            }
        }
    }

    /**
     * Adds a task to a given workday.
     *
     * @param dayIndex - the index of the workday where the task should be added to
     * @param task - the task that should be added
     */
    protected void addTaskByWorkDay(int dayIndex, Task task) {
        days.get(dayIndex).addTask(task);
    }

    /**
     * Removes a task from a given workday.
     *
     * @param dayIndex - the index of the workday the task should be removed from
     * @param taskIndex - the index of the task that should be removed
     */
    protected void removeTaskByWorkDay(int dayIndex, int taskIndex) {
        days.get(dayIndex).removeTask(taskIndex);
    }

    /**
     * Replaces a task with a new one in a given workday.
     *
     * @param dayIndex - the index of the day where the task should be replaced
     * @param taskIndex - the index of the task that should be replaces
     * @param newTask - the new task that should take the places of the old one
     */
    protected void setTaskByWorkDay(int dayIndex, int taskIndex, Task newTask) {
        days.get(dayIndex).setTask(taskIndex, newTask);
    }

    /**
     * Gets the difference between the required working mins and the completed working mins in a month.
     *
     * @return - long - the difference in minutes
     */
    public long getExtraMinPerMonth() {
        return getSumPerMonth() - requiredMinPerMonth;
    }

    /**
     * Gets the sum of the working minutes in a month.
     *
     * @return - long - sum of the working minutes
     */
    public long getSumPerMonth() {
        long sum = 0;
        if (!days.isEmpty()) {
            sum = days.stream().map(day -> day.getSumPerDay()).reduce(sum, (accumulator, _item) -> accumulator + _item);
        }
        return sum;
    }

    /**
     * Checks whether the given workday already exists in the month.
     *
     * @param workDay - the workday that should be checked
     * @return - true, if the workday is new, false, if not
     */
    public boolean isNewDate(WorkDay workDay) {
        if (days.stream().noneMatch(day -> (day.getActualDay().equals(workDay.getActualDay())))) {
            return true;
        } else {
            throw new DayAdditionException("This day already exists in this month!");
        }
    }

    /**
     * Checks whether the given workday is in the correct month.
     *
     * @param workDay - the workday that should be checked
     * @return - true, if the workday is correct, false, if not
     */
    public boolean isSameMonth(WorkDay workDay) {
        if (this.getDate().getMonth() == workDay.getActualDay().getMonth()) {
            return true;
        } else {
            throw new DayAdditionException("Incorrect month!");
        }
    }

    /**
     * Checks whether the list of workdays is empty.
     *
     * @return - true if the list of workdays is not empty
     */
    public boolean areThereAnyDays() {
        if (!days.isEmpty()) {
            return true;
        } else {
            throw new EmptyListException("This month is empty");
        }
    }
}
