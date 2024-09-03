package task;

/**
 * Represents a todo Task in the Giorgo application.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo Task with the specified description and priority.
     *
     * @param description the description of the todo Task
     * @param priority the priority of the todo Task
     */
    public Todo(String description, int priority) {
        super(description);
        this.priority = priority;
    }

    @Override
    public String toFileFormat() {
        return String.format("T | %s | %d | %s", isDone() ? "1" : "0", priority, getDescription());
    }

    public static Todo fromFileFormat(String fileFormat) {
        String[] parts = fileFormat.split(" \\| ");
        if (parts.length != 4 || !parts[0].equals("T")) {
            return null;
        }

        Todo todo = new Todo(parts[3], Integer.parseInt(parts[2]));
        todo.setDone(parts[1].equals("1"));
        return todo;
    }
}
