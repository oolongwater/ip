package ui;

import task.Deadline;
import task.task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import task.Event;

/**
 * Handles user interactions in the Giorgo application.
 */
public class ui {
    private Scanner scanner;

    /**
     * Constructs a new UI instance.
     */
    public ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        String logo = "Giorgo";
        System.out.println("____________________________________________________________\n" +
                " Hello! I'm " + logo + "\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n");
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("____________________________________________________________\n" +
                " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n");
    }

    /**
     * Displays a line separator.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println("____________________________________________________________\n" +
                message + "\n" +
                "____________________________________________________________\n");
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks the list of tasks to display
     */
    public void showTaskList(ArrayList<task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("____________________________________________________________\n" +
                    " No tasks yet.\n" +
                    "____________________________________________________________\n");
        } else {
            System.out.println("____________________________________________________________\n" +
                    " Here are the tasks in your list:\n");
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
    public static void listTasksOnDate(String date, ArrayList<task> tasks) {
        LocalDate specifiedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
        System.out.println("____________________________________________________________\n" +
                " Here are the tasks on " + specifiedDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
        for (int i = 0; i < tasks.size(); i++) {
            task task = tasks.get(i);
            if (task instanceof Deadline && ((Deadline) task).by.toLocalDate().equals(specifiedDate)) {
                System.out.println((i + 1) + "." + task.toString());
            } else if (task instanceof Event && LocalDate.parse(((Event) task).from, DateTimeFormatter.ofPattern("d/M/yyyy HHmm")).equals(specifiedDate)) {
                System.out.println((i + 1) + "." + task.toString());
            }
        }
        System.out.println("____________________________________________________________\n");
    }

    /**
     * Displays a message indicating a task has been added.
     *
     * @param task the task that was added
     * @param size the new size of the task list
     */
    public void showTaskAdded(task task, int size) {
        System.out.println("____________________________________________________________\n" +
                " Got it. I've added this task:\n" +
                "    " + task + "\n" +
                " Now you have " + size + " tasks in the list.\n" +
                "____________________________________________________________\n");
    }

    /**
     * Displays a message indicating a task has been deleted.
     *
     * @param task the task that was deleted
     * @param size the new size of the task list
     */
    public void showTaskDeleted(task task, int size) {
        System.out.println("____________________________________________________________\n" +
                " Noted. I've removed this task:\n" +
                "    " + task + "\n" +
                " Now you have " + size + " tasks in the list.\n" +
                "____________________________________________________________\n");
    }

    /**
     * Displays a message indicating a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public void showTaskMarked(task task) {
        System.out.println("____________________________________________________________\n" +
                " Nice! I've marked this task as done:\n" +
                "    [" + task.getStatusIcon() + "] " + task.description + "\n" +
                "____________________________________________________________\n");
    }

    /**
     * Displays a message indicating a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     */
    public void showTaskUnmarked(task task) {
        System.out.println("____________________________________________________________\n" +
                " OK, I've marked this task as not done yet:\n" +
                "    [" + task.getStatusIcon() + "] " + task.description + "\n" +
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
    public void showMatchingTasks(String keyword, ArrayList<task> tasks) {
        System.out.println("____________________________________________________________\n" +
                " Here are the matching tasks in your list:\n");
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            task task = tasks.get(i);
            if (task.description.contains(keyword)) {
                System.out.println((count + 1) + "." + task.toString());
                count++;
            }
        }
        if (count == 0) {
            System.out.println(" No matching tasks found.\n");
        }
        System.out.println("____________________________________________________________\n");
    }
}