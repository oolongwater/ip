package task;

/**
 * Represents an event task in the Giorgo application.
 */
public class Event extends task {
    public String from;
    protected String to;

    /**
     * Constructs an Event task with the specified description, start time, and end time.
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
     * Returns a string representation of the Event task.
     *
     * @return a string representation of the Event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the file format representation of the Event task.
     *
     * @return the file format representation of the Event task
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    /**
     * Creates an Event task from its file format representation.
     *
     * @param line the file format representation of the Event task
     * @return the Event task, or null if the format is invalid
     */
    public static Event fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length != 5 || !parts[0].equals("E")) {
            return null;
        }

        Event event = new Event(parts[2], parts[3], parts[4]);
        event.isDone = parts[1].equals("1");
        return event;
    }
}
