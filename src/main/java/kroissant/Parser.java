package kroissant;

/**
 * Parses and executes user commands for the task management application.
 */
public class Parser {

    private static final String CMD_BYE = "BYE";
    private static final String CMD_LIST = "LIST";
    private static final String CMD_MARK = "MARK";
    private static final String CMD_UNMARK = "UNMARK";
    private static final String CMD_TODO = "TODO";
    private static final String CMD_DEADLINE = "DEADLINE";
    private static final String CMD_EVENT = "EVENT";
    private static final String CMD_DELETE = "DELETE";
    private static final String CMD_FIND = "FIND";

    private static final String ARG_BY = " /by ";
    private static final String ARG_FROM = " /from ";
    private static final String ARG_TO = " /to ";
    private static final String ARG_FROM_OR_TO = " /from | /to ";

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

        // Check for exit command
        if (input.equalsIgnoreCase(CMD_BYE)) {
            return true;
        }

        // Parse command and arguments
        String[] parts = input.split(" ", 2);
        String commandStr = parts[0].toUpperCase();
        String arguments = (parts.length > 1) ? parts[1] : "";

        // Convert String to Command enum
        Command command;
        try {
            command = Command.valueOf(commandStr);
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
                markTask(arguments, tasks, storage, ui);
                break;

            case UNMARK:
                unmarkTask(arguments, tasks, storage, ui);
                break;

            case TODO:
                addTodo(arguments, tasks, storage, ui);
                break;

            case DEADLINE:
                addDeadline(arguments, tasks, storage, ui);
                break;

            case EVENT:
                addEvent(arguments, tasks, storage, ui);
                break;

            case DELETE:
                deleteTask(arguments, tasks, storage, ui);
                break;

            case FIND:
                findTask(arguments, tasks, ui);
                break;

            case BYE:
                // This case is handled in parseAndExecute
                break;

            default:
                // Should not reach here as invalid commands are caught earlier
                break;
        }
    }

    private static void markTask(String arguments, TaskList tasks, Storage storage, Ui ui) throws KroissantException {
        validateArgs(arguments);
        int markIndex = parseIndex(arguments);
        tasks.mark(markIndex);
        ui.showTaskMarked(tasks.get(markIndex));
        storage.save(tasks.getTasks());
    }

    private static void unmarkTask(String arguments, TaskList tasks, Storage storage, Ui ui) throws KroissantException {
        validateArgs(arguments);
        int unmarkIndex = parseIndex(arguments);
        tasks.unmark(unmarkIndex);
        ui.showTaskUnmarked(tasks.get(unmarkIndex));
        storage.save(tasks.getTasks());
    }

    private static void addTodo(String arguments, TaskList tasks, Storage storage, Ui ui) throws KroissantException {
        if (arguments.isEmpty()) {
            throw new KroissantException("You can't bake air! The description is empty.");
        }
        Task newTodo = new Todo(arguments);
        tasks.add(newTodo);
        ui.showTaskAdded(newTodo, tasks.size());
        storage.save(tasks.getTasks());
    }

    private static void addDeadline(String arguments, TaskList tasks, Storage storage, Ui ui)
            throws KroissantException {
        if (!arguments.contains(ARG_BY)) {
            throw new KroissantException("Don't let it burn! Please set a '/by' timer.");
        }
        String[] deadlineArgs = arguments.split(ARG_BY, 2);
        try {
            Task newDeadline = new Deadline(deadlineArgs[0], deadlineArgs[1]);
            tasks.add(newDeadline);
            ui.showTaskAdded(newDeadline, tasks.size());
            storage.save(tasks.getTasks());
        } catch (IllegalArgumentException e) {
            throw new KroissantException(e.getMessage());
        }
    }

    private static void addEvent(String arguments, TaskList tasks, Storage storage, Ui ui) throws KroissantException {
        if (!arguments.contains(ARG_FROM) || !arguments.contains(ARG_TO)) {
            throw new KroissantException("This party is half-baked! I need '/from' and '/to' times.");
        }
        String[] eventArgs = arguments.split(ARG_FROM_OR_TO);
        Task newEvent = new Event(eventArgs[0], eventArgs[1], eventArgs[2]);
        tasks.add(newEvent);
        ui.showTaskAdded(newEvent, tasks.size());
        storage.save(tasks.getTasks());
    }

    private static void deleteTask(String arguments, TaskList tasks, Storage storage, Ui ui) throws KroissantException {
        validateArgs(arguments);
        int deleteIndex = parseIndex(arguments);
        Task removedTask = tasks.delete(deleteIndex);
        ui.showTaskDeleted(removedTask, tasks.size());
        storage.save(tasks.getTasks());
    }

    private static void findTask(String arguments, TaskList tasks, Ui ui) throws KroissantException {
        if (arguments.isEmpty()) {
            throw new KroissantException("What are you looking for? Give me a keyword to search!");
        }
        ui.showSearchResults(tasks.find(arguments));
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
        if (input.equalsIgnoreCase(CMD_BYE)) {
            return "Time to roll out!\nLet's get this bread again soon!";
        }

        // Parse command and arguments
        String[] parts = input.split(" ", 2);
        String commandStr = parts[0].toUpperCase();
        String arguments = (parts.length > 1) ? parts[1] : "";

        // Convert String to Command enum
        Command command;
        try {
            command = Command.valueOf(commandStr);
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
                return getListResponse(tasks);

            case MARK:
                return markTaskResponse(arguments, tasks, storage);

            case UNMARK:
                return unmarkTaskResponse(arguments, tasks, storage);

            case TODO:
                return addTodoResponse(arguments, tasks, storage);

            case DEADLINE:
                return addDeadlineResponse(arguments, tasks, storage);

            case EVENT:
                return addEventResponse(arguments, tasks, storage);

            case DELETE:
                return deleteTaskResponse(arguments, tasks, storage);

            case FIND:
                return findTaskResponse(arguments, tasks);

            case BYE:
                return "Time to roll out!\nLet's get this bread again soon!";

            default:
                throw new KroissantException("I don't have a recipe for that! Is it a secret menu item?");
        }
    }

    private static String getListResponse(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "The oven is empty! Time to get kneading.";
        } else {
            StringBuilder sb = new StringBuilder("Here are the pastries rising in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
            return sb.toString();
        }
    }

    private static String markTaskResponse(String arguments, TaskList tasks, Storage storage)
            throws KroissantException {
        validateArgs(arguments);
        int markIndex = parseIndex(arguments);
        tasks.mark(markIndex);
        storage.save(tasks.getTasks());
        return "Chef's kiss! This task is golden brown and fully baked:\n  " + tasks.get(markIndex);
    }

    private static String unmarkTaskResponse(String arguments, TaskList tasks, Storage storage)
            throws KroissantException {
        validateArgs(arguments);
        int unmarkIndex = parseIndex(arguments);
        tasks.unmark(unmarkIndex);
        storage.save(tasks.getTasks());
        return "Oops, looks a bit doughy inside. Putting it back in the oven:\n  " + tasks.get(unmarkIndex);
    }

    private static String addTodoResponse(String arguments, TaskList tasks, Storage storage) throws KroissantException {
        if (arguments.isEmpty()) {
            throw new KroissantException("You can't bake air! The description is empty.");
        }
        Task newTodo = new Todo(arguments);
        tasks.add(newTodo);
        storage.save(tasks.getTasks());
        return getTaskAddedMessage(newTodo, tasks.size());
    }

    private static String addDeadlineResponse(String arguments, TaskList tasks, Storage storage)
            throws KroissantException {
        if (!arguments.contains(ARG_BY)) {
            throw new KroissantException("Don't let it burn! Please set a '/by' timer.");
        }
        String[] deadlineArgs = arguments.split(ARG_BY, 2);
        try {
            Task newDeadline = new Deadline(deadlineArgs[0], deadlineArgs[1]);
            tasks.add(newDeadline);
            storage.save(tasks.getTasks());
            return getTaskAddedMessage(newDeadline, tasks.size());
        } catch (IllegalArgumentException e) {
            throw new KroissantException(e.getMessage());
        }
    }

    private static String addEventResponse(String arguments, TaskList tasks, Storage storage)
            throws KroissantException {
        if (!arguments.contains(ARG_FROM) || !arguments.contains(ARG_TO)) {
            throw new KroissantException("This party is half-baked! I need '/from' and '/to' times.");
        }
        String[] eventArgs = arguments.split(ARG_FROM_OR_TO);
        Task newEvent = new Event(eventArgs[0], eventArgs[1], eventArgs[2]);
        tasks.add(newEvent);
        storage.save(tasks.getTasks());
        return getTaskAddedMessage(newEvent, tasks.size());
    }

    private static String deleteTaskResponse(String arguments, TaskList tasks, Storage storage)
            throws KroissantException {
        validateArgs(arguments);
        int deleteIndex = parseIndex(arguments);
        Task removedTask = tasks.delete(deleteIndex);
        storage.save(tasks.getTasks());
        return "Noted. This one out the oven:\n  " + removedTask
                + "\nNow you have " + tasks.size() + " pastries baking in the list!";
    }

    private static String findTaskResponse(String arguments, TaskList tasks) throws KroissantException {
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
    }

    private static String getTaskAddedMessage(Task task, int size) {
        return "Okay, I've folded this into the dough:\n  " + task
                + "\nNow you have " + size + " pastries baking in the list!";
    }
}
