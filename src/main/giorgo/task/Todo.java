package task;

import exception.InvalidInputException;

public class Todo extends task {
    public Todo(String description) throws InvalidInputException {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }


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