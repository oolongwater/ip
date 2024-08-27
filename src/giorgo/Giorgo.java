package giorgo;

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
     * Constructor for the Giorgo class.
     * Initializes the UI, storage, tasks, and parser.
     */
    public Giorgo() {
        createDirectoryIfNotExists();
        ui = new Ui();
        storage = new Storage(FILE_PATH);
        tasks = new ArrayList<>(storage.load());
        parser = new Parser();
    }

    /**
     * Ensures the directory for storing tasks exists.
     * Creates the directory if it does not exist.
     */
    private void createDirectoryIfNotExists() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Runs the main loop of the Giorgo application.
     * Handles user input and executes corresponding commands.
     */
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
                    Task taskToMark = tasks.get(Integer.parseInt(argument) - 1);
                    taskToMark.setDone(true);
                    storage.save(tasks);
                    ui.showTaskMarked(taskToMark);
                    break;
                case UNMARK:
                    Task taskToUnmark = tasks.get(Integer.parseInt(argument) - 1);
                    taskToUnmark.setDone(false);
                    storage.save(tasks);
                    ui.showTaskUnmarked(taskToUnmark);
                    break;
                case TODO:
                    if (argument.isEmpty()) {
                        throw new InvalidInputException("OOPS!!! The description of a todo cannot be empty.");
                    } else {
                        Task todo = new Todo(argument);
                        tasks.add(todo);
                        storage.save(tasks);
                        ui.showTaskAdded(todo, tasks.size());
                    }
                    break;
                case DEADLINE:
                    String[] deadlineParts = parser.parseDeadlineArguments(argument);
                    Task deadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                    tasks.add(deadline);
                    storage.save(tasks);
                    ui.showTaskAdded(deadline, tasks.size());
                    break;
                case EVENT:
                    String[] eventParts = parser.parseEventArguments(argument);
                    Task event = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                    tasks.add(event);
                    storage.save(tasks);
                    ui.showTaskAdded(event, tasks.size());
                    break;
                case DELETE:
                    try {
                        int indexToDelete = Integer.parseInt(argument) - 1;
                        if (indexToDelete >= 0 && indexToDelete < tasks.size()) {
                            Task taskToDelete = tasks.remove(indexToDelete);
                            storage.save(tasks);
                            ui.showTaskDeleted(taskToDelete, tasks.size());
                        } else {
                            throw new InvalidInputException("OOPS!!! The Task index is out of bounds.");
                        }
                    } catch (NumberFormatException e) {
                        throw new InvalidInputException("OOPS!!! The Task index must be a number.");
                    }
                    break;
                case DATE:
                    LocalDate date = parser.parseDate(argument);
                    Ui.listTasksOnDate(String.valueOf(date), tasks);
                    break;
                case FIND:
                    System.out.println("find command");
                    ui.showMatchingTasks(argument, tasks);
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

    /**
     * Main method to start the Giorgo application.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        new Giorgo().run();
    }
}

