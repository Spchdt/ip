package kroissant;

/**
 * Represents a todo task without any date or time constraints.
 */
public class Todo extends Task {
    public Todo(String title) {
        super(title);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
