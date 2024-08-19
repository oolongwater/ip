import java.util.Scanner;

public class Giorgo {
    public static void main(String[] args) {
        String logo = " Giorgo ";
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "____________________________________________________________\n" +
                        " Hello! I'm " + logo + "\n" +
                        " What can I do for you?\n" +
                        "____________________________________________________________\n");

        String input;
        do {
            input = scanner.nextLine();

            if (!input.equalsIgnoreCase("bye")) {
                System.out.println(
                        "____________________________________________________________\n" +
                                " " + input + "\n" +
                                "____________________________________________________________\n");
            }

        } while (!input.equalsIgnoreCase("bye"));

        System.out.println(
                "____________________________________________________________\n" +
                        " Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n");

        scanner.close();
    }
}