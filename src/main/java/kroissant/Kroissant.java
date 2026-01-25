package kroissant;

/**
 * Represents the main chatbot application for task management.
 * Handles initialization, user interaction loop, and task persistence.
 */
public class Kroissant {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Kroissant instance with the specified storage file path.
     * Initializes UI, storage, and attempts to load existing tasks.
     * If loading fails, starts with an empty task list.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Kroissant(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (KroissantException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main application loop.
     * Displays welcome message, processes user commands until exit command is
     * received,
     * handles exceptions, and displays goodbye message on exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                isExit = Parser.parseAndExecute(fullCommand, tasks, ui, storage);
            } catch (KroissantException e) {
                ui.showError(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                ui.showError("We don't have that many batches in the oven.");
            } finally {
                ui.showLine();
            }
        }

        ui.showGoodbye();
        ui.close();
    }

    public static void main(String[] args) {
        new Kroissant("tasks.ser").run();
    }
}