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
            String[] commands = input.split(" ");

            switch (commands[0]) {
                case "list":
                    System.out.println("  Here's what's still need to be cooking:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    break;

                case "mark":
                    Task MarkedTask = tasks.get(Integer.parseInt(commands[1]) - 1);
                    MarkedTask.markDone();
                    System.out.println("  Good Job! This task is fully baked:");
                    System.out.println("    " + MarkedTask);
                    break;

                case "unmark":
                    Task UnmarkedTask = tasks.get(Integer.parseInt(commands[1]) - 1);
                    UnmarkedTask.markUndone();
                    System.out.println("  hmm OK, this needs more kneading:");
                    System.out.println("    " + UnmarkedTask);
                    break;

                default:
                    tasks.add(new Task(input));
                    System.out.println("added: " + input);
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
}
