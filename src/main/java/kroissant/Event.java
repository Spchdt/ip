package kroissant;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Constructs an Event task with the specified title and duration.
     *
     * @param title Description of the event.
     * @param from  Start time of the event.
     * @param to    End time of the event.
     */
    public Event(String title, String from, String to) {
        super(title);
        this.from = from;
        this.to = to;
    }

    private String durationToString() {
        return " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + durationToString();
    }
}
