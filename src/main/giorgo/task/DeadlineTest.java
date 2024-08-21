package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTest {

    @Test
    public void testDeadlineConstructor() {
        assertThrows(DateTimeParseException.class, () -> {
            new Deadline("submit report", "invalid date");
        });

        Deadline deadline = new Deadline("submit report", "12/12/2023 1800");
        assertEquals("submit report", deadline.description);
        assertEquals("Dec 12 2023, 6:00PM", deadline.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")));
    }

    @Test
    public void testToString() {
        Deadline deadline = new Deadline("submit report", "12/12/2023 1800");
        assertEquals("[D][ ] submit report (by: Dec 12 2023, 6:00PM)", deadline.toString());
    }
}