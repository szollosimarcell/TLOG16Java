package basicclasses;

import java.time.Duration;
import java.time.LocalTime;

/**
 *
 * @author MARCI
 */
public class Task {
    
    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;

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

    public Task(String taskId) {
        this.taskId = taskId;
    }

    public long getMinPerTask() {
        return Duration.between(startTime, endTime).toMinutes();
    }
    
    public boolean isValidTaskId() {
        return isValidRedmineTaskId() || isValidLTTaskId();
    }
    
    public boolean isValidRedmineTaskId() {
        return taskId.matches("\\d{4}");
    }
    
    public boolean isValidLTTaskId() {
        return taskId.matches("LT-\\d{4}");
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setStartTime(int hour, int min) {
        this.startTime = LocalTime.of(hour, min);
    }
    
    public void setStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime);
    }

    public void setEndTime(int hour, int min) {
        this.endTime = LocalTime.of(hour, min);
    }
    
    public void setEndtTime(String endTime) {
        this.endTime = LocalTime.parse(endTime);
    }

    public void setComment(String comment) {
        this.comment = comment;
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
