package kroissant;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println("  Rise and Shine! I'm Kroissant.");
        System.out.println("  What ingredients are we mixing today?");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println("  Time to roll out!");
        System.out.println("  Let's get this bread again soon!");
        showLine();
    }

    public String readCommand() {
        System.out.print(">> ");
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        System.out.println("  OH CRUMBS!!! " + message);
    }

    public void showLoadingError() {
        System.out.println("  OH CRUMBS!!! Error loading tasks. Starting with an empty list.");
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("  Okay, I've folded this into the dough:");
        System.out.println("    " + task);
        System.out.println("  Now you have " + taskCount + " pastries baking in the list!");
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("  Noted. This one out the oven:");
        System.out.println("    " + task);
        System.out.println("  Now you have " + taskCount + " pastries baking in the list!");
    }

    public void showTaskMarked(Task task) {
        System.out.println("  Chef's kiss! This task is golden brown and fully baked:");
        System.out.println("    " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("  Oops, looks a bit doughy inside. Putting it back in the oven:");
        System.out.println("    " + task);
    }

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
