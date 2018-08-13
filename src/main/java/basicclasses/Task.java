package basicclasses;

import exceptions.InvalidInputException;

import java.time.Duration;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MARCI
 */
@Getter
@Setter
public final class Task {

    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;

    private String taskIdOld;
    private LocalTime startTimeOld;
    private LocalTime endTimeOld;
    private String commentOld;

    public Task(String taskId, int startHour, int startMin, int endHour, int endMin, String comment) {
        this.taskId = taskId;
        this.startTime = LocalTime.of(startHour, startMin);
        this.endTime = LocalTime.of(endHour, endMin);
        this.comment = comment;
    }

    public Task(String taskId, String startTime, String endTime, String comment) {
        this.taskId = taskId;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.comment = comment;
    }

    public Task(String taskId, String startTime, String comment) {
        this.taskId = taskId;
        this.startTime = LocalTime.parse(startTime);
        this.comment = comment;
    }

    public Task(String taskId) {
        this.taskId = taskId;
        if (!isValidTaskId()) {
            throw new InvalidInputException("Invalid taskID!");
        }
    }

    /**
     * @return - the duration of the task in minutes
     */
    public long getMinPerTask() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    /**
     * Checks whether the if of the task is valid or not.
     *
     * @return - true, if valid, false, if not
     */
    public boolean isValidTaskId() {
        return isValidRedmineTaskId() || isValidLTTaskId();
    }

    public boolean isValidRedmineTaskId() {
        return taskId.matches("^\\d{4}$");
    }

    public boolean isValidLTTaskId() {
        return taskId.matches("^LT-\\d{4}$");
    }

    public boolean isValidTime() {
        return startTime.compareTo(endTime) <= 0;
    }

    public String showOld() {
        return "taskId: " + taskIdOld + ", startTime: " + startTimeOld + ", endTime: " + endTimeOld + ", comment: " + commentOld;
    }

    @Override
    public String toString() {
        return "taskId: " + taskId + ", startTime: " + startTime + ", endTime: " + endTime + ", comment: " + comment;
    }
}
