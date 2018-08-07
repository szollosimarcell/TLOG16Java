package basicclasses;

import exceptions.EmptyListException;
import exceptions.RedundantMonthAdditionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MARCI
 */
public class TimeLogger {

    private final List<WorkMonth> months = new ArrayList<>();

    public void addMonth(WorkMonth workMonth) {
        if (isNewMonth(workMonth)) {
            months.add(workMonth);
        }
    }

    public void addWorkDayByMonth(int monthIndex, WorkDay workDay, boolean isWeekendEnabled) {
        months.get(monthIndex).addWorkDay(workDay, isWeekendEnabled);
    }

    public void addTaskByMonth(int monthIndex, int dayIndex, Task task) {
        months.get(monthIndex).addTaskByWorkDay(dayIndex, task);
    }

    public void removeTaskByMonth(int monthIndex, int dayIndex, int taskIndex) {
        months.get(monthIndex).removeTaskByWorkDay(dayIndex, taskIndex);
    }

    public void setTaskByMonth(int monthIndex, int dayIndex, int taskIndex, Task newTask) {
        months.get(monthIndex).setTaskByWorkDay(dayIndex, taskIndex, newTask);
    }

    public boolean isNewMonth(WorkMonth workMonth) {
        if (months.stream().noneMatch(month -> (month.getDate().compareTo(workMonth.getDate()) == 0))) {
            return true;
        } else {
            throw new RedundantMonthAdditionException();
        }
    }

    public boolean areThereAnyMonths() {
        if (!months.isEmpty()) {
            return true;
        } else {
            throw new EmptyListException("TimeLogger is empty!");
        }
    }

    public List<WorkMonth> getMonths() {
        return months;
    }

}
