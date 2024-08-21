package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Unit tests for the Deadline class.
 */
public class DeadlineTest {

    /**
     * Tests the Deadline constructor to ensure it correctly parses valid dates
     * and throws an exception for invalid dates.
     */
    @Test
    public void testDeadlineConstructor() {
        assertThrows(DateTimeParseException.class, () -> {
            new Deadline("submit report", "invalid date");
        });

        Deadline deadline = new Deadline("submit report", "12/12/2023 1800");
        assertEquals("submit report", deadline.description);
        assertEquals("Dec 12 2023, 6:00PM", deadline.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")));
    }

    /**
     * Tests the toString method to ensure it returns the correct string representation
     * of the Deadline task.
     */
    @Test
    public void testToString() {
        Deadline deadline = new Deadline("submit report", "12/12/2023 1800");
        assertEquals("[D][ ] submit report (by: Dec 12 2023, 6:00PM)", deadline.toString());
    }
}