package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

/**
 * Handles the storage of tasks to and from a file.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath the path to the file where tasks are stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return a list of tasks loaded from the file
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        Path path = Paths.get(filePath);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                return tasks;
            }

            try (BufferedReader reader = Files.newBufferedReader(path)) {
                tasks = reader.lines()
                        .map(this::createTaskFromFileFormat)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }

        } catch (IOException e) {
            System.err.println("An error occurred while loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves the list of tasks to the file.
     *
     * @param tasks the list of tasks to be saved
     */
    public void save(List<Task> tasks) {
        Path path = Paths.get(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            tasks.stream()
                    .map(Task::toFileFormat)
                    .forEach(taskString -> {
                        try {
                            writer.write(taskString);
                            writer.newLine();
                        } catch (IOException e) {
                            System.err.println("An error occurred while saving tasks: " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("An error occurred while saving tasks: " + e.getMessage());
        }
    }

    /**
     * Creates a task from its file format representation.
     *
     * @param fileFormat the file format representation of the task
     * @return the created task, or null if the format is invalid
     */
    private Task createTaskFromFileFormat(String fileFormat) {
        String[] parts = fileFormat.split(" \\| ");
        String taskType = parts[0];

        return switch (taskType) {
        case "T" -> Todo.fromFileFormat(fileFormat);
        case "D" -> Deadline.fromFileFormat(fileFormat);
        case "E" -> Event.fromFileFormat(fileFormat);
        default -> {
            System.err.println("Unknown task type: " + taskType);
            yield null;
        }
        };
    }
}
