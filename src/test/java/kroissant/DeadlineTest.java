package kroissant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    public void testDeadlineCreation() {
        Deadline deadline = new Deadline("Submit report", "2/12/2024 1800");
        assertTrue(deadline.toString().contains("[D]"));
        assertTrue(deadline.toString().contains("Submit report"));
        assertTrue(deadline.toString().contains("(by:"));
    }

    @Test
    public void testDeadlineMarkDone() {
        Deadline deadline = new Deadline("Submit report", "2/12/2024 1800");
        deadline.markDone();
        assertTrue(deadline.toString().contains("[D][X]"));
        assertEquals("X", deadline.getStatusIcon());
    }

    @Test
    public void testDeadlineWithDifferentDateFormat() {
        Deadline deadline1 = new Deadline("Task 1", "2/12/2024 1800");
        Deadline deadline2 = new Deadline("Task 2", "02/12/2024 1800");
        Deadline deadline3 = new Deadline("Task 3", "2024-12-02 1800");

        assertTrue(deadline1.toString().contains("Dec 02 2024"));
        assertTrue(deadline2.toString().contains("Dec 02 2024"));
        assertTrue(deadline3.toString().contains("Dec 02 2024"));
    }

    @Test
    public void testDeadlineInvalidDateFormat() {
        assertThrows(IllegalArgumentException.class, () -> new Deadline("Task", "invalid date"));
    }

    @Test
    public void testDeadlineInvalidDateFormat2() {
        assertThrows(IllegalArgumentException.class, () -> new Deadline("Task", "2024-13-45"));
    }
}
