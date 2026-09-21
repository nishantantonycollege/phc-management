import java.io.*;
import java.util.ArrayList;

// Reads and writes records in simple CSV text files inside the "data" folder.
public class FileHelper {

    private static final String FOLDER = "data";

    // Read all records from a file. Each record is a String array.
    public static ArrayList<String[]> readFile(String fileName) {
        ArrayList<String[]> rows = new ArrayList<>();
        File file = new File(FOLDER, fileName);

        if (!file.exists()) {
            return rows; // no file yet, so no records
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    rows.add(line.split(",", -1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    // Write all records to a file (replaces old content).
    public static void writeFile(String fileName, ArrayList<String[]> rows) {
        File folder = new File(FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(folder, fileName)))) {
            for (String[] row : rows) {
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Count how many records are in a file.
    public static int countRecords(String fileName) {
        return readFile(fileName).size();
    }
}
