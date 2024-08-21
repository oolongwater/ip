package task;

import exception.InvalidInputException;

/**
 * Represents a to-do task in the Giorgo application.
 */
public class Todo extends task {

    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description the description of the to-do task
     * @throws InvalidInputException if the description is invalid
     */
    public Todo(String description) throws InvalidInputException {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task.
     *
     * @return a string representation of the Todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the file format representation of the Todo task.
     *
     * @return the file format representation of the Todo task
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Creates a Todo task from its file format representation.
     *
     * @param line the file format representation of the Todo task
     * @return the Todo task, or null if the format is invalid
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
        todo.isDone = parts[1].equals("1");
        return todo;
    }
}