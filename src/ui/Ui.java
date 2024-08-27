package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import task.Deadline;
import task.Event;
import task.Task;

/**
 * Handles user interactions in the Giorgo application.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a new UI instance.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        String logo = "Giorgo";
        System.out.println("____________________________________________________________\n"
                +
                " Hello! I'm " + logo + "\n"
                +
                " What can I do for you?\n"
                +
                "____________________________________________________________\n");
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("""
                ____________________________________________________________
                 Bye. Hope to see you again soon!
                ____________________________________________________________
                """);
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println("____________________________________________________________\n"
                +
                message + "\n"
                +
                "____________________________________________________________\n");
    }

    /**
     * Displays the list of Tasks.
     *
     * @param tasks the list of Tasks to display
     */
    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("""
                    ____________________________________________________________
                     No Tasks yet.
                    ____________________________________________________________
                    """);
        } else {
            System.out.println("""
                    ____________________________________________________________
                     Here are the Tasks in your list:
                    """);
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i).toString());
            }
            System.out.println("____________________________________________________________\n");
        }
    }

    /**
     * Displays the tasks on a specified date.
     *
     * @param date the date to filter tasks by
     * @param tasks the list of tasks to filter
     */
    public static void listTasksOnDate(String date, ArrayList<Task> tasks) {
        LocalDate specifiedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
        System.out.println("____________________________________________________________\n"
                +
                " Here are the tasks on " + specifiedDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task instanceof Deadline && ((Deadline) task).getBy().toLocalDate().equals(specifiedDate)) {
                System.out.println((i + 1) + "." + task);
            } else if (task instanceof Event && LocalDate.parse(((Event) task).getFrom(),
                    DateTimeFormatter.ofPattern("d/M/yyyy HHmm")).equals(specifiedDate)) {
                System.out.println((i + 1) + "." + task);
            }
        }
        System.out.println("____________________________________________________________\n");
    }

    /**
     * Displays a message indicating a Task has been added.
     *
     * @param task the Task that was added
     * @param size the new size of the Task list
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println("____________________________________________________________\n"
                +
                " Got it. I've added this Task:\n"
                +
                "    " + task + "\n"
                +
                " Now you have " + size + " Tasks in the list.\n"
                +
                "____________________________________________________________\n");
    }

    /**
     * Displays a message indicating a Task has been deleted.
     *
     * @param task the Task that was deleted
     * @param size the new size of the Task list
     */
    public void showTaskDeleted(Task task, int size) {
        System.out.println("____________________________________________________________\n"
                +
                " Noted. I've removed this Task:\n"
                +
                "    " + task + "\n"
                +
                " Now you have " + size + " Tasks in the list.\n"
                +
                "____________________________________________________________\n");
    }

    /**
     * Displays a message indicating a Task has been marked as done.
     *
     * @param task the Task that was marked as done
     */
    public void showTaskMarked(Task task) {
        System.out.println("____________________________________________________________\n"
                +
                " Nice! I've marked this Task as done:\n"
                +
                "    [" + task.getStatusIcon() + "] " + task.getDescription() + "\n"
                +
                "____________________________________________________________\n");
    }

    /**
     * Displays a message indicating a Task has been marked as not done.
     *
     * @param task the Task that was marked as not done
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("____________________________________________________________\n"
                +
                " OK, I've marked this Task as not done yet:\n"
                +
                "    [" + task.getStatusIcon() + "] " + task.getDescription() + "\n"
                +
                "____________________________________________________________\n");
    }

    /**
     * Reads a command from the user.
     *
     * @return the command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Displays the tasks that match the given keyword.
     *
     * @param keyword the keyword to search for
     * @param tasks the list of tasks to search
     */
    public void showMatchingTasks(String keyword, ArrayList<Task> tasks) {
        System.out.println("""
                ____________________________________________________________
                 Here are the matching tasks in your list:
                """);
        int count = 0;
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                System.out.println((count + 1) + "." + task);
                count++;
            }
        }
        if (count == 0) {
            System.out.println(" No matching tasks found.\n");
        }
        System.out.println("____________________________________________________________\n");
    }
}
