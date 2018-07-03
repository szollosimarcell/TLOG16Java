/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicclasses;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author MARCI
 */
public class Util {

    public static long roundToMultipleQuarterHour(LocalTime startTime, LocalTime endTime) {
        long min = Duration.between(startTime, endTime).toMinutes();
        long remainder = min % 15;
        if (remainder <= 7) {
            min += remainder;
        } else {
            min -= remainder;
        }
        return min;
    }

    public static boolean isMultipleQuarterHour(long minPerTask) {
        return minPerTask % 15 == 0;
    }

    public static boolean isWeekday(WorkDay workDay) {
        return 0 < workDay.getActualDay().getDayOfWeek().getValue()
                && workDay.getActualDay().getDayOfWeek().getValue() < 6;
    }

    public static boolean isSeperatedTime(Task t, List<Task> tasks) {
        if (!tasks.isEmpty()) {
            if (!tasks.stream().noneMatch((task) -> (t.getStartTime().isBefore(task.getEndTime()) && task.getStartTime().isBefore(t.getEndTime())
                                                    || t.getStartTime().equals(task.getStartTime()) && t.getEndTime().equals(task.getEndTime())))) {
                return false;
            }
        }
        return true;
    }
}
