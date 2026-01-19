public class Deadline extends Task {
    private String date;

    public Deadline(String title, String date) {
        super(title);
        this.date = date.substring(date.indexOf(" ") + 1);
    }

    private String dateToString() {
        return " (by: " + date + ")";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + dateToString();
    }
}
