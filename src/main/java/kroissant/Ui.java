package kroissant;

import java.util.Scanner;

/**
 * Handles all user interface interactions including displaying messages and
 * reading user input.
 */
public class Ui {
    private static final String ERROR_PREFIX = "  OH CRUMBS!!! ";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        showLine();
        System.out.println("  Rise and Shine! I'm Kroissant.");
        System.out.println("  What ingredients are we mixing today?");
        showLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        showLine();
        System.out.println("  Time to roll out!");
        System.out.println("  Let's get this bread again soon!");
        showLine();
    }

    /**
     * Reads a command from the user.
     *
     * @return The user input string.
     */
    public String readCommand() {
        System.out.print(">> ");
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public void showLoadingError() {
        System.out.println(ERROR_PREFIX + "Error loading tasks. Starting with an empty list.");
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task      The task that was added.
     * @param taskCount Total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int taskCount) {
        assert task != null : "Task to display cannot be null";
        System.out.println("  Okay, I've folded this into the dough:");
        System.out.println("    " + task);
        System.out.println("  Now you have " + taskCount + " pastries baking in the list!");
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task      The task that was deleted.
     * @param taskCount Total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        assert task != null : "Task to display cannot be null";
        System.out.println("  Noted. This one out the oven:");
        System.out.println("    " + task);
        System.out.println("  Now you have " + taskCount + " pastries baking in the list!");
    }

    /**
     * Displays confirmation that a task was marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        assert task != null : "Task to display cannot be null";
        System.out.println("  Chef's kiss! This task is golden brown and fully baked:");
        System.out.println("    " + task);
    }

    /**
     * Displays confirmation that a task was unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        assert task != null : "Task to display cannot be null";
        System.out.println("  Oops, looks a bit doughy inside. Putting it back in the oven:");
        System.out.println("    " + task);
    }

    /**
     * Displays all tasks in the task list.
     * Shows a message if the list is empty.
     *
     * @param tasks TaskList containing all tasks.
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("  The oven is empty! Time to get kneading.");
        } else {
            System.out.println("  Here are the pastries rising in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Displays search results.
     *
     * @param matchingTasks ArrayList of tasks matching the search.
     */
    public void showSearchResults(java.util.ArrayList<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println("  No matching pastries found in the oven!");
        } else {
            System.out.println("  Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println("  " + (i + 1) + "." + matchingTasks.get(i));
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
