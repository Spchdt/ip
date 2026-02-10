package kroissant;

/**
 * Parses and executes user commands for the task management application.
 */
public class Parser {

    /**
     * Parses the user input and executes the corresponding command.
     * Returns true if the command is an exit command, false otherwise.
     *
     * @param input   User input string containing the command and arguments.
     * @param tasks   TaskList to operate on.
     * @param ui      UI instance for displaying messages.
     * @param storage Storage instance for saving tasks.
     * @return True if exit command is received, false otherwise.
     * @throws KroissantException If the command is invalid or execution fails.
     */
    public static boolean parseAndExecute(String input, TaskList tasks, Ui ui, Storage storage)
            throws KroissantException {
        assert input != null : "Input command cannot be null";

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
        assert command != null : "Command cannot be null";

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

            case FIND:
                if (arguments.isEmpty()) {
                    throw new KroissantException("What are you looking for? Give me a keyword to search!");
                }
                ui.showSearchResults(tasks.find(arguments));
                break;

            case BYE:
                // This case is handled in parseAndExecute
                break;

            default:
                // Should not reach here as invalid commands are caught earlier
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

    /**
     * Parses and executes user input, returning a response string for GUI mode.
     *
     * @param input   User input string containing the command and arguments.
     * @param tasks   TaskList to operate on.
     * @param storage Storage instance for saving tasks.
     * @return Response message as a String.
     * @throws KroissantException If the command is invalid or execution fails.
     */
    public static String getResponse(String input, TaskList tasks, Storage storage) throws KroissantException {
        // Check for exit command
        if (input.equals("bye")) {
            return "Time to roll out!\nLet's get this bread again soon!";
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

        // Execute command and return response
        return executeForResponse(command, arguments, tasks, storage);
    }

    private static String executeForResponse(Command command, String arguments, TaskList tasks, Storage storage)
            throws KroissantException {

        switch (command) {
            case LIST:
                if (tasks.isEmpty()) {
                    return "The oven is empty! Time to get kneading.";
                } else {
                    StringBuilder sb = new StringBuilder("Here are the pastries rising in your list:\n");
                    for (int i = 0; i < tasks.size(); i++) {
                        sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
                    }
                    return sb.toString();
                }

            case MARK:
                validateArgs(arguments);
                int markIndex = parseIndex(arguments);
                tasks.mark(markIndex);
                storage.save(tasks.getTasks());
                return "Chef's kiss! This task is golden brown and fully baked:\n  " + tasks.get(markIndex);

            case UNMARK:
                validateArgs(arguments);
                int unmarkIndex = parseIndex(arguments);
                tasks.unmark(unmarkIndex);
                storage.save(tasks.getTasks());
                return "Oops, looks a bit doughy inside. Putting it back in the oven:\n  " + tasks.get(unmarkIndex);

            case TODO:
                if (arguments.isEmpty()) {
                    throw new KroissantException("You can't bake air! The description is empty.");
                }
                Task newTodo = new Todo(arguments);
                tasks.add(newTodo);
                storage.save(tasks.getTasks());
                return "Okay, I've folded this into the dough:\n  " + newTodo
                        + "\nNow you have " + tasks.size() + " pastries baking in the list!";

            case DEADLINE:
                if (!arguments.contains(" /by ")) {
                    throw new KroissantException("Don't let it burn! Please set a '/by' timer.");
                }
                String[] deadlineArgs = arguments.split(" /by ", 2);
                try {
                    Task newDeadline = new Deadline(deadlineArgs[0], deadlineArgs[1]);
                    tasks.add(newDeadline);
                    storage.save(tasks.getTasks());
                    return "Okay, I've folded this into the dough:\n  " + newDeadline
                            + "\nNow you have " + tasks.size() + " pastries baking in the list!";
                } catch (IllegalArgumentException e) {
                    throw new KroissantException(e.getMessage());
                }

            case EVENT:
                if (!arguments.contains(" /from ") || !arguments.contains(" /to ")) {
                    throw new KroissantException("This party is half-baked! I need '/from' and '/to' times.");
                }
                String[] eventArgs = arguments.split(" /from | /to ");
                Task newEvent = new Event(eventArgs[0], eventArgs[1], eventArgs[2]);
                tasks.add(newEvent);
                storage.save(tasks.getTasks());
                return "Okay, I've folded this into the dough:\n  " + newEvent
                        + "\nNow you have " + tasks.size() + " pastries baking in the list!";

            case DELETE:
                validateArgs(arguments);
                int deleteIndex = parseIndex(arguments);
                Task removedTask = tasks.delete(deleteIndex);
                storage.save(tasks.getTasks());
                return "Noted. This one out the oven:\n  " + removedTask
                        + "\nNow you have " + tasks.size() + " pastries baking in the list!";

            case FIND:
                if (arguments.isEmpty()) {
                    throw new KroissantException("What are you looking for! Give me a keyword to search!");
                }
                java.util.ArrayList<Task> matchingTasks = tasks.find(arguments);
                if (matchingTasks.isEmpty()) {
                    return "No matching pastries found in the oven!";
                } else {
                    StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                    for (int i = 0; i < matchingTasks.size(); i++) {
                        sb.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
                    }
                    return sb.toString();
                }

            case BYE:
                return "Time to roll out!\nLet's get this bread again soon!";

            default:
                throw new KroissantException("I don't have a recipe for that! Is it a secret menu item?");
        }
    }
}
