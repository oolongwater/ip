package task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo;

import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.command.Command;
import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.command.Parser;
import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.exception.InvalidInputException;
import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.storage.Storage;
import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.Deadline;
import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.Event;
import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.Todo;
import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.ui.ui;
import task.task;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class Giorgo {

    private static final String DIRECTORY_PATH = System.getProperty("user.home") + File.separator + "chatbot_tasks";
    private static final String FILE_PATH = DIRECTORY_PATH + File.separator + "tasks.txt";

    private task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.ui.ui ui;
    private task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.storage.Storage storage;
    private ArrayList<task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task> tasks;
    private task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.command.Parser parser;

    public Giorgo() {
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
                        task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task taskToMark = tasks.get(Integer.parseInt(argument) - 1);
                        taskToMark.isDone = true;
                        storage.save(tasks);
                        ui.showTaskMarked(taskToMark);
                        break;
                    case UNMARK:
                        task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task taskToUnmark = tasks.get(Integer.parseInt(argument) - 1);
                        taskToUnmark.isDone = false;
                        storage.save(tasks);
                        ui.showTaskUnmarked(taskToUnmark);
                        break;
                    case TODO:
                        if (argument.isEmpty()) {
                            throw new task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.exception.InvalidInputException("OOPS!!! The description of a todo cannot be empty.");
                        } else {
                            task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task todo = new Todo(argument);
                            tasks.add(todo);
                            storage.save(tasks);
                            ui.showTaskAdded(todo, tasks.size());
                        }
                        break;
                    case DEADLINE:
                        String[] deadlineParts = parser.parseDeadlineArguments(argument);
                        task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task deadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                        tasks.add(deadline);
                        storage.save(tasks);
                        ui.showTaskAdded(deadline, tasks.size());
                        break;
                    case EVENT:
                        String[] eventParts = parser.parseEventArguments(argument);
                        task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task event = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                        tasks.add(event);
                        storage.save(tasks);
                        ui.showTaskAdded(event, tasks.size());
                        break;
                    case DELETE:
                        try {
                            int indexToDelete = Integer.parseInt(argument) - 1;
                            if (indexToDelete >= 0 && indexToDelete < tasks.size()) {
                                task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.task taskToDelete = tasks.remove(indexToDelete);
                                storage.save(tasks);
                                ui.showTaskDeleted(taskToDelete, tasks.size());
                            } else {
                                throw new task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.exception.InvalidInputException("OOPS!!! The task index is out of bounds.");
                            }
                        } catch (NumberFormatException e) {
                            throw new task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.task.task.src.main.giorgo.exception.InvalidInputException("OOPS!!! The task index must be a number.");
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
        new Giorgo().run();
    }
}

