package task.src.main.giorgo.task.src.main.giorgo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends task {
    public LocalDateTime by;

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
