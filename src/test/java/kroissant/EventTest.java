package kroissant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testEventCreation() {
        Event event = new Event("Team meeting", "2pm", "4pm");
        assertEquals("[E][ ] Team meeting (from: 2pm to: 4pm)", event.toString());
    }

    @Test
    public void testEventMarkDone() {
        Event event = new Event("Team meeting", "2pm", "4pm");
        event.markDone();
        assertEquals("[E][X] Team meeting (from: 2pm to: 4pm)", event.toString());
        assertEquals("X", event.getStatusIcon());
    }

    @Test
    public void testEventMarkUndone() {
        Event event = new Event("Team meeting", "2pm", "4pm");
        event.markDone();
        event.markUndone();
        assertEquals("[E][ ] Team meeting (from: 2pm to: 4pm)", event.toString());
        assertEquals(" ", event.getStatusIcon());
    }

    @Test
    public void testEventWithEmptyTimes() {
        Event event = new Event("Meeting", "", "");
        assertEquals("[E][ ] Meeting (from:  to: )", event.toString());
    }

    @Test
    public void testEventWithDetailedTimes() {
        Event event = new Event("Conference", "Monday 9am", "Friday 5pm");
        assertTrue(event.toString().contains("Monday 9am"));
        assertTrue(event.toString().contains("Friday 5pm"));
    }
}
