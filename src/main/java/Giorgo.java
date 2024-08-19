import java.util.Scanner;

public class Giorgo {

    public static void main(String[] args) throws InvalidInputException {
        String logo = "Giorgo";
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(
                "____________________________________________________________\n" +
                        " Hello! I'm " + logo + "\n" +
                        " What can I do for you?\n" +
                        "____________________________________________________________\n");

        String input = null;
        do {
            try {
                input = scanner.nextLine().trim();
                String[] parts = input.split(" ", 2);
                String command = parts[0];
                String argument = parts.length > 1 ? parts[1] : "";

                switch (command.toLowerCase()) {
                    case "list":
                        listTasks(tasks, taskCount);
                        break;
                    case "mark":
                        markTask(argument, tasks, taskCount);
                        break;
                    case "unmark":
                        unmarkTask(argument, tasks, taskCount);
                        break;
                    case "todo":
                        if (argument.isEmpty()) {
                            throw new InvalidInputException("OOPS!!! The description of a todo cannot be empty.");
                        } else {
                            addTask(new Todo(argument), tasks, taskCount);
                            taskCount++;
                        }
                        break;
                    case "deadline":
                        String[] deadlineParts = argument.split("/by ");
                        if (deadlineParts.length == 2) {
                            addTask(new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim()), tasks, taskCount);
                            taskCount++;
                        } else {
                            throw new InvalidInputException("Invalid deadline format. Use: deadline <description> /by <date/time>");
                        }
                        break;
                    case "event":
                        String[] eventParts = argument.split("/from |/to ");
                        if (eventParts.length == 3) {
                            addTask(new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()), tasks, taskCount);
                            taskCount++;
                        } else {
                            throw new InvalidInputException("Invalid event format. Use: event <description> /from <start> /to <end>");
                        }
                        break;
                    case "bye":
                        System.out.println("____________________________________________________________\n" +
                                " Bye. Hope to see you again soon!\n" +
                                "____________________________________________________________\n");
                        break;
                    default:
                        throw new InvalidInputException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

            } catch (InvalidInputException e) {
                System.out.println("____________________________________________________________\n" +
                        e.getMessage() +
                        "\n____________________________________________________________\n");
            }
        } while (!input.equalsIgnoreCase("bye"));
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
    private static void markTask(String input, Task[] tasks, int taskCount) throws InvalidInputException {
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
            throw new InvalidInputException("Invalid input. Please provide a task number after 'mark'.");
        }
    }

    // Helper method to unmark a task
    private static void unmarkTask(String input, Task[] tasks, int taskCount) throws InvalidInputException {
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
            throw new InvalidInputException("Invalid input. Please provide a task number after 'unmark'.");
        }
    }

    private static void addTask(Task task, Task[] tasks, int taskCount) throws InvalidInputException {
        if (taskCount < 100) {
            tasks[taskCount] = task;
            System.out.println("____________________________________________________________\n" +
                    " Got it. I've added this task:\n" +
                    "    " + task + "\n" +
                    " Now you have " + (taskCount + 1) + " tasks in the list.\n" +
                    "____________________________________________________________\n");
        } else {
            throw new InvalidInputException("Task list is full. Cannot add more tasks.");
        }
    }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
        System.out.println("ERROR");
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
    public Todo(String description) throws InvalidInputException {
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

