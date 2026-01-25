package kroissant;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public void save(ArrayList<Task> tasks) throws KroissantException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(tasks);

        } catch (IOException e) {
            throw new KroissantException("Error saving file: " + e.getMessage());
        }
    }
}
