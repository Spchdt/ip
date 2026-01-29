package kroissant;

import java.io.Serializable;

/**
 * Represents a task with a title and completion status.
 * Serves as the base class for all task types (Todo, Deadline, Event).
 */
public class Task implements Serializable {
    private String title;
    private boolean isDone;

    /**
     * Constructs a Task with the specified title.
     * The task is initially marked as not done.
     *
     * @param title Description of the task.
     */
    public Task(String title) {
        this.title = title;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing the completion status of the task.
     *
     * @return "X" if task is done, " " (space) otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markUndone() {
        this.isDone = false;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + title;
    }
}
