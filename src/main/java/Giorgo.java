import java.util.Scanner;

public class Giorgo {

    public static void main(String[] args) {
        String logo = " Giorgo ";
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100]; // Fixed-size array to store Task objects
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
                listTasks(tasks, taskCount);
            } else if (input.startsWith("mark ")) {
                markTask(input, tasks, taskCount);
            } else if (input.startsWith("unmark ")) {
                unmarkTask(input, tasks, taskCount);
            } else if (!input.equalsIgnoreCase("bye")) {
                addTask(input, tasks, taskCount);
                taskCount++;
            }

        } while (!input.equalsIgnoreCase("bye"));

        System.out.println(
                "____________________________________________________________\n" +
                        " Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n");

        scanner.close();
    }

    // Helper method to list tasks
    private static void listTasks(Task[] tasks, int taskCount) {
        if (taskCount == 0) {
            System.out.println("____________________________________________________________\n" +
                    " No tasks yet.\n" +
                    "____________________________________________________________\n");
        } else {
            System.out.println("____________________________________________________________\n" +
                    " Here are the tasks in your list:\n");
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].description);
            }
            System.out.println("____________________________________________________________\n");
        }
    }

    // Helper method to mark a task as done
    private static void markTask(String input, Task[] tasks, int taskCount) {
        try {
            int taskIndex = Integer.parseInt(input.substring(5)) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                tasks[taskIndex].isDone = true;
                System.out.println("____________________________________________________________\n" +
                        " Nice! I've marked this task as done:\n" +
                        "    [" + tasks[taskIndex].getStatusIcon() + "] " + tasks[taskIndex].description + "\n" +
                        "____________________________________________________________\n");
            } else {
                System.out.println("____________________________________________________________\n" +
                        " Invalid task number.\n" +
                        "____________________________________________________________\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("____________________________________________________________\n" +
                    " Invalid input. Please provide a task number after 'mark'.\n" +
                    "____________________________________________________________\n");
        }
    }

    // Helper method to unmark a task
    private static void unmarkTask(String input, Task[] tasks, int taskCount) {
        try {
            int taskIndex = Integer.parseInt(input.substring(7)) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                tasks[taskIndex].isDone = false;
                System.out.println("____________________________________________________________\n" +
                        " OK, I've marked this task as not done yet:\n" +
                        "    [" + tasks[taskIndex].getStatusIcon() + "] " + tasks[taskIndex].description + "\n" +
                        "____________________________________________________________\n");
            } else {
                System.out.println("____________________________________________________________\n" +
                        " Invalid task number.\n" +
                        "____________________________________________________________\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("____________________________________________________________\n" +
                    " Invalid input. Please provide a task number after 'unmark'.\n" +
                    "____________________________________________________________\n");
        }
    }

    // Helper method to add a task
    private static void addTask(String input, Task[] tasks, int taskCount) {
        if (taskCount < 100) {
            tasks[taskCount] = new Task(input);
            System.out.println("____________________________________________________________\n" +
                    " added: " + input + "\n" +
                    "____________________________________________________________\n");
        } else {
            System.out.println("____________________________________________________________\n" +
                    " Task list is full. Cannot add more tasks.\n" +
                    "____________________________________________________________\n");
        }
    }
}

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }
}