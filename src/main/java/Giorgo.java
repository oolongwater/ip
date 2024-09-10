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

        try {
            return switch (command) {
            case LIST -> handleList();
            case MARK -> handleMark(argument);
            case UNMARK -> handleUnmark(argument);
            case TODO -> handleTodo(argument);
            case DEADLINE -> handleDeadline(argument);
            case EVENT -> handleEvent(argument);
            case DELETE -> handleDelete(argument);
            case DATE -> handleDate(argument);
            case FIND -> handleFind(argument);
            case BYE -> handleBye();
            default -> throw new InvalidInputException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            };
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        } catch (Exception e) {
            return ui.getError(e.getMessage());
        }
    }

    private String handleList() {
        return ui.showTaskList(tasks.toArray(new Task[0]));
    }

    private String handleMark(String argument) {
        Task taskToMark = getTask(argument);
        taskToMark.setDone(true);
        storage.save(tasks);
        return ui.getTaskMarked(taskToMark);
    }

    private String handleUnmark(String argument) {
        Task taskToUnmark = getTask(argument);
        taskToUnmark.setDone(false);
        storage.save(tasks);
        return ui.getTaskUnmarked(taskToUnmark);
    }

    private String handleTodo(String argument) throws InvalidInputException {
        if (argument == null || argument.trim().isEmpty()) {
            throw new InvalidInputException("todo cannot be empty!");
        }
        String[] todoParts = parser.parseTodoArguments(argument);
        assert todoParts.length == 2 : "Todo arguments should have 2 parts";
        int priority = Integer.parseInt(todoParts[1].trim());
        Task todo = new Todo(todoParts[0].trim(), priority);
        tasks.add(todo);
        storage.save(tasks);
        return ui.getTaskAdded(todo, tasks.size());
    }

    private String handleDeadline(String argument) throws InvalidInputException {
        String[] deadlineParts = parser.parseDeadlineArguments(argument);
        assert deadlineParts.length == 3 : "Deadline arguments should have 3 parts";
        int priority = Integer.parseInt(deadlineParts[2].trim());
        Task deadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim(), priority);
        tasks.add(deadline);
        storage.save(tasks);
        return ui.getTaskAdded(deadline, tasks.size());
    }

    private String handleEvent(String argument) throws InvalidInputException {
        String[] eventParts = parser.parseEventArguments(argument);
        assert eventParts.length == 4 : "Event arguments should have 4 parts";
        int priority = Integer.parseInt(eventParts[3].trim());
        Task event = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim(), priority);
        tasks.add(event);
        storage.save(tasks);
        return ui.getTaskAdded(event, tasks.size());
    }

    private String handleDelete(String argument) {
        try {
            int indexToDelete = Integer.parseInt(argument) - 1;
            if (indexToDelete < 0 || indexToDelete >= tasks.size()) {
                throw new IndexOutOfBoundsException("UH-OH! There are only " + tasks.size() + " tasks in the list!");
            }
            Task taskToDelete = tasks.remove(indexToDelete);
            storage.save(tasks);
            return ui.getTaskDeleted(taskToDelete, tasks.size());
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
    }

    private String handleDate(String argument) throws InvalidInputException {
        LocalDate date = parser.parseDate(argument);
        assert date != null : "Parsed date should not be null";
        return ui.getTasksOnDate(String.valueOf(date), tasks.toArray(new Task[0]));
    }

    private String handleFind(String argument) {
        return ui.getMatchingTasks(argument, tasks.toArray(new Task[0]));
    }

    private String handleBye() {
        return ui.getGoodbye();
    }

    private Task getTask(String argument) throws IndexOutOfBoundsException {
        int index = Integer.parseInt(argument) - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("UH-OH! There are only " + tasks.size() + " tasks in the list!");
        }
        return tasks.get(index);
    }
}
