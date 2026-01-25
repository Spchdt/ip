package kroissant;

public class Parser {

    public static boolean parseAndExecute(String input, TaskList tasks, Ui ui, Storage storage)
            throws KroissantException {

        // Check for exit command
        if (input.equals("bye")) {
            return true;
        }

        // Parse command and arguments
        String[] parts = input.split(" ", 2);
        String commandStr = parts[0];
        String arguments = (parts.length > 1) ? parts[1] : "";

        // Convert String to Command enum
        Command command;
        try {
            command = Command.valueOf(commandStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new KroissantException("I don't have a recipe for that! Is it a secret menu item?");
        }

        // Execute command
        executeCommand(command, arguments, tasks, ui, storage);
        return false;
    }

    private static void executeCommand(Command command, String arguments, TaskList tasks, Ui ui, Storage storage)
            throws KroissantException {

        switch (command) {
            case LIST:
                ui.showTaskList(tasks);
                break;

            case MARK:
                validateArgs(arguments);
                int markIndex = parseIndex(arguments);
                tasks.mark(markIndex);
                ui.showTaskMarked(tasks.get(markIndex));
                storage.save(tasks.getTasks());
                break;

            case UNMARK:
                validateArgs(arguments);
                int unmarkIndex = parseIndex(arguments);
                tasks.unmark(unmarkIndex);
                ui.showTaskUnmarked(tasks.get(unmarkIndex));
                storage.save(tasks.getTasks());
                break;

            case TODO:
                if (arguments.isEmpty()) {
                    throw new KroissantException("You can't bake air! The description is empty.");
                }
                Task newTodo = new Todo(arguments);
                tasks.add(newTodo);
                ui.showTaskAdded(newTodo, tasks.size());
                storage.save(tasks.getTasks());
                break;

            case DEADLINE:
                if (!arguments.contains(" /by ")) {
                    throw new KroissantException("Don't let it burn! Please set a '/by' timer.");
                }
                String[] deadlineArgs = arguments.split(" /by ", 2);
                try {
                    Task newDeadline = new Deadline(deadlineArgs[0], deadlineArgs[1]);
                    tasks.add(newDeadline);
                    ui.showTaskAdded(newDeadline, tasks.size());
                    storage.save(tasks.getTasks());
                } catch (IllegalArgumentException e) {
                    throw new KroissantException(e.getMessage());
                }
                break;

            case EVENT:
                if (!arguments.contains(" /from ") || !arguments.contains(" /to ")) {
                    throw new KroissantException("This party is half-baked! I need '/from' and '/to' times.");
                }
                String[] eventArgs = arguments.split(" /from | /to ");
                Task newEvent = new Event(eventArgs[0], eventArgs[1], eventArgs[2]);
                tasks.add(newEvent);
                ui.showTaskAdded(newEvent, tasks.size());
                storage.save(tasks.getTasks());
                break;

            case DELETE:
                validateArgs(arguments);
                int deleteIndex = parseIndex(arguments);
                Task removedTask = tasks.delete(deleteIndex);
                ui.showTaskDeleted(removedTask, tasks.size());
                storage.save(tasks.getTasks());
                break;

            case BYE:
                // This case is handled in parseAndExecute
                break;
        }
    }

    private static void validateArgs(String args) throws KroissantException {
        if (args.isEmpty()) {
            throw new KroissantException("Which one? Give me the number, don't be a loafer!");
        }
    }

    private static int parseIndex(String indexStr) throws KroissantException {
        try {
            return Integer.parseInt(indexStr) - 1;
        } catch (NumberFormatException e) {
            throw new KroissantException("That's not a number, you doughnut!");
        }
    }
}
