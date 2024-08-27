package ui;

import task.Deadline;
import task.Task;

/**
 * Handles user interactions in the Giorgo application.
 */
public class Ui {

    /**
     * Generates a string representation of the task list.
     *
     * @param tasks The list of tasks to be displayed.
     * @return A string containing the formatted list of tasks.
     */
    public String showTaskList(Task... tasks) {
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.length; i++) {
            sb.append(i + 1).append(". ").append(tasks[i]).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a message indicating that a task has been marked as done.
     *
     * @param task The task that has been marked as done.
     * @return A string message indicating the task has been marked as done.
     */
    public String getTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Returns a message indicating that a task has been marked as not done.
     *
     * @param task The task that has been marked as not done.
     * @return A string message indicating the task has been marked as not done.
     */
    public String getTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    /**
     * Returns a message indicating that a task has been added.
     *
     * @param task The task that has been added.
     * @param size The current number of tasks in the list.
     * @return A string message indicating the task has been added.
     */
    public String getTaskAdded(Task task, int size) {
        return "Got it. I've added this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns a message indicating that a task has been deleted.
     *
     * @param task The task that has been deleted.
     * @param size The current number of tasks in the list.
     * @return A string message indicating the task has been deleted.
     */
    public String getTaskDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns an error message.
     *
     * @param message The error message to be displayed.
     * @return A string containing the error message.
     */
    public String getError(String message) {
        return "Error: " + message;
    }

    /**
     * Returns a goodbye message.
     *
     * @return A string containing the goodbye message.
     */
    public String getGoodbye() {
        return "Bye. he he he";
    }

    /**
     * Returns a list of tasks that match the given argument.
     *
     * @param argument The argument to match against task descriptions.
     * @param tasks The list of tasks to search.
     * @return A string containing the list of matching tasks.
     */
    public String getMatchingTasks(String argument, Task... tasks) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (Task task : tasks) {
            if (task.getDescription().contains(argument)) {
                sb.append(task).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Returns a list of tasks that are due on the given date.
     *
     * @param date The date to match against task deadlines.
     * @param tasks The list of tasks to search.
     * @return A string containing the list of tasks due on the given date.
     */
    public String getTasksOnDate(String date, Task... tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks on " + date + ":\n");
        boolean hasTasks = false;

        for (Task task : tasks) {
            if (task instanceof Deadline) {
                if (((Deadline) task).getBy().equals(date)) {
                    sb.append(task).append("\n");
                    hasTasks = true;
                }
            }
        }

        if (!hasTasks) {
            sb = new StringBuilder("No tasks on " + date);
        }

        return sb.toString();
    }
}