package giorgo;

import command.Command;
import storage.Storage;
import task.task;
import ui.ui;
import exception.InvalidInputException;
import task.Deadline;
import task.Event;
import task.Todo;
import command.Parser;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class giorgo {

    private static final String DIRECTORY_PATH = System.getProperty("user.home") + File.separator + "chatbot_tasks";
    private static final String FILE_PATH = DIRECTORY_PATH + File.separator + "tasks.txt";

    private ui ui;
    private Storage storage;
    private ArrayList<task> tasks;
    private Parser parser;

    public giorgo() {
        createDirectoryIfNotExists();
        ui = new ui();
        storage = new Storage(FILE_PATH);
        tasks = new ArrayList<>(storage.load());
        parser = new Parser();
    }

    private void createDirectoryIfNotExists() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()){
            directory.mkdirs();
        }
    }

    public void run() {
        ui.showWelcome();

        String input;
        do {
            input = ui.readCommand();
            Command command = parser.parseCommand(input);
            String argument = parser.parseArgument(input);

            try {
                switch (command) {
                    case LIST:
                        ui.showTaskList(tasks);
                        break;
                    case MARK:
                        task taskToMark = tasks.get(Integer.parseInt(argument) - 1);
                        taskToMark.isDone = true;
                        storage.save(tasks);
                        ui.showTaskMarked(taskToMark);
                        break;
                    case UNMARK:
                        task taskToUnmark = tasks.get(Integer.parseInt(argument) - 1);
                        taskToUnmark.isDone = false;
                        storage.save(tasks);
                        ui.showTaskUnmarked(taskToUnmark);
                        break;
                    case TODO:
                        if (argument.isEmpty()) {
                            throw new InvalidInputException("OOPS!!! The description of a todo cannot be empty.");
                        } else {
                            task todo = new Todo(argument);
                            tasks.add(todo);
                            storage.save(tasks);
                            ui.showTaskAdded(todo, tasks.size());
                        }
                        break;
                    case DEADLINE:
                        String[] deadlineParts = parser.parseDeadlineArguments(argument);
                        task deadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                        tasks.add(deadline);
                        storage.save(tasks);
                        ui.showTaskAdded(deadline, tasks.size());
                        break;
                    case EVENT:
                        String[] eventParts = parser.parseEventArguments(argument);
                        task event = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                        tasks.add(event);
                        storage.save(tasks);
                        ui.showTaskAdded(event, tasks.size());
                        break;
                    case DELETE:
                        try {
                            int indexToDelete = Integer.parseInt(argument) - 1;
                            if (indexToDelete >= 0 && indexToDelete < tasks.size()) {
                                task taskToDelete = tasks.remove(indexToDelete);
                                storage.save(tasks);
                                ui.showTaskDeleted(taskToDelete, tasks.size());
                            } else {
                                throw new InvalidInputException("OOPS!!! The task index is out of bounds.");
                            }
                        } catch (NumberFormatException e) {
                            throw new InvalidInputException("OOPS!!! The task index must be a number.");
                        }
                        break;
                    case DATE:
                        LocalDate date = parser.parseDate(argument);
                        ui.listTasksOnDate(String.valueOf(date), tasks);
                        break;
                    case BYE:
                        ui.showGoodbye();
                        break;
                    default:
                        throw new InvalidInputException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }

        } while (!input.equalsIgnoreCase("bye"));
    }

    public static void main(String[] args) {
        new giorgo().run();
    }
}

