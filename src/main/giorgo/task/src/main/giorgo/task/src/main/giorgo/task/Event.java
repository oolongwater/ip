package task.src.main.giorgo.task.src.main.giorgo.task;

// Event class (inherits from Task)
public class Event extends task {
    public String from;
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
