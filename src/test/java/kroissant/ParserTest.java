package kroissant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ParserTest {
    @TempDir
    Path tempDir;

    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        ui = new Ui();
        storage = new Storage(tempDir.resolve("test_tasks.ser").toString());
    }

    @Test
    public void testParseByeCommand() throws KroissantException {
        boolean isExit = Parser.parseAndExecute("bye", taskList, ui, storage);
        assertTrue(isExit);
    }

    @Test
    public void testParseInvalidCommand() {
        assertThrows(KroissantException.class, () -> Parser.parseAndExecute("invalid", taskList, ui, storage));
    }

    @Test
    public void testParseTodoCommand() throws KroissantException {
        boolean isExit = Parser.parseAndExecute("todo buy milk", taskList, ui, storage);
        assertFalse(isExit);
        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Todo);
    }

    @Test
    public void testParseTodoWithoutDescription() {
        assertThrows(KroissantException.class, () -> Parser.parseAndExecute("todo", taskList, ui, storage));
    }

    @Test
    public void testParseDeadlineCommand() throws KroissantException {
        boolean isExit = Parser.parseAndExecute("deadline submit report /by 2/12/2024 1800",
                taskList, ui, storage);
        assertFalse(isExit);
        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Deadline);
    }

    @Test
    public void testParseDeadlineWithoutBy() {
        assertThrows(KroissantException.class, () -> Parser.parseAndExecute(
                "deadline submit report", taskList, ui, storage));
    }

    @Test
    public void testParseEventCommand() throws KroissantException {
        boolean isExit = Parser.parseAndExecute("event meeting /from 2pm /to 4pm",
                taskList, ui, storage);
        assertFalse(isExit);
        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Event);
    }

    @Test
    public void testParseEventWithoutFromTo() {
        assertThrows(KroissantException.class, () -> Parser.parseAndExecute("event meeting", taskList, ui, storage));
    }

    @Test
    public void testParseMarkCommand() throws KroissantException {
        taskList.add(new Todo("test task"));
        Parser.parseAndExecute("mark 1", taskList, ui, storage);
        assertEquals("X", taskList.get(0).getStatusIcon());
    }

    @Test
    public void testParseMarkWithoutIndex() {
        taskList.add(new Todo("test task"));
        assertThrows(KroissantException.class, () -> Parser.parseAndExecute("mark", taskList, ui, storage));
    }

    @Test
    public void testParseMarkWithInvalidIndex() {
        taskList.add(new Todo("test task"));
        assertThrows(KroissantException.class, () -> Parser.parseAndExecute("mark abc", taskList, ui, storage));
    }

    @Test
    public void testParseUnmarkCommand() throws KroissantException {
        taskList.add(new Todo("test task"));
        taskList.mark(0);
        Parser.parseAndExecute("unmark 1", taskList, ui, storage);
        assertEquals(" ", taskList.get(0).getStatusIcon());
    }

    @Test
    public void testParseDeleteCommand() throws KroissantException {
        taskList.add(new Todo("test task"));
        Parser.parseAndExecute("delete 1", taskList, ui, storage);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testParseDeleteWithoutIndex() {
        taskList.add(new Todo("test task"));
        assertThrows(KroissantException.class, () -> Parser.parseAndExecute("delete", taskList, ui, storage));
    }

    @Test
    public void testParseListCommand() throws KroissantException {
        taskList.add(new Todo("task 1"));
        taskList.add(new Todo("task 2"));
        boolean isExit = Parser.parseAndExecute("list", taskList, ui, storage);
        assertFalse(isExit);
        assertEquals(2, taskList.size());
    }

    @Test
    public void testCaseInsensitiveCommands() throws KroissantException {
        Parser.parseAndExecute("TODO buy milk", taskList, ui, storage);
        Parser.parseAndExecute("List", taskList, ui, storage);
        assertEquals(1, taskList.size());
    }
}
