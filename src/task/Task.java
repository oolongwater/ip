package task;

/**
 * Represents an abstract Task in the Giorgo application.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Constructs a Task with the specified description.
     *
     * @param description the description of the Task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the Task.
     *
     * @return the description of the Task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status of the Task.
     *
     * @return true if the Task is done, otherwise false
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the status of the Task.
     *
     * @param isDone the status of the Task
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns the status icon of the Task.
     *
     * @return "X" if the Task is done, otherwise " "
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns a string representation of the Task.
     *
     * @return a string representation of the Task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the file format representation of the Task.
     *
     * @return the file format representation of the Task
     */
    public abstract String toFileFormat();

}
