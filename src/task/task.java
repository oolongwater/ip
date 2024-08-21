package task;

import exception.InvalidInputException;

/**
 * Represents an abstract task in the Giorgo application.
 */
public abstract class task {
    public String description;
    public boolean isDone;

    /**
     * Constructs a task with the specified description.
     *
     * @param description the description of the task
     */
    public task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is done, otherwise " "
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a string representation of the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the file format representation of the task.
     *
     * @return the file format representation of the task
     */
    public abstract String toFileFormat();

    /**
     * Creates a task from its file format representation.
     *
     * @param line the file format representation of the task
     * @return the task, or null if the format is invalid
     */
    public static task fromFileFormat(String line) {
        return null;
    }
}



