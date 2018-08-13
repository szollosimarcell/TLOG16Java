package basicclasses;

import exceptions.InvalidInputException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MARCI
 */
public class Util {

    public final static Scanner scanner = new Scanner(System.in);

    /**
     * Rounds the start time of a given task to be multiple of quarter hour
     *
     * @param task - the task which start time should be rounded
     */
    public static void roundStartTime(Task task) {
        LocalTime startTime = task.getStartTime();
        LocalTime endTime = task.getEndTime();
        long min = Duration.between(startTime, endTime).toMinutes();
        long remainder = min % 15;
        if (remainder <= 7) {
            startTime = startTime.plusMinutes(remainder);
        } else {
            startTime = startTime.minusMinutes(15 - remainder);
        }
        task.setStartTime(startTime);
    }

    /**
     * Rounds the end time of a given task to be multiple of quarter hour
     *
     * @param task - the task which end time should be rounded
     */
    public static void roundEndTime(Task task) {
        LocalTime startTime = task.getStartTime();
        LocalTime endTime = task.getEndTime();
        long min = Duration.between(startTime, endTime).toMinutes();
        long remainder = min % 15;
        if (remainder <= 7) {
            endTime = endTime.minusMinutes(remainder);
        } else {
            endTime = endTime.plusMinutes(15 - remainder);
        }
        task.setStartTime(startTime);
    }

    /**
     * Checks if the given value is multiple of a quarter hour (15)
     *
     * @param min - the value being checked
     * @return - true, if the value if multiple of a quarter hour, false if not
     */
    public static boolean isMultipleQuarterHour(long min) {
        return min % 15 == 0;
    }

    /**
     * Checks whether the given day is a weekday.
     *
     * @param workDay - the day being checked
     * @return - true, if it is a weekday, false not
     */
    public static boolean isWeekday(WorkDay workDay) {
        int dayNumber = workDay.getActualDay().getDayOfWeek().getValue();
        if (0 < dayNumber && dayNumber < 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether a task is overlapping any other in the list of tasks
     *
     * @param t - the task being checked whether it is overlapping any other task or not
     * @param tasks - the list of task where the overlapping is checked
     * @return - true, if the times are separated, false if not
     */
    public static boolean isSeparatedTime(Task t, List<Task> tasks) {
        return tasks.stream().noneMatch(task -> (t.getStartTime().isBefore(task.getEndTime()) && task.getStartTime().isBefore(t.getEndTime()))
                    || (t.getStartTime().equals(task.getStartTime())));
    }


    /**
     * Checks whether the input is in the given interval. If it is an index of a list, the returned values is decreased by 1.
     * If it is a normal input number, the returned value will be the input as it is.
     *
     * @param lowerLimit - the lower limit of the interval
     * @param upperLimit - the upper limit of the interval
     * @param isIndex - the boolean that decides whether it is an index, or a normal number input
     * @return - the input
     */
    public static int checkRange(int lowerLimit, int upperLimit, boolean isIndex) {
        int input = checkIfNumeric();
        if (input < lowerLimit || upperLimit < input) {
            throw new InvalidInputException("Wrong value! Please type only correct or reasonable numbers!");
        }
        return isIndex ? input - 1 : input;
    }

    /**
     * Checks whether the input is a numeric value.
     *
     * @return the input
     */
    public static int checkIfNumeric() {
        String input = scanner.nextLine();
        if (!input.matches("[0-9]+")) {
            throw new InvalidInputException("Wrong value! Please type only numeric characters!");
        }
        return Integer.parseInt(input);
    }

    /**
     * Checks whether the input is a valid has a valid time format.
     *
     * @return the input
     */
    public static LocalTime timeInput() {
        String input = scanner.nextLine();
        if (!input.matches("^\\d{2}:\\d{2}$")) {
            throw new InvalidInputException("Invalid time format!");
        }
        return LocalTime.parse(input);
    }

    /**
     * Checks whether the input is a character of 'y' or 'n'.
     *
     * @return - true, if the input is 'y', false if it is 'n'
     */
    public static boolean isYes() {
        String answer = scanner.nextLine();
        switch (answer) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                throw new InvalidInputException("Please type only 'y' or 'n'!");
        }
    }
}
