import java.util.Scanner;

public class Giorgo {
    public static void main(String[] args) {
        String logo = " Giorgo ";
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100]; // Fixed-size array to store tasks
        int taskCount = 0; // Keep track of the number of tasks

        System.out.println(
                "____________________________________________________________\n" +
                        " Hello! I'm " + logo + "\n" +
                        " What can I do for you?\n" +
                        "____________________________________________________________\n");

        String input;
        do {
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("list")) {
                // List all stored tasks
                if (taskCount == 0) {
                    System.out.println("____________________________________________________________\n" +
                            " No tasks yet.\n" +
                            "____________________________________________________________\n");
                } else {
                    System.out.println("____________________________________________________________\n");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                    System.out.println("____________________________________________________________\n");
                }
            } else if (!input.equalsIgnoreCase("bye")) {
                // Add task if it's not "bye" and there's space
                if (taskCount < 100) {
                    tasks[taskCount++] = input;
                    System.out.println("____________________________________________________________\n" +
                            " added: " + input + "\n" +
                            "____________________________________________________________\n");
                } else {
                    System.out.println("____________________________________________________________\n" +
                            " Task list is full. Cannot add more tasks.\n" +
                            "____________________________________________________________\n");
                }
            }

        } while (!input.equalsIgnoreCase("bye"));

        System.out.println(
                "____________________________________________________________\n" +
                        " Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n");

        scanner.close();
    }
}