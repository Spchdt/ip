package kroissant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    public void testTodoMarkDone() {
        Todo todo = new Todo("Buy groceries");
        todo.markDone();
        assertEquals("[T][X] Buy groceries", todo.toString());
        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void testTodoMarkUndone() {
        Todo todo = new Todo("Buy groceries");
        todo.markDone();
        todo.markUndone();
        assertEquals("[T][ ] Buy groceries", todo.toString());
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void testTodoWithEmptyDescription() {
        Todo todo = new Todo("");
        assertEquals("[T][ ] ", todo.toString());
    }

    @Test
    public void testTodoWithSpecialCharacters() {
        Todo todo = new Todo("Buy milk & bread @ store");
        assertEquals("[T][ ] Buy milk & bread @ store", todo.toString());
    }
}
