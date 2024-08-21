package command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import exception.InvalidInputException;

public class ParserTest {

    private final Parser parser = new Parser();

    @Test
    public void testParseCommand() {
        assertEquals(Command.LIST, parser.parseCommand("list"));
        assertEquals(Command.TODO, parser.parseCommand("todo read book"));
    }

    @Test
    public void testParseDeadlineArguments() throws InvalidInputException {
        assertThrows(InvalidInputException.class, () -> {
            parser.parseDeadlineArguments("deadline read book");
        });

        String[] result = parser.parseDeadlineArguments("read book /by 12/12/2023");
        assertEquals("read book", result[0].trim());
        assertEquals("12/12/2023", result[1].trim());
    }
}