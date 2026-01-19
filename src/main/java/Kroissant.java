import java.util.ArrayList;
import java.util.Scanner;

public class Kroissant {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //welcome message
        printLine();
        System.out.println("  Welcome! I'm Kroissant.");
        System.out.println("  What do you knead today?");
        printLine();

        //Save and display list
        String input = "";
        ArrayList<Task> tasks = new ArrayList<>();

        while(!input.equals("bye")) {
            System.out.print(">> ");
            input = scanner.nextLine();
            String command = input.split(" ")[0];
            String arguments = input.substring(input.indexOf(" ") + 1);

            switch (command) {
                case "list":
                    System.out.println("  Here's what's still need to be cooking:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    break;

                case "mark":
                    Task MarkedTask = tasks.get(Integer.parseInt(arguments) - 1);
                    MarkedTask.markDone();
                    System.out.println("  Good Job! This task is fully baked:");
                    System.out.println("    " + MarkedTask);
                    break;

                case "unmark":
                    Task UnmarkedTask = tasks.get(Integer.parseInt(arguments) - 1);
                    UnmarkedTask.markUndone();
                    System.out.println("  hmm OK, this needs more kneading:");
                    System.out.println("    " + UnmarkedTask);
                    break;

                case "todo":
                    Task newTodo = new Todo(arguments);
                    tasks.add(newTodo);
                    printAddedTask(newTodo, tasks.size());
                    break;

                case "deadline":
                    String[] deadlineArgs = arguments.split(" /");
                    Task newDeadline = new Deadline(deadlineArgs[0], deadlineArgs[1]);
                    tasks.add(newDeadline);
                    printAddedTask(newDeadline, tasks.size());
                    break;

                case "event":
                    String[] eventArgs = arguments.split(" /");
                    Task newEvent = new Event(eventArgs[0], eventArgs[1], eventArgs[2]);
                    tasks.add(newEvent);
                    printAddedTask(newEvent, tasks.size());
                    break;

                default:
                    System.out.println(input);
                    break;
            }

            printLine();
        }

        //Exit Message
        System.out.println("  Time to roll out!");
        System.out.println("  Hope you have a loafly day!");
        printLine();
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void printAddedTask(Task task, int size) {
        System.out.println("  Added task:");
        System.out.println("    " + task);
        System.out.println("Now you got " + size + " stuff in the list!");
    }
}
