package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import exception.InvalidInputException;

/**
 * Unit tests for the Parser class.
 */
public class ParserTest {

    private final Parser parser = new Parser();

    /**
     * Tests the parseCommand method to ensure it correctly parses commands.
     */
    @Test
    public void testParseCommand() {
        assertEquals(Command.LIST, parser.parseCommand("list"));
        assertEquals(Command.TODO, parser.parseCommand("todo read book"));
    }

    /**
     * Tests the parseDeadlineArguments method to ensure it correctly parses deadline arguments.
     * Expects an InvalidInputException for invalid formats.
     *
     * @throws InvalidInputException if the input format is invalid
     */
    @Test
    public void testParseDeadlineArguments() throws InvalidInputException {
        assertThrows(InvalidInputException.class, () -> parser.parseDeadlineArguments("deadline read book"));

        String[] result = parser.parseDeadlineArguments("read book /by 12/12/2023");
        assertEquals("read book", result[0].trim());
        assertEquals("12/12/2023", result[1].trim());
    }
}
