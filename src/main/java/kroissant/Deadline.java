package kroissant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline (due date and time).
 * Supports multiple date/time input formats and displays in a standardized
 * format.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter[] INPUT_FORMATS = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm")
    };
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
    private LocalDateTime date;

    /**
     * Constructs a Deadline task with the specified title and deadline.
     *
     * @param title Description of the task.
     * @param date  Deadline in one of the supported formats (yyyy-MM-dd HHmm,
     *              d/M/yyyy HHmm, dd/MM/yyyy HHmm).
     * @throws IllegalArgumentException If the date format is invalid.
     */
    public Deadline(String title, String date) {
        super(title);
        this.date = parseDate(date);
    }

    private LocalDateTime parseDate(String date) {
        for (DateTimeFormatter fmt : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(date, fmt);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy HHmm (e.g., 2/12/2019 1800)");
    }

    private String dateToString() {
        return " (by: " + date.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + dateToString();
    }
}
