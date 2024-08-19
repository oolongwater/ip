import java.util.Scanner;

public class Giorgo {

    public static void main(String[] args) {
        String logo = " Giorgo ";
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

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
            } else if (input.startsWith("todo ")) {
                addTask(new Todo(input.substring(5)), tasks, taskCount);
                taskCount++;
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split("/by ");
                if (parts.length == 2) {
                    addTask(new Deadline(parts[0].trim(), parts[1].trim()), tasks, taskCount);
                    taskCount++;
                } else {
                    System.out.println("____________________________________________________________\n" +
                            " Invalid deadline format. Use: deadline <description> /by <date/time>\n" +
                            "____________________________________________________________\n");
                }
            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split("/from |/to ");
                if (parts.length == 3) {
                    addTask(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()), tasks, taskCount);
                    taskCount++;
                } else {
                    System.out.println("____________________________________________________________\n" +
                            " Invalid event format. Use: event <description> /from <start> /to <end>\n" +
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
                System.out.println((i + 1) + "." + tasks[i].toString());
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

    private static void addTask(Task task, Task[] tasks, int taskCount) {
        if (taskCount < 100) {
            tasks[taskCount] = task;
            System.out.println("____________________________________________________________\n" +
                    " Got it. I've added this task:\n" +
                    "    " + task + "\n" +
                    " Now you have " + (taskCount + 1) + " tasks in the list.\n" +
                    "____________________________________________________________\n");
        } else {
            System.out.println("____________________________________________________________\n" +
                    " Task list is full. Cannot add more tasks.\n" +
                    "____________________________________________________________\n");
        }
    }
}



// Task class (base class)
class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

// Deadline class (inherits from Task)
class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

// Event class (inherits from Task)
class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}