package kroissant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskListTest {
    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        task1 = new Todo("Buy groceries");
        task2 = new Todo("Read book");
        task3 = new Todo("Exercise");
    }

    @Test
    public void testAddTask() throws KroissantException {
        taskList.add(task1);
        assertEquals(1, taskList.size());
        assertEquals(task1, taskList.get(0));
    }

    @Test
    public void testAddMultipleTasks() throws KroissantException {
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        assertEquals(3, taskList.size());
        assertEquals(task1, taskList.get(0));
        assertEquals(task2, taskList.get(1));
        assertEquals(task3, taskList.get(2));
    }

    @Test
    public void testDeleteTask() throws KroissantException {
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        Task deleted = taskList.delete(1);
        assertEquals(task2, deleted);
        assertEquals(2, taskList.size());
        assertEquals(task1, taskList.get(0));
        assertEquals(task3, taskList.get(1));
    }

    @Test
    public void testDeleteInvalidIndex() throws KroissantException {
        taskList.add(task1);
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.delete(5));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.delete(-1));
    }

    @Test
    public void testMarkTask() throws KroissantException {
        taskList.add(task1);
        taskList.mark(0);
        assertEquals("X", taskList.get(0).getStatusIcon());
    }

    @Test
    public void testUnmarkTask() throws KroissantException {
        taskList.add(task1);
        taskList.mark(0);
        taskList.unmark(0);
        assertEquals(" ", taskList.get(0).getStatusIcon());
    }

    @Test
    public void testMarkInvalidIndex() throws KroissantException {
        taskList.add(task1);
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.mark(5));
    }

    @Test
    public void testIsEmpty() throws KroissantException {
        assertTrue(taskList.isEmpty());
        taskList.add(task1);
        assertFalse(taskList.isEmpty());
    }

    @Test
    public void testSize() throws KroissantException {
        assertEquals(0, taskList.size());
        taskList.add(task1);
        assertEquals(1, taskList.size());
        taskList.add(task2);
        assertEquals(2, taskList.size());
        taskList.delete(0);
        assertEquals(1, taskList.size());
    }

    @Test
    public void testGetTasks() throws KroissantException {
        taskList.add(task1);
        taskList.add(task2);
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(2, tasks.size());
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));
    }

    @Test
    public void testConstructorWithExistingTasks() {
        ArrayList<Task> existingTasks = new ArrayList<>();
        existingTasks.add(task1);
        existingTasks.add(task2);

        TaskList newTaskList = new TaskList(existingTasks);
        assertEquals(2, newTaskList.size());
        assertEquals(task1, newTaskList.get(0));
        assertEquals(task2, newTaskList.get(1));
    }

    @Test
    public void testFindWithMatchingKeyword() throws KroissantException {
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        ArrayList<Task> results = taskList.find("book");
        assertEquals(1, results.size());
        assertEquals(task2, results.get(0));
    }

    @Test
    public void testFindCaseInsensitive() throws KroissantException {
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        ArrayList<Task> results = taskList.find("BOOK");
        assertEquals(1, results.size());
        assertEquals(task2, results.get(0));
    }

    @Test
    public void testFindMultipleMatches() throws KroissantException {
        Task task4 = new Deadline("return book", "31/12/2024 1800");
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task4);

        ArrayList<Task> results = taskList.find("book");
        assertEquals(2, results.size());
        assertTrue(results.contains(task2));
        assertTrue(results.contains(task4));
    }

    @Test
    public void testFindNoMatches() throws KroissantException {
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        ArrayList<Task> results = taskList.find("homework");
        assertEquals(0, results.size());
    }

    @Test
    public void testFindEmptyList() {
        ArrayList<Task> results = taskList.find("book");
        assertEquals(0, results.size());
    }
}
