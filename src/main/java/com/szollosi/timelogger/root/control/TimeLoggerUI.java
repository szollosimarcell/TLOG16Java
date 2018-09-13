package com.szollosi.timelogger.root.control;

import com.szollosi.timelogger.root.objects.Task;
import com.szollosi.timelogger.root.objects.TimeLogger;
import com.szollosi.timelogger.root.objects.Util;
import com.szollosi.timelogger.root.objects.WorkDay;
import com.szollosi.timelogger.root.objects.WorkMonth;
import com.szollosi.timelogger.root.exceptions.InvalidInputException;
import com.szollosi.timelogger.root.exceptions.NotExpectedTimeOrderException;
import com.szollosi.timelogger.root.exceptions.NotSeperatedTimesException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
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

    /**
     * Show a user interface-like menu in command line.
     */
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

    /**
     * The programs menu, that keeps it responsive for the user.
     *
     * @param n The number of the menu command
     * @return - true, if the program should continue, false, is the user has given an exit command.
     */
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
                    listAllTask();
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

    /**
     * Exits the program.
     *
     * @return - true, if the user wants to exit, false if not
     */
    private boolean exit() {
        System.out.print("\nAre you sure exitting from TimeLogger? (y/n) ");
        return Util.isYes();
    }

    /**
     * Lists the months of the time logger.
     */
    private void listMonths() {
        if (workLog.areThereAnyMonths()) {
            int i = 1;
            for (WorkMonth month : workLog.getMonths()) {
                System.out.println(i + ". " + month.getDate());
                i++;
            }
        }
    }

    /**
     * Lists the days of a given month.
     */
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

    /**
     * List all tasks of a given day.
     */
    private void listAllTask() {
        listDays();
        System.out.print("\nPlease type the number of the day! ");
        workDay = workMonth.getDays().get(Util.checkRange(1, workMonth.getDays().size(), true));
        if (workDay.areThereAnyTasks()) {
            int i = 1;
            for (Task task : workDay.getTasks()) {
                System.out.println(i + ". " + task.toString());
                i++;
            }
        }
    }

    /**
     * List only the unfinished tasks of a given day.
     */
    private void ListOnlyUnfinishedTasks() {
        listDays();
        System.out.print("\nPlease type the number of the day! ");
        workDay = workMonth.getDays().get(Util.checkRange(1, workMonth.getDays().size(), true));
        unfinishedTaskIDs.clear();

        System.out.println("You can only end unfinished tasks, these are the following:");
        if (workDay.areThereAnyTasks()) {
            for (int i = 0; i < workDay.getTasks().size(); i++) {
                Task task = workDay.getTasks().get(i);
                int number = 1;
                if (task.getMinPerTask() == 0) {
                    System.out.println(number + ". " + task.toString());
                    unfinishedTaskIDs.add(i);
                    number++;
                }
            }
        }
    }

    /**
     * Adds a new work month to the time logger.
     */
    private void addNewMonth() {
        WorkMonth month = createMonth();
        workLog.addMonth(month);
    }

    /**
     * Creates a month with specific user inputs.
     *
     * @return - the created work month
     */
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

    /**
     * Adds a new workday to a specific given month
     */
    private void addNewDay() {
        listMonths();
        System.out.print("\nPlease enter the number of the month: ");
        int monthIndex = Util.checkRange(1, workLog.getMonths().size(), true);
        workMonth = workLog.getMonths().get(monthIndex);
        WorkDay workDay = createDay();
        workLog.addWorkDayByMonth(monthIndex, workDay, false);
    }

    /**
     * Creates a day with specific user inputs.
     *
     * @return - the created workday
     */
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

    /**
     * Asks for the number of the day in the given month.
     *
     * @param maxDay - the maximum day of the given month.
     * @return - the number of the day
     */
    private int askForDayNumber(int maxDay) {
        System.out.print("\nPlease enter the number of the day you want to add in! (the current month has " + maxDay + " days): ");
        if (workMonth.getDate().getMonthValue() == LocalDate.now().getMonthValue()) {
            return Util.checkRange(1, LocalDate.now().getDayOfMonth(), false);
        } else {
            return Util.checkRange(1, maxDay, false);
        }
    }

    /**
     * Asks for the working time of the day whether the user wants to customize it or not
     *
     * @return the working time of the day, if the user haven't modified, returns 0
     */
    private int askForWorkingMins() {
        System.out.print("\nThe default required working time is 7,5 hours, do you want to customize it? (y/n) ");
        if (Util.isYes()) {
            System.out.print("Please type the required working hours in minutes! ");
            return Util.checkRange(300, 600, false);
        } else {
            return 0;
        }
    }

    /**
     * Start a task in the time logger
     */
    private void startTask() {
        listDays();
        int monthIndex = workLog.getMonths().indexOf(workMonth);
        System.out.print("\nPlease enter the number of the day you want to add into: ");
        int dayIndex = Util.checkRange(1, workMonth.getDays().size(), true);
        workDay = workMonth.getDays().get(dayIndex);
        Task task = createTask();
        workLog.addTaskByMonth(monthIndex, dayIndex, task);
    }

    /**
     * Creates a task with specific user input
     *
     * @return
     */
    private Task createTask() {
        System.out.println("\nPlease enter the ID of the task, valid ID can be 4 digits or LT-{4 digits}, for example: LT-4863");
        System.out.print("\nYour taskID: ");
        Task task = new Task(scanner.nextLine());

        System.out.print("\nPlease type the comment of the task: ");
        task.setComment(scanner.nextLine());

        System.out.println("\nThe end time of the last task is " + workDay.latestTaskEndTime());
        System.out.print("\nPlease enter the starting time with at least quarterly accuracy and in a format like 'HH:MM' ");
        askForStartTime(task);
        return task;
    }

    /**
     * Asks for start time end set a default value for end time.
     *
     * @param task
     */
    private void askForStartTime(Task task) {
        task.setStartTime(Util.timeInput());
        task.setEndTime(task.getStartTime());
        if (Util.isMultipleQuarterHour(task.getStartTime().getMinute()) == false) {
            throw new InvalidInputException("\n Invalid time! Please enter the time with at least quarterly accuracy! ");
        }
        if (!Util.isSeparatedTime(task, workDay.getTasks())) {
            throw new NotSeperatedTimesException("\nInvalid start time! The time intervals of the tasks cannot overlap each other!");
        }
    }

    /**
     * Ends a task in the time logger.
     */
    private void endTask() {
        ListOnlyUnfinishedTasks();
        int monthIndex = workLog.getMonths().indexOf(workMonth);
        int dayIndex = workMonth.getDays().indexOf(workDay);

        System.out.print("\nPlease enter the number of the task you want to end: ");
        int taskIndex = unfinishedTaskIDs.get(Util.checkRange(1, unfinishedTaskIDs.size(), true));
        Task task = workDay.getTasks().get(taskIndex);

        System.out.print("\nPlease enter the end time (in format HH:MM): ");
        askForEndTime(task, taskIndex);

        workLog.setTaskByMonth(monthIndex, dayIndex, taskIndex, task);
    }

    private void askForEndTime(Task task, int taskIndex) {
        task.setEndTime(Util.timeInput());
        if (!task.isValidTime()) {
            throw new NotExpectedTimeOrderException("\nThe end time cannot be lesser than the start time");
        }
        if (!Util.isSeparatedTime(task, workDay.getTasks(), taskIndex)) {
            throw new NotSeperatedTimesException("\nInvalid time interval! The time intervals of the tasks cannot overlap each other!");
        }
        if (!Util.isMultipleQuarterHour(((int) task.getMinPerTask()))) {
            System.out.println("The time you have just given is not multiple of quarter hour. Do you want to round it? (y/n)");
            if (Util.isYes()) {
                Util.roundEndTime(task);
            } else {
                throw new InvalidInputException("");
            }
        }
    }

    /**
     * Deletes a task from the time logger
     */
    private void deleteTask() {
        listAllTask();
        int monthIndex = workLog.getMonths().indexOf(workMonth);
        int dayIndex = workMonth.getDays().indexOf(workDay);
        System.out.print("Please enter the number of the task you want to delete: ");
        int taskIndex = Util.checkRange(1, workDay.getTasks().size(), true);
        System.out.println("Are you sure deleting this task? (y/n) ");
        if (Util.isYes()) {
            workLog.removeTaskByMonth(monthIndex, dayIndex, taskIndex);
        }

    }

    /**
     * Modifies a task in the time logger. Creates a copy of the task being altered and after modification it replaces
     * the old task with the new one.
     */
    private void modifyTask() {
        listAllTask();
        int monthIndex = workLog.getMonths().indexOf(workMonth);
        int dayIndex = workMonth.getDays().indexOf(workDay);

        System.out.print("Please enter the number of the task you want to modify: ");
        int taskIndex = Util.checkRange(1, workDay.getTasks().size(), true);
        Task newTask = workDay.getTasks().get(dayIndex);
        modificationMenu(newTask);

        workLog.setTaskByMonth(monthIndex, dayIndex, taskIndex, newTask);
        System.out.println("The modified task: " + newTask + "\n(Old task: " + newTask.showOld() + ")");

    }

    /**
     * This function is the modification menu of a specific task. It ends when the user decides to exit from the modification
     * of the specific task.
     *
     * @param newTask - the task being modified
     */
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
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Shows a user-interface-like modification menu for the specific task.
     *
     * @param task - the task being modified.
     */
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

    /**
     * Exits the modification of the task, if the user decides to.
     *
     * @return
     */
    private boolean exitModification() {
        System.out.print("Are you sure exitting form modification? (y/n) ");
        return Util.isYes();
    }

    /**
     * Modifies the id of the given task. Stores the previous id to the taskIdOld variable.
     *
     * @param task - the task being modified
     */
    private void modifyTaskID(Task task) {
        System.out.print("Please enter the new TaskID: ");
        task.setTaskId(scanner.nextLine());
        task.setTaskIdOld(task.getTaskId());
        if (task.isValidTaskId()) {
            throw new InvalidInputException("Invalid TaskID!");
        }
    }

    private void modifyStartTime(Task task) {
        System.out.print("Please enter the new start time: ");
        task.setStartTimeOld(task.getStartTime());
        task.setStartTime(Util.timeInput());
        if (!task.isValidTime()) {
            throw new NotExpectedTimeOrderException("\nThe end time cannot be lesser than the start time");
        }
        if (!Util.isSeparatedTime(task, workDay.getTasks())) {
            throw new NotSeperatedTimesException("\nInvalid time interval! The time intervals of the tasks cannot overlap each other!");
        }
        if (!Util.isMultipleQuarterHour(((int) task.getMinPerTask()))) {
            System.out.println("The time you have just given is not multiple of quarter hour. Do you want to round it? (y/n)");
            if (Util.isYes()) {
                Util.roundStartTime(task);
            } else {
                throw new InvalidInputException("");
            }
        }
    }

    private void modifyEndTime(Task task) {
        System.out.print("Please enter the new end time: ");
        task.setEndTimeOld(task.getEndTime());
        task.setEndTime(Util.timeInput());
        if (!task.isValidTime()) {
            throw new NotExpectedTimeOrderException("\nThe end time cannot be lesser than the start time");
        }
        if (!Util.isSeparatedTime(task, workDay.getTasks())) {
            throw new NotSeperatedTimesException("\nInvalid time interval! The time intervals of the tasks cannot overlap each other!");
        }
        if (!Util.isMultipleQuarterHour(((int) task.getMinPerTask()))) {
            System.out.println("The time you have just given is not multiple of quarter hour. Do you want to round it? (y/n)");
            if (Util.isYes()) {
                Util.roundEndTime(task);
            } else {
                throw new InvalidInputException("");
            }
        }
    }

    /**
     * Modifies the comment of the task. Stores the old comment to the commentOld variable.
     *
     * @param task
     */
    private void ModifyComment(Task task) {
        System.out.print("Please type the new comment: ");
        task.setCommentOld(task.getTaskId());
        task.setComment(scanner.nextLine());
    }

    /**
     * Show statistics for a given month.
     */
    private void showStatistics() {
        listMonths();
        System.out.println("Please enter the number of the month you want to show the statistics for: ");
        workMonth = workLog.getMonths().get(Util.checkRange(1, workLog.getMonths().size(), true));
        if (workMonth.areThereAnyDays()) {
            System.out.println("Summarized working minutes of month " + workMonth.getDate() + " is " + workMonth.getSumPerMonth() + "\n");
            workMonth.getDays().forEach((day) -> {
                System.out.println(day.getActualDay() + ": " + day.getSumPerDay());
            });
        }
    }
}
