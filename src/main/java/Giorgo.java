import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.List;

enum Command {
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    BYE,
    UNKNOWN,
    DATE;

    public static Command fromString(String commandString) {
        for (Command command : Command.values()) {
            if (command.name().equalsIgnoreCase(commandString)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}


public class Giorgo {

    private static final String DIRECTORY_PATH = System.getProperty("user.home") + File.separator + "chatbot_tasks";
    private static final String FILE_PATH = DIRECTORY_PATH + File.separator + "tasks.txt";

    private Ui ui;
    private Storage storage;
    private ArrayList<Task> tasks;
    private Parser parser;

    public Giorgo() {
        createDirectoryIfNotExists();
        ui = new Ui();
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
                        Task taskToMark = tasks.get(Integer.parseInt(argument) - 1);
                        taskToMark.isDone = true;
                        storage.save(tasks);
                        ui.showTaskMarked(taskToMark);
                        break;
                    case UNMARK:
                        Task taskToUnmark = tasks.get(Integer.parseInt(argument) - 1);
                        taskToUnmark.isDone = false;
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
                        Task taskToDelete = tasks.get(Integer.parseInt(argument) - 1);
                        tasks.get(Integer.parseInt(argument) - 1);
                        storage.save(tasks);
                        ui.showTaskDeleted(taskToDelete, tasks.size());
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


class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
        System.out.println("ERROR");
    }
}

// Task class (base class)
abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public abstract String toFileFormat();

    public static Task fromFileFormat(String line) {
        return null;
    }

}

class Todo extends Task {
    public Todo(String description) throws InvalidInputException {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }


    public static Todo fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length != 3 || !parts[0].equals("T")) {
            return null;
        }

        Todo todo = null;
        try {
            todo = new Todo(parts[2]);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        todo.isDone = parts[1].equals("1");
        return todo;
    }
}

class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use: d/M/yyyy HHmm");
            throw e;
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
    }

    public static Deadline fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length != 4 || !parts[0].equals("D")) {
            return null;
        }

        Deadline deadline = null;
        try {
            deadline = new Deadline(parts[2], parts[3]);
            deadline.isDone = parts[1].equals("1");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use: d/M/yyyy HHmm");
        }
        return deadline;
    }
}

// Event class (inherits from Task)
class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    public static Event fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length != 5 || !parts[0].equals("E")) {
            return null;
        }

        Event event = null;
        event = new Event(parts[2], parts[3], parts[4]);
        event.isDone = parts[1].equals("1");
        return event;
    }
}

class Ui {
    private Scanner scanner;

    public Ui() {
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

    public void showTaskList(ArrayList<Task> tasks) {
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

    public static void listTasksOnDate(String date, ArrayList<Task> tasks) {
        LocalDate specifiedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
        System.out.println("____________________________________________________________\n" +
                " Here are the tasks on " + specifiedDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task instanceof Deadline && ((Deadline) task).by.toLocalDate().equals(specifiedDate)) {
                System.out.println((i + 1) + "." + task.toString());
            } else if (task instanceof Event && LocalDate.parse(((Event) task).from, DateTimeFormatter.ofPattern("d/M/yyyy HHmm")).equals(specifiedDate)) {
                System.out.println((i + 1) + "." + task.toString());
            }
        }
        System.out.println("____________________________________________________________\n");
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println("____________________________________________________________\n" +
                " Got it. I've added this task:\n" +
                "    " + task + "\n" +
                " Now you have " + size + " tasks in the list.\n" +
                "____________________________________________________________\n");
    }

    public void showTaskDeleted(Task task, int size) {
        System.out.println("____________________________________________________________\n" +
                " Noted. I've removed this task:\n" +
                "    " + task + "\n" +
                " Now you have " + size + " tasks in the list.\n" +
                "____________________________________________________________\n");
    }

    public void showTaskMarked(Task task) {
        System.out.println("____________________________________________________________\n" +
                " Nice! I've marked this task as done:\n" +
                "    [" + task.getStatusIcon() + "] " + task.description + "\n" +
                "____________________________________________________________\n");
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("____________________________________________________________\n" +
                " OK, I've marked this task as not done yet:\n" +
                "    [" + task.getStatusIcon() + "] " + task.description + "\n" +
                "____________________________________________________________\n");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }
}

class Storage {

    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        Path path = Paths.get(filePath);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                return tasks;
            }

            BufferedReader reader = Files.newBufferedReader(path);
            String line;
            while ((line = reader.readLine()) != null) {
                String taskType = line.split(" \\| ")[0];
                Task task = null;
                switch (taskType) {
                    case "T":
                        task = Todo.fromFileFormat(line);
                        break;
                    case "D":
                        task = Deadline.fromFileFormat(line);
                        break;
                    case "E":
                        task = Event.fromFileFormat(line);
                        break;
                }
                if (task != null) {
                    tasks.add(task);
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("An error occurred while loading tasks.");
        }

        return tasks;
    }

    public void save(List<Task> tasks) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks.");
        }
    }
}

class Parser {

    public Command parseCommand(String userInput) {
        String[] parts = userInput.split(" ", 2);
        return Command.fromString(parts[0]);
    }

    public String parseArgument(String userInput) {
        String[] parts = userInput.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    public String[] parseDeadlineArguments(String argument) throws InvalidInputException {
        String[] parts = argument.split("/by ");
        if (parts.length != 2) {
            throw new InvalidInputException("Invalid deadline format. Use: deadline <description> /by <date/time>");
        }
        return parts;
    }

    public String[] parseEventArguments(String argument) throws InvalidInputException {
        String[] parts = argument.split("/from |/to ");
        if (parts.length != 3) {
            throw new InvalidInputException("Invalid event format. Use: event <description> /from <start> /to <end>");
        }
        return parts;
    }

    public LocalDate parseDate(String date) throws InvalidInputException {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date format. Use: d/M/yyyy");
        }
    }
}

class TaskList {

    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task task) throws InvalidInputException {
        if (tasks.size() >= 100) {
            throw new InvalidInputException("Task list is full. Cannot add more tasks.");
        }
        tasks.add(task);
    }

    public void delete(int index) throws InvalidInputException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidInputException("Invalid task number.");
        }
        tasks.remove(index);
    }

    public Task get(int index) throws InvalidInputException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidInputException("Invalid task number.");
        }
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getTasks() {
        return tasks;
    }
}



