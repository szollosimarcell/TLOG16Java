package control;

import basicclasses.Task;
import basicclasses.TimeLogger;
import basicclasses.Util;
import basicclasses.WorkDay;
import basicclasses.WorkMonth;
import exceptions.NotExpectedTimeOrderException;
import exceptions.NotSeperatedTimesException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MARCI
 */
public class TimeLoggerUI {

    private final List<Integer> unfinishedTaskIDs = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final TimeLogger workLog;

    private WorkMonth workMonth;
    private WorkDay workDay;

    TimeLoggerUI(TimeLogger workLog) {
        this.workLog = workLog;
    }

    public void showMenu() {
        System.out.println("\nTimeLogger menu:");
        System.out.println("------------------------------------------\n");
        System.out.println("Choose one command of the followings and push its number!\n");
        System.out.println("0: Exit from TimeLogger");
        System.out.println("1: List months");
        System.out.println("2: List days");
        System.out.println("3: List tasks");
        System.out.println("4: Add a new month");
        System.out.println("5: Add a day to a specific month");
        System.out.println("6: Start a task for a day");
        System.out.println("7: Finish a specific task");
        System.out.println("8: Delete a task");
        System.out.println("9: Modify task");
        System.out.println("10: Show statistics\n");
        System.out.print("Your number: ");
    }

    public boolean menu(int n) {
        try {
            switch (n) {
                case 0:
                    return !exit();
                case 1:
                    listMonths();
                    break;
                case 2:
                    listDays();
                    break;
                case 3:
                    listTasks(false);
                    break;
                case 4:
                    addNewMonth();
                    break;
                case 5:
                    addNewDay();
                    break;
                case 6:
                    startTask();
                    break;
                case 7:
                    endTask();
                    break;
                case 8:
                    deleteTask();
                    break;
                case 9:
                    modifyTask();
                    break;
                case 10:
                    showStatistics();
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    private boolean exit() {
        System.out.print("\nAre you sure exitting from TimeLogger? (y/n) ");
        return Util.isYes();
    }

    private void listMonths() {
        if (workLog.areThereAnyMonths()) {
            int i = 1;
            for (WorkMonth month : workLog.getMonths()) {
                System.out.println(i + ". " + month.getDate());
                i++;
            }
        }
    }

    private void listDays() {
        listMonths();
        System.out.print("\nPlease type the number of the month! ");
        workMonth = workLog.getMonths().get(Util.checkRange(1, workLog.getMonths().size(), true));
        if (workMonth.areThereAnyDays()) {
            int i = 1;
            for (WorkDay day : workMonth.getDays()) {
                System.out.println(i + ". " + day.toString());
                i++;
            }
        }
    }

    private void listTasks(boolean onlyFinished) {
        listDays();
        System.out.print("\nPlease type the number of the day! ");
        workDay = workMonth.getDays().get(Util.checkRange(1, workMonth.getDays().size(), true));
        if (workDay.AreThereAnyTasks()) {
            if (onlyFinished) {
                ListOnlyUnfinishedTasks();
            } else {
                listAllTask();
            }
        }
    }

    private void listAllTask() {
        int i = 1;
        for (Task task : workDay.getTasks()) {
            System.out.println(i + ". " + task.toString());
            i++;
        }
    }

    private void ListOnlyUnfinishedTasks() {
        unfinishedTaskIDs.clear();
        int number = 1;

        System.out.println("You can only end unfinished tasks, these are the following:");
        for (int i = 0; i < workDay.getTasks().size(); i++) {
            Task task = workDay.getTasks().get(i);
            if (task.getMinPerTask() == 0) {
                System.out.println(number + ". " + task.toString());
                unfinishedTaskIDs.add(i);
                number++;
            }
        }
    }

    private void addNewMonth() {
        WorkMonth month = createMonth();
        workLog.addMonth(month);
    }

    private WorkMonth createMonth() {
        System.out.print("\nPlease type a year! ");
        int year = Util.checkRange(2000, LocalDate.now().getYear(), false);
        System.out.print("\nPlease type a month! ");
        int month;
        if (year == LocalDate.now().getYear()) {
            month = Util.checkRange(1, LocalDate.now().getMonthValue(), false);
        } else {
            month = Util.checkRange(1, 12, false);
        }
        return new WorkMonth(year, month);
    }

    private void addNewDay() {
        listMonths();
        System.out.print("\nPlease enter the number of the month: ");
        int monthIndex = Util.checkRange(1, workLog.getMonths().size(), true);
        workMonth = workLog.getMonths().get(monthIndex);
        WorkDay workDay = createDay();
        workLog.addWorkDayByMonth(monthIndex, workDay, false);
    }

    private WorkDay createDay() {
        YearMonth monthDate = workMonth.getDate();
        Calendar cal = new GregorianCalendar(monthDate.getYear(), monthDate.getMonth().getValue() - 1, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayNumber = askForDayNumber(maxDay);
        int workingMins = askForWorkingMins();

        if (workingMins == 0) {
            return new WorkDay(monthDate.getYear(), monthDate.getMonth().getValue(), dayNumber);
        } else {
            return new WorkDay(workingMins, monthDate.getYear(), monthDate.getMonthValue(), dayNumber);
        }
    }

    private int askForDayNumber(int maxDay) {
        System.out.print("\nPlease enter the number of the day you want to add in! (the current month has " + maxDay + " days): ");
        if (workMonth.getDate().getMonthValue() == LocalDate.now().getMonthValue()) {
            return Util.checkRange(1, LocalDate.now().getDayOfMonth(), false);
        } else {
            return Util.checkRange(1, maxDay, false);
        }
    }

    private int askForWorkingMins() {
        System.out.print("\nThe default required working time is 7,5 hours, do you want to customize it? (y/n) ");
        if (Util.isYes()) {
            System.out.print("Please type the required working hours in minutes! ");
            return Util.checkRange(300, 600, false);
        } else {
            return 0;
        }
    }

    private void startTask() {
        listDays();
        int monthIndex = workLog.getMonths().indexOf(workMonth);
        System.out.print("\nPlease enter the number of the day you want to add into: ");
        int dayIndex = Util.checkRange(1, workMonth.getDays().size(), true);
        workDay = workMonth.getDays().get(dayIndex);
        Task task = createTask();
        workLog.addTaskByMonth(monthIndex, dayIndex, task);
    }

    private Task createTask() {
        System.out.println("\nPlease enter the ID of the task, valid ID can be 4 digits or LT-{4 digits}, for example: LT-4863");
        System.out.print("\nYour taskID: ");
        Task task = new Task(scanner.nextLine());

        System.out.print("\nPlease type the comment of the task: ");
        task.setComment(scanner.nextLine());

        System.out.println("\nThe endtime of the last task is " + workDay.latestTaskEndTime());
        System.out.print("\nPlease enter the starting time (in format HH:MM): ");
        askForStartTime(task);
        return task;
    }

    private void askForStartTime(Task task) {
        String startTime = Util.checkTimeFormat();
        task.setStartTime(startTime);
        task.setEndTime(startTime);
        if (!Util.isSeperatedTime(task, workDay.getTasks())) {
            throw new NotSeperatedTimesException("\nYou can not start a task in this time, only in empty time intervals! ");
        }
    }

    private void endTask() {
        listTasks(true);
        int monthIndex = workLog.getMonths().indexOf(workMonth);
        int dayIndex = workMonth.getDays().indexOf(workDay);

        System.out.print("\nPlease enter the number of the task you want to end: ");
        int taskIndex = unfinishedTaskIDs.get(Util.checkRange(1, unfinishedTaskIDs.size(), true));
        Task task = workDay.getTasks().get(taskIndex);

        workLog.removeTaskByMonth(monthIndex, dayIndex, taskIndex);
        System.out.print("\nPlease enter the endtime (in format HH:MM): ");
        askForEndTime(task);

        workLog.addTaskByMonth(monthIndex, dayIndex, task);
    }

    private void askForEndTime(Task task) {
        String input = Util.checkTimeFormat();
        task.setEndTime(input);
        Util.roundCheck(task, true);

        while (!Util.isSeperatedTime(task, workDay.getTasks()) || !task.isValidTime()) {
            System.out.println("You can not end a task in this time, only in empty time intervals! ");
            input = Util.checkTimeFormat();
            task.setEndTime(input);
            Util.roundCheck(task, true);
        }
    }

    private void deleteTask() {
        listTasks(false);
        int monthIndex = workLog.getMonths().indexOf(workMonth);
        int dayIndex = workMonth.getDays().indexOf(workDay);
        System.out.print("Please enter the number of the task you want to delete: ");
        int taskIndex = Util.checkRange(1, workDay.getTasks().size(), true);
        System.out.println("Are you sure deleting this task? (y/n) ");
        if (Util.isYes()) {
            workLog.removeTaskByMonth(monthIndex, dayIndex, taskIndex);
        }

    }

    private void modifyTask() {
        listTasks(false);
        int monthIndex = workLog.getMonths().indexOf(workMonth);
        int dayIndex = workMonth.getDays().indexOf(workDay);

        System.out.print("Please enter the number of the task you want to modify: ");
        int taskIndex = Util.checkRange(1, workDay.getTasks().size(), true);
        Task newTask = workDay.getTasks().get(dayIndex);
        modificationMenu(newTask);

        workLog.setTaskByMonth(monthIndex, dayIndex, taskIndex, newTask);
        System.out.println("The modified task: " + newTask + "\n(Old task: " + newTask.showOld() + ")");

    }

    public void modificationMenu(Task newTask) {
        int input;
        boolean modify = true;
        while (modify) {
            try {
                showModificationMenu(newTask);
                input = Util.checkRange(0, 4, false);
                switch (input) {
                    case 0:
                        modify = !exitModification();
                        break;
                    case 1:
                        modifyTaskID(newTask);
                        break;
                    case 2:
                        modifyStartTime(newTask);
                        break;
                    case 3:
                        modifyEndTime(newTask);
                        break;
                    case 4:
                        ModifyComment(newTask);
                        break;
                }
            } catch (NotExpectedTimeOrderException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void showModificationMenu(Task task) {
        System.out.println("\nModification menu: " + task.toString());
        System.out.println("---------------------------------------------------------------------\n");
        System.out.println("Choose one command of the followings and push its number!\n");
        System.out.println("0: Exit");
        System.out.println("1: Modify TaskID");
        System.out.println("2: Modify StartTime");
        System.out.println("3: Modify EndTime");
        System.out.println("4: Modify Comment\n");
        System.out.print("Your number: ");
    }

    private boolean exitModification() {
        System.out.print("Are you sure exitting form modification? (y/n) ");
        return Util.isYes();
    }

    private void modifyTaskID(Task task) {
        System.out.print("Please enter the new TaskID: ");
        task.setTaskIdOld();
        task.setTaskId(scanner.nextLine());
        while (!task.isValidTaskId()) {
            System.out.print("Wrong value, please type it again! ");
            task.setTaskId(scanner.nextLine());
        }
    }

    private void modifyStartTime(Task task) {
        System.out.print("Please enter the new StartTime: ");
        task.setStartTimeOld();
        task.setStartTime(Util.checkTimeFormat());
        Util.roundCheck(task, false);
        while (Util.isSeperatedTime(task, workDay.getTasks()) || !task.isValidTime()) {
            System.out.print("You can not start a task in this time, only in empty time intervals! ");
            System.out.print("Please try again: ");
            task.setStartTime(Util.checkTimeFormat());
            Util.roundCheck(task, false);
        }
    }

    private void modifyEndTime(Task task) {
        System.out.print("Please enter the new EndTime in format (HH:MM): ");
        task.setEndTimeOld();
        task.setEndTime(Util.checkTimeFormat());
        Util.roundCheck(task, true);
        while (Util.isSeperatedTime(task, workDay.getTasks()) || !task.isValidTime()) {
            System.out.print("You can not start a task in this time, only in empty time intervals! ");
            System.out.print("Please try again: ");
            task.setEndTime(Util.checkTimeFormat());
            Util.roundCheck(task, true);
        }
    }

    private void ModifyComment(Task task) {
        System.out.print("Please type the new comment: ");
        task.setCommentOld();
        task.setComment(scanner.nextLine());
    }

    private void showStatistics() {
        listMonths();
        if (!workLog.getMonths().isEmpty()) {
            System.out.println("Please enter the number of the month you want to show the statistics for: ");
            workMonth = workLog.getMonths().get(Util.checkRange(1, workLog.getMonths().size(), true));
            if (!workMonth.getDays().isEmpty()) {
                System.out.println("Summerized working hours of month " + workMonth.getDate() + " is " + workMonth.getSumPerMonth() + "\n");
                workMonth.getDays().forEach((day) -> {
                    System.out.println(day.getActualDay() + ": " + day.getSumPerDay());
                });
            } else {
                System.out.println("This month is empty!");
            }
        }
    }
}
