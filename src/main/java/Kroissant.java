import java.util.Scanner;

public class Kroissant {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printLine();
        System.out.println("  Welcome! I'm Kroissant.");
        System.out.println("  What do you knead today?");
        printLine();

        String userMsg = "";
        while(!userMsg.equals("bye")) {
            System.out.println(userMsg);
            System.out.print(">> ");
            userMsg = scanner.nextLine();
            printLine();
        }
        System.out.println("  Time to roll out!");
        System.out.println("  Hope you have a loaf-ly day!");
        printLine();
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
