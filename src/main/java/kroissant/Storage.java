package kroissant;

import java.io.*;
import java.util.ArrayList;

/**
 * Handles loading and saving of tasks to a file using serialization.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * Returns an empty list if the file does not exist.
     *
     * @return ArrayList of tasks loaded from the file.
     * @throws KroissantException If there is an error reading the file.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Task> load() throws KroissantException {
        File file = new File(filePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {

            return (ArrayList<Task>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new KroissantException("Error loading file: " + e.getMessage());
        }
    }

    /**
     * Saves the given list of tasks to the storage file.
     *
     * @param tasks ArrayList of tasks to save.
     * @throws KroissantException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws KroissantException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(tasks);

        } catch (IOException e) {
            throw new KroissantException("Error saving file: " + e.getMessage());
        }
    }
}
