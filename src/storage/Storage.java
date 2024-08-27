package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
                default:
                    // Handle unexpected Task type
                    System.out.println("Unknown Task type: " + taskType);
                    break;
                }
                if (task != null) {
                    tasks.add(task);
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("An error occurred while loading Tasks.");
        }

        return tasks;
    }

    /**
     * Saves the list of Tasks to the file.
     *
     * @param tasks the list of Tasks to be saved
     */
    public void save(List<Task> tasks) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving Tasks.");
        }
    }
}
