package task.src.main.giorgo.task;


public abstract class task {
    public String description;
    public boolean isDone;

    public task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public abstract String toFileFormat();

    public static task fromFileFormat(String line) {
        return null;
    }

}



