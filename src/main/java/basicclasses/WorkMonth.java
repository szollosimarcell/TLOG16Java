package basicclasses;

import exceptions.DayAdditionException;
import exceptions.EmptyListException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MARCI
 */
public class WorkMonth {

    private final List<WorkDay> days = new ArrayList<>();
    private final YearMonth date;
    private final long requiredMinPerMonth = 9000;

    public WorkMonth(int year, int month) {
        this.date = YearMonth.of(year, month);
    }

    public void addWorkDay(WorkDay workDay, boolean isWeekendEnabled) {
        if (isWeekendEnabled && isNewDate(workDay) && isSameMonth(workDay) || !isWeekendEnabled && Util.isWeekday(workDay) && isNewDate(workDay) && isSameMonth(workDay)) {
            days.add(workDay);
        } else {
            if (!Util.isWeekday(workDay)) {
                throw new DayAdditionException("You can't add workday to weekend!");
            }
        }
    }

    protected void addTaskByWorkDay(int dayIndex, Task task) {
        days.get(dayIndex).addTask(task);
    }

    protected void removeTaskByWorkDay(int dayIndex, int taskIndex) {
        days.get(dayIndex).removeTask(taskIndex);
    }

    protected void setTaskByWorkDay(int dayIndex, int taskIndex, Task newTask) {
        days.get(dayIndex).setTask(taskIndex, newTask);
    }

    public long getExtraMinPerMonth() {
        return getSumPerMonth() - requiredMinPerMonth;
    }

    public long getSumPerMonth() {
        long sum = 0;
        if (!days.isEmpty()) {
            sum = days.stream().map(day -> day.getSumPerDay()).reduce(sum, (accumulator, _item) -> accumulator + _item);
        }
        return sum;
    }

    public boolean isNewDate(WorkDay workDay) {
        if (days.stream().noneMatch(day -> (day.getActualDay().equals(workDay.getActualDay())))) {
            return true;
        } else {
            throw new DayAdditionException("This day already exists in this month!");
        }
    }

    public boolean isSameMonth(WorkDay workDay) {
        if (this.getDate().getMonth() == workDay.getActualDay().getMonth()) {
            return true;
        } else {
            throw new DayAdditionException("Incorrect month!");
        }
    }

    public boolean areThereAnyDays() {
        if (!days.isEmpty()) {
            return true;
        } else {
            throw new EmptyListException("This month is empty");
        }
    }

    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }

    public long getRequiredMinPerMonth() {
        return requiredMinPerMonth;
    }
}
