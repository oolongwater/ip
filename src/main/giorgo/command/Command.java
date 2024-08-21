package command;

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
    DATE;

    public static Command fromString(String commandString) {
        for (Command command : Command.values()) {
            if (command.name().equalsIgnoreCase(commandString)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}