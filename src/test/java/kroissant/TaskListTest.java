package kroissant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
    public void testAddTask() {
        taskList.add(task1);
        assertEquals(1, taskList.size());
        assertEquals(task1, taskList.get(0));
    }

    @Test
    public void testAddMultipleTasks() {
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        assertEquals(3, taskList.size());
        assertEquals(task1, taskList.get(0));
        assertEquals(task2, taskList.get(1));
        assertEquals(task3, taskList.get(2));
    }

    @Test
    public void testDeleteTask() {
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
    public void testDeleteInvalidIndex() {
        taskList.add(task1);
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.delete(5));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.delete(-1));
    }

    @Test
    public void testMarkTask() {
        taskList.add(task1);
        taskList.mark(0);
        assertTrue(taskList.get(0).getStatusIcon().equals("X"));
    }

    @Test
    public void testUnmarkTask() {
        taskList.add(task1);
        taskList.mark(0);
        taskList.unmark(0);
        assertTrue(taskList.get(0).getStatusIcon().equals(" "));
    }

    @Test
    public void testMarkInvalidIndex() {
        taskList.add(task1);
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.mark(5));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(taskList.isEmpty());
        taskList.add(task1);
        assertFalse(taskList.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, taskList.size());
        taskList.add(task1);
        assertEquals(1, taskList.size());
        taskList.add(task2);
        assertEquals(2, taskList.size());
        taskList.delete(0);
        assertEquals(1, taskList.size());
    }

    @Test
    public void testGetTasks() {
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
}
