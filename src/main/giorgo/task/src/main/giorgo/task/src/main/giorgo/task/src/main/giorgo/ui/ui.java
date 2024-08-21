package task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.ui;

import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.Deadline;
import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.Event;
import task.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class ui {
    private Scanner scanner;

    public ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String logo = "Giorgo";
        System.out.println("____________________________________________________________\n" +
                " Hello! I'm " + logo + "\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n");
    }

    public void showGoodbye() {
        System.out.println("____________________________________________________________\n" +
                " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        System.out.println("____________________________________________________________\n" +
                message + "\n" +
                "____________________________________________________________\n");
    }

    public void showTaskList(ArrayList<task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task> tasks) {
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

    public static void listTasksOnDate(String date, ArrayList<task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task> tasks) {
        LocalDate specifiedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
        System.out.println("____________________________________________________________\n" +
                " Here are the tasks on " + specifiedDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
        for (int i = 0; i < tasks.size(); i++) {
            task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task task = tasks.get(i);
            if (task instanceof task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.Deadline && ((Deadline) task).by.toLocalDate().equals(specifiedDate)) {
                System.out.println((i + 1) + "." + task.toString());
            } else if (task instanceof task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.Event && LocalDate.parse(((Event) task).from, DateTimeFormatter.ofPattern("d/M/yyyy HHmm")).equals(specifiedDate)) {
                System.out.println((i + 1) + "." + task.toString());
            }
        }
        System.out.println("____________________________________________________________\n");
    }

    public void showTaskAdded(task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task task, int size) {
        System.out.println("____________________________________________________________\n" +
                " Got it. I've added this task:\n" +
                "    " + task + "\n" +
                " Now you have " + size + " tasks in the list.\n" +
                "____________________________________________________________\n");
    }

    public void showTaskDeleted(task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task task, int size) {
        System.out.println("____________________________________________________________\n" +
                " Noted. I've removed this task:\n" +
                "    " + task + "\n" +
                " Now you have " + size + " tasks in the list.\n" +
                "____________________________________________________________\n");
    }

    public void showTaskMarked(task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task task) {
        System.out.println("____________________________________________________________\n" +
                " Nice! I've marked this task as done:\n" +
                "    [" + task.getStatusIcon() + "] " + task.description + "\n" +
                "____________________________________________________________\n");
    }

    public void showTaskUnmarked(task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task task) {
        System.out.println("____________________________________________________________\n" +
                " OK, I've marked this task as not done yet:\n" +
                "    [" + task.getStatusIcon() + "] " + task.description + "\n" +
                "____________________________________________________________\n");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }
}