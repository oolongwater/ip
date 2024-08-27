package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Task with a deadline in the Giorgo application.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Constructs a Deadline Task with the specified description and deadline date/time.
     *
     * @param description the description of the Task
     * @param by the deadline date/time in the format d/M/yyyy HHmm
     * @throws DateTimeParseException if the date format is invalid
     */
    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use: d/M/yyyy HHmm");
            throw e;
        }
    }

    /**
     * Returns the deadline date/time.
     *
     * @return the deadline date/time
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the Deadline Task.
     *
     * @return a string representation of the Deadline Task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) + ")";
    }

    /**
     * Returns the file format representation of the Deadline Task.
     *
     * @return the file format representation of the Deadline Task
     */
    @Override
    public String toFileFormat() {
        return "D | "
                + (isDone() ? "1" : "0")
                + " | "
                + getDescription()
                + " | "
                + by.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
    }
    /**
     * Creates a Deadline Task from its file format representation.
     *
     * @param line the file format representation of the Deadline Task
     * @return the Deadline Task, or null if the format is invalid
     */
    public static Deadline fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length != 4 || !parts[0].equals("D")) {
            return null;
        }

        Deadline deadline = null;
        try {
            deadline = new Deadline(parts[2], parts[3]);
            deadline.setDone(parts[1].equals("1"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use: d/M/yyyy HHmm");
        }
        return deadline;
    }
}
