import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final String filePath;

    public TaskManager(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(List<Task> tasks) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(tasks);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Task> loadTasks() {
        File file = new File(filePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            return (ArrayList<Task>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file (starting with empty list): " + e.getMessage());
            return new ArrayList<>();
        }
    }
}