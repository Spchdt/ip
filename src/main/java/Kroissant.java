import java.util.ArrayList;
import java.util.Scanner;

public class Kroissant {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        printLine();
        System.out.println("  Rise and Shine! I'm Kroissant.");
        System.out.println("  What ingredients are we mixing today?");
        printLine();

        String input = "";

        while (true) {
            System.out.print(">> ");
            input = scanner.nextLine();

            // 1. Check for exit command immediately
            if (input.equals("bye")) {
                break;
            }

            try {
                // 2. Safe Parsing
                String[] parts = input.split(" ", 2);
                String commandStr = parts[0]; // Kept as String for Enum conversion
                String arguments = (parts.length > 1) ? parts[1] : "";

                printLine();

                // 3. Convert String to Enum (Minimal Change Logic)
                Command command;
                try {
                    command = Command.valueOf(commandStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new KroissantException("I don't have a recipe for that! Is it a secret menu item?");
                }

                switch (command) {
                    case LIST:
                        if (tasks.isEmpty()) {
                            System.out.println("  The oven is empty! Time to get kneading.");
                        } else {
                            System.out.println("  Here are the pastries rising in your list:");
                            for (int i = 0; i < tasks.size(); i++) {
                                System.out.println("  " + (i + 1) + ". " + tasks.get(i));
                            }
                        }
                        break;

                    case MARK:
                        validateArgs(arguments);
                        int markIndex = Integer.parseInt(arguments) - 1;
                        tasks.get(markIndex).markDone();
                        System.out.println("  Chef's kiss! This task is golden brown and fully baked:");
                        System.out.println("    " + tasks.get(markIndex));
                        break;

                    case UNMARK:
                        validateArgs(arguments);
                        int unmarkIndex = Integer.parseInt(arguments) - 1;
                        tasks.get(unmarkIndex).markUndone();
                        System.out.println("  Oops, looks a bit doughy inside. Putting it back in the oven:");
                        System.out.println("    " + tasks.get(unmarkIndex));
                        break;

                    case TODO:
                        if (arguments.isEmpty()) {
                            throw new KroissantException("You can't bake air! The description is empty.");
                        }
                        Task newTodo = new Todo(arguments);
                        tasks.add(newTodo);
                        printAddedTask(newTodo, tasks.size());
                        break;

                    case DEADLINE:
                        if (!arguments.contains(" /by ")) {
                            throw new KroissantException("Don't let it burn! Please set a '/by' timer.");
                        }
                        String[] deadlineArgs = arguments.split(" /by ", 2);
                        Task newDeadline = new Deadline(deadlineArgs[0], deadlineArgs[1]);
                        tasks.add(newDeadline);
                        printAddedTask(newDeadline, tasks.size());
                        break;

                    case EVENT:
                        if (!arguments.contains(" /from ") || !arguments.contains(" /to ")) {
                            throw new KroissantException("This party is half-baked! I need '/from' and '/to' times.");
                        }
                        String[] eventArgs = arguments.split(" /from | /to ");
                        Task newEvent = new Event(eventArgs[0], eventArgs[1], eventArgs[2]);
                        tasks.add(newEvent);
                        printAddedTask(newEvent, tasks.size());
                        break;

                    case DELETE:
                        validateArgs(arguments);
                        int deleteIndex = Integer.parseInt(arguments) - 1;
                        Task removedTask = tasks.remove(deleteIndex);
                        printDeletedTasks(removedTask, tasks.size());
                        break;

                    // No default case needed anymore!
                }

            } catch (KroissantException e) {
                System.out.println("  OH CRUMBS!!! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("  OH CRUMBS!!! That's not a number, you doughnut!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("  OH CRUMBS!!! We don't have that many batches in the oven.");
            }

            printLine();
        }

        // Exit Message
        printLine();
        System.out.println("  Time to roll out!");
        System.out.println("  Let's get this bread again soon!");
        printLine();
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void printDeletedTasks(Task task, int size) {
        System.out.println("  Noted. This one out the oven:");
        System.out.println("    " + task);
        System.out.println("  Now you have " + size + " pastries baking in the list!");
    }

    public static void printAddedTask(Task task, int size) {
        System.out.println("  Okay, I've folded this into the dough:");
        System.out.println("    " + task);
        System.out.println("  Now you have " + size + " pastries baking in the list!");
    }

    public static void validateArgs(String args) throws KroissantException {
        if (args.isEmpty()) {
            throw new KroissantException("Which one? Give me the number, don't be a loafer!");
        }
    }
}