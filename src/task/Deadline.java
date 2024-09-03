package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline Task in the Giorgo application.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructs a Deadline Task with the specified description, date, and priority.
     *
     * @param description the description of the deadline Task
     * @param by the date of the deadline Task
     * @param priority the priority of the deadline Task
     * @throws DateTimeParseException if the date format is invalid
     */
    public Deadline(String description, String by, int priority) throws DateTimeParseException {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[d/M/yyyy HHmm][dd/MM/yyyy HHmm]");
        this.by = LocalDateTime.parse(by, formatter);
        this.priority = priority;
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) + ")";
    }

    @Override
    public String toFileFormat() {
        return String.format("D | %s | %d | %s | %s", isDone() ? "1" : "0", priority, getDescription(), by.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm")));
    }

    public static Deadline fromFileFormat(String fileFormat) {
        String[] parts = fileFormat.split(" \\| ");
        if (parts.length != 5 || !parts[0].equals("D")) {
            return null;
        }

        Deadline deadline = null;
        try {
            deadline = new Deadline(parts[3], parts[4], Integer.parseInt(parts[2]));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        assert deadline != null;
        deadline.setDone(parts[1].equals("1"));
        return deadline;
    }
}
