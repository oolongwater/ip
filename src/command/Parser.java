package command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import exception.InvalidInputException;

/**
 * Parses user input into commands and arguments for the Giorgo application.
 */
public class Parser {

    /**
     * Parses the command from the user input.
     *
     * @param userInput the input string from the user
     * @return the corresponding Command enum value
     */
    public Command parseCommand(String userInput) {
        String[] parts = userInput.split(" ", 2);
        return Command.fromString(parts[0]);
    }
    /**
     * Parses the arguments for a Todo task.
     *
     * @param argument the input string containing the Todo description and priority
     * @return an array containing the description and priority
     */
    public String[] parseTodoArguments(String argument) {
        return argument.split(",", 2);
    }
    /**
     * Parses the argument from the user input.
     *
     * @param userInput the input string from the user
     * @return the argument part of the input string
     */
    public String parseArgument(String userInput) {
        String[] parts = userInput.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    /**
     * Parses the arguments for a deadline command.
     *
     * @param argument the argument string for the deadline command
     * @return an array containing the description and date/time
     * @throws InvalidInputException if the format is invalid
     */
    public String[] parseDeadlineArguments(String argument) throws InvalidInputException {
        String[] parts = argument.split(" /by |, ");
        if (parts.length != 3) {
            throw new InvalidInputException(
                    "Invalid deadline format. Use: deadline <description> /by <date/time>, <priority>");
        }
        return parts;
    }

    /**
     * Parses the arguments for an event command.
     *
     * @param argument the argument string for the event command
     * @return an array containing the description, start, and end times
     * @throws InvalidInputException if the format is invalid
     */
    public String[] parseEventArguments(String argument) throws InvalidInputException {
        String[] parts = argument.split(" /from | /to |, ");
        if (parts.length != 4) {
            throw new InvalidInputException(
                    "Invalid event format. Use: event <description> /from <start date/time> /to <end date/time>, "
                            + "<priority>");
        }
        return parts;
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param date the date string in the format d/M/yyyy
     * @return the corresponding LocalDate object
     * @throws InvalidInputException if the date format is invalid
     */
    public LocalDate parseDate(String date) throws InvalidInputException {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date format. Use: d/M/yyyy");
        }
    }
}
