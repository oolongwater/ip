package ui;

import java.util.ArrayList;

import task.Deadline;
import task.Task;

/**
 * Handles user interactions in the Giorgo application.
 */
public class Ui {

    public String showWelcome() {
        return "Welcome to Giorgo!";
    }

    public String showTaskList(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        String message = sb.toString();
        return message;
    }

    public String getTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    public String getTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    public String getTaskAdded(Task task, int size) {
        return "Got it. I've added this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }

    public String getTaskDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }

    public String getError(String message) {
        return "Error: " + message;
    }

    public String getGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    public String getMatchingTasks(String argument, ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (Task task : tasks) {
            if (task.getDescription().contains(argument)) {
                sb.append(task).append("\n");
            }
        }
        return sb.toString();
    }

    public String getTasksOnDate(String date, ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks on " + date + ":\n");
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                if (((Deadline) task).getBy().equals(date)) {
                    sb.append(task).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
