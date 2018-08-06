package basicclasses;

import exceptions.InvalidInputException;
import java.time.Duration;
import java.time.LocalTime;

/**
 *
 * @author MARCI
 */
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
            throw new InvalidInputException("Invalid TaskID!");
        }
    }

    public long getMinPerTask() {
        return Duration.between(startTime, endTime).toMinutes();
    }

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

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setStartTime(int hour, int min) {
        this.startTime = LocalTime.of(hour, min);
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime);
    }

    public void setEndTime(int hour, int min) {
        this.endTime = LocalTime.of(hour, min);
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTaskIdOld() {
        this.taskIdOld = taskId;
    }

    public void setStartTimeOld() {
        this.startTimeOld = startTime;
    }

    public void setEndTimeOld() {
        this.endTimeOld = endTime;
    }

    public void setCommentOld() {
        this.commentOld = comment;
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getComment() {
        return comment;
    }

}
