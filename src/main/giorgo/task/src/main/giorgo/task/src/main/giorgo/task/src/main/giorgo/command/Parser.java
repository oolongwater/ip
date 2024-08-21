package task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.command;

import task.src.main.giorgo.task.src.main.giorgo.task.src.main.giorgo.exception.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public Command parseCommand(String userInput) {
        String[] parts = userInput.split(" ", 2);
        return Command.fromString(parts[0]);
    }

    public String parseArgument(String userInput) {
        String[] parts = userInput.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    public String[] parseDeadlineArguments(String argument) throws InvalidInputException {
        String[] parts = argument.split("/by ");
        if (parts.length != 2) {
            throw new InvalidInputException("Invalid deadline format. Use: deadline <description> /by <date/time>");
        }
        return parts;
    }

    public String[] parseEventArguments(String argument) throws InvalidInputException {
        String[] parts = argument.split("/from |/to ");
        if (parts.length != 3) {
            throw new InvalidInputException("Invalid event format. Use: event <description> /from <start> /to <end>");
        }
        return parts;
    }

    public LocalDate parseDate(String date) throws InvalidInputException {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date format. Use: d/M/yyyy");
        }
    }
}