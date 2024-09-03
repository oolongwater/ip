package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event Task in the Giorgo application.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an Event Task with the specified description, start date, end date, and priority.
     *
     * @param description the description of the event Task
     * @param from the start date of the event Task
     * @param to the end date of the event Task
     * @param priority the priority of the event Task
     * @throws DateTimeParseException if the date format is invalid
     */
    public Event(String description, String from, String to, int priority) throws DateTimeParseException {
        super(description);
        this.from = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        this.to = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        this.priority = priority;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) + " to: " + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) + ")";
    }

    @Override
    public String toFileFormat() {
        return String.format("E | %s | %d | %s | %s", isDone() ? "1" : "0", priority, getDescription(), from.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm")) + " | " + to.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm")));
    }

    public static Event fromFileFormat(String fileFormat) {
        String[] parts = fileFormat.split(" \\| ");
        if (parts.length != 6 || !parts[0].equals("E")) {
            return null;
        }

        Event event = null;
        try {
            event = new Event(parts[3], parts[4], parts[5], Integer.parseInt(parts[2]));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        assert event != null;
        event.setDone(parts[1].equals("1"));
        return event;
    }
}