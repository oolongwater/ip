package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

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
        assertThrows(DateTimeParseException.class, () -> new Deadline("submit report", "invalid date", 1));

        Deadline deadline = new Deadline("submit report", "12/12/2023 1800", 2);
        assertEquals("submit report", deadline.getDescription());
        assertEquals("Dec 12 2023, 6:00PM", deadline.getBy().format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")));
        assertEquals(2, deadline.getPriority());
    }

    /**
     * Tests the toString method to ensure it returns the correct string representation
     * of the Deadline Task.
     */
    @Test
    public void testToString() {
        Deadline deadline = new Deadline("submit report", "12/12/2023 1800", 2);
        assertEquals("[D][ ] submit report (by: Dec 12 2023, 6:00PM)", deadline.toString());
    }
}
