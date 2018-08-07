/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public static Scanner scanner = new Scanner(System.in);

    public static LocalTime roundToMultipleQuarterHour(LocalTime startTime, LocalTime endTime, boolean isEndTime) {
        long min = Duration.between(startTime, endTime).toMinutes();
        long remainder = min % 15;
        if (isEndTime) {
            if (remainder <= 7) {
                endTime = endTime.minusMinutes(remainder);
                System.out.println(endTime);
            } else {
                endTime = endTime.plusMinutes(15 - remainder);
            }
            return endTime;
        } else {
            if (remainder <= 7) {
                startTime = startTime.plusMinutes(remainder);
            } else {
                startTime = startTime.minusMinutes(15 - remainder);
            }
            return startTime;
        }
    }

    public static void roundCheck(Task task, boolean isEndTime) {
        while (!isMultipleQuarterHour(task.getMinPerTask())) {
            System.out.println("This is not a valid time, you have to enter the time at least with quarterly accuracy!");
            System.out.println("Do you want to round it? (y/n) ");
            if (isYes()) {
                if (isEndTime) {
                    task.setEndTime(roundToMultipleQuarterHour(task.getStartTime(), task.getEndTime(), isEndTime));
                } else {
                    task.setStartTime(roundToMultipleQuarterHour(task.getStartTime(), task.getEndTime(), isEndTime));
                }
            } else {
                System.out.println("Then please enter a correct endtime: ");
                task.setEndTime(Util.checkTimeFormat());
            }
        }
    }

    public static boolean isMultipleQuarterHour(long minPerTask) {
        return minPerTask % 15 == 0;
    }

    public static boolean isWeekday(WorkDay workDay) {
        if (0 < workDay.getActualDay().getDayOfWeek().getValue()
                && workDay.getActualDay().getDayOfWeek().getValue() < 6) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSeperatedTime(Task t, List<Task> tasks) {
        return tasks.stream().noneMatch(task -> (t.getStartTime().isBefore(task.getEndTime()) && task.getStartTime().isBefore(t.getEndTime()))
                    || (t.getStartTime().equals(task.getStartTime())));
    }
    
    

    public static int checkRange(int lowerLimit, int upperLimit, boolean isIndex) throws InvalidInputException {
        int input = checkIfNumeric();
        if (input < lowerLimit || upperLimit < input) {
            throw new InvalidInputException("Wrong value! Please type only correct or reasonable numbers!");
        }
        return isIndex ? input - 1 : input;
    }

    public static int checkIfNumeric() {
        String input = scanner.nextLine();
        if (!input.matches("[0-9]+")) {
            throw new InvalidInputException("Wrong value! Please type only numberic characters!");
        }
        return Integer.parseInt(input);
    }

    public static String checkTimeFormat() {
        String input = scanner.nextLine();
        if (!input.matches("^\\d{2}:\\d{2}$")) {
            throw new InvalidInputException("Invalid time format!");
        }
        return input;
    }

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
