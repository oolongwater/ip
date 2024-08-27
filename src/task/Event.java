package task;

/**
 * Represents an event Task in the Giorgo application.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Constructs an Event Task with the specified description, start time, and end time.
     *
     * @param description the description of the event
     * @param from the start time of the event
     * @param to the end time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time of the event.
     *
     * @return the start time of the event
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns a string representation of the Event Task.
     *
     * @return a string representation of the Event Task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the file format representation of the Event Task.
     *
     * @return the file format representation of the Event Task
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + from + " | " + to;
    }

    /**
     * Creates an Event Task from its file format representation.
     *
     * @param line the file format representation of the Event Task
     * @return the Event Task, or null if the format is invalid
     */
    public static Event fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length != 5 || !parts[0].equals("E")) {
            return null;
        }

        Event event = new Event(parts[2], parts[3], parts[4]);
        event.setDone(parts[1].equals("1"));
        return event;
    }
}
