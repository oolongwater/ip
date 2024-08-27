package command;

/**
 * Enum representing the various commands that can be issued in the Giorgo application.
 */
public enum Command {
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    BYE,
    UNKNOWN,
    DATE,
    FIND;

    /**
     * Converts a string to a corresponding Command enum value.
     * If the string does not match any command, returns UNKNOWN.
     *
     * @param commandString the string representation of the command
     * @return the corresponding Command enum value, or UNKNOWN if no match is found
     */
    public static Command fromString(String commandString) {
        for (Command command : Command.values()) {
            if (command.name().equalsIgnoreCase(commandString)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
