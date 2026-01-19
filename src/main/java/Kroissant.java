import java.util.ArrayList;
import java.util.Scanner;

public class Kroissant {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printLine();
        System.out.println("  Welcome! I'm Kroissant.");
        System.out.println("  What do you knead today?");
        printLine();

        String userMsg = "";
        ArrayList<String> tasks = new ArrayList<>();

        while(!userMsg.equals("bye")) {
            System.out.print(">> ");
            userMsg = scanner.nextLine();

            if (userMsg.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else {
                tasks.add(userMsg);
                System.out.println("added: " + userMsg);
            }

            printLine();
        }
        System.out.println("  Time to roll out!");
        System.out.println("  Hope you have a loafly day!");
        printLine();
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
