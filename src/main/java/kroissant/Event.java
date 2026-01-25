package kroissant;

public class Event extends Task {
    private String from;
    private String to;

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
