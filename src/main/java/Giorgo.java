package main.java;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import command.Command;
import command.Parser;
import exception.InvalidInputException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;
import ui.Ui;

/**
 * Main class for the Giorgo application.
 * Handles the initialization and running of the application.
 */
public class Giorgo {

    private static final String DIRECTORY_PATH = System.getProperty("user.home") + File.separator + "chatbot_tasks";
    private static final String FILE_PATH = DIRECTORY_PATH + File.separator + "tasks.txt";
    private final Ui ui;
    private final Storage storage;
    private final ArrayList<Task> tasks;
    private final Parser parser;

    /**
     * Constructs a new Giorgo instance.
     * Initializes the user interface, storage, task list, and parser.
     * Ensures the necessary directory for storing tasks exists.
     */
    public Giorgo() {
        createDirectoryIfNotExists();
        ui = new Ui();
        storage = new Storage(FILE_PATH);
        tasks = new ArrayList<>(storage.load());
        parser = new Parser();
    }

    private void createDirectoryIfNotExists() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String getResponse(String input) {
        Command command = parser.parseCommand(input);
        String argument = parser.parseArgument(input);
        String response;

        try {
            switch (command) {
            case LIST:
                response = ui.showTaskList(tasks.toArray(new Task[0]));
                break;
            case MARK:
                Task taskToMark = tasks.get(Integer.parseInt(argument) - 1);
                assert taskToMark != null : "Task to mark should not be null";
                taskToMark.setDone(true);
                storage.save(tasks);
                response = ui.getTaskMarked(taskToMark);
                break;
            case UNMARK:
                Task taskToUnmark = tasks.get(Integer.parseInt(argument) - 1);
                assert taskToUnmark != null : "Task to unmark should not be null";
                taskToUnmark.setDone(false);
                storage.save(tasks);
                response = ui.getTaskUnmarked(taskToUnmark);
                break;
            case TODO:
                if (argument.isEmpty()) {
                    throw new InvalidInputException("OOPS!!! The description of a todo cannot be empty.");
                }
                Task todo = new Todo(argument);
                tasks.add(todo);
                storage.save(tasks);
                response = ui.getTaskAdded(todo, tasks.size());
                break;
            case DEADLINE:
                String[] deadlineParts = parser.parseDeadlineArguments(argument);
                assert deadlineParts.length == 2 : "Deadline arguments should have 2 parts";
                Task deadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                tasks.add(deadline);
                storage.save(tasks);
                response = ui.getTaskAdded(deadline, tasks.size());
                break;
            case EVENT:
                String[] eventParts = parser.parseEventArguments(argument);
                assert eventParts.length == 3 : "Event arguments should have 3 parts";
                Task event = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                tasks.add(event);
                storage.save(tasks);
                response = ui.getTaskAdded(event, tasks.size());
                break;
            case DELETE:
                int indexToDelete = Integer.parseInt(argument) - 1;
                assert indexToDelete >= 0d
                        && indexToDelete < tasks.size() : "Task index to delete should be within bounds";
                Task taskToDelete = tasks.remove(indexToDelete);
                storage.save(tasks);
                response = ui.getTaskDeleted(taskToDelete, tasks.size());
                break;
            case DATE:
                LocalDate date = parser.parseDate(argument);
                assert date != null : "Parsed date should not be null";
                response = ui.getTasksOnDate(String.valueOf(date), tasks.toArray(new Task[0]));
                break;
            case FIND:
                response = ui.getMatchingTasks(argument, tasks.toArray(new Task[0]));
                break;
            case BYE:
                response = ui.getGoodbye();
                break;
            default:
                throw new InvalidInputException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (Exception e) {
            response = ui.getError(e.getMessage());
        }

        return response;
    }
}
