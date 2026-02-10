package kroissant;

import java.util.ArrayList;

/**
 * Manages a list of tasks with operations for adding, deleting, marking, and
 * retrieving tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added.
     */
    public void add(Task task) {
        assert task != null : "Task to add cannot be null";
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the specified index.
     *
     * @param index Index of the task to delete (0-based).
     * @return The deleted task.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task delete(int index) throws IndexOutOfBoundsException {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of the task to retrieve (0-based).
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task get(int index) throws IndexOutOfBoundsException {
        return tasks.get(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index Index of the task to mark (0-based).
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public void mark(int index) throws IndexOutOfBoundsException {
        tasks.get(index).markDone();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index Index of the task to unmark (0-based).
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public void unmark(int index) throws IndexOutOfBoundsException {
        tasks.get(index).markUndone();
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds all tasks containing the specified keyword in their title.
     *
     * @param keyword The keyword to search for (case-insensitive).
     * @return ArrayList of matching tasks.
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
