package task;

import exception.InvalidInputException;

/**
 * Represents a to-do Task in the Giorgo application.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo Task with the specified description.
     *
     * @param description the description of the to-do Task
     * @throws InvalidInputException if the description is invalid
     */
    public Todo(String description) throws InvalidInputException {
        super(description);
    }

    /**
     * Returns a string representation of the Todo Task.
     *
     * @return a string representation of the Todo Task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the file format representation of the Todo Task.
     *
     * @return the file format representation of the Todo Task
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }

    /**
     * Creates a Todo Task from its file format representation.
     *
     * @param line the file format representation of the Todo Task
     * @return the Todo Task, or null if the format is invalid
     */
    public static Todo fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length != 3 || !parts[0].equals("T")) {
            return null;
        }

        Todo todo = null;
        try {
            todo = new Todo(parts[2]);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        assert todo != null;
        todo.setDone(parts[1].equals("1"));
        return todo;
    }
}
