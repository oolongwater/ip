package storage;

import task.task;
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
import task.Todo;

public class Storage {

    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<task> load() {
        List<task> tasks = new ArrayList<>();
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
                task task = null;
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

    public void save(List<task> tasks) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            for (task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks.");
        }
    }
}