import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class EnglishDictionary {
    private final QuadraticPerfectHashTable perfectHashTable;

    public EnglishDictionary(String backendType) {
        // Initialize the dictionary based on the backend type
        if (backendType.equals("QuadraticPerfectHashTable")) {
            perfectHashTable = new QuadraticPerfectHashTable(3, new String[0]);
        } else {
            throw new IllegalArgumentException("Invalid backend type");
        }
    }

    public void insert(String word) {
        if (perfectHashTable.insert(word,0)) {
            System.out.println("Word inserted successfully: " + word);
        } else {
            System.out.println("Word already exists: " + word);
        }
        System.out.println(Arrays.deepToString(perfectHashTable.table));
    }

    public void delete(String word) {
        if (perfectHashTable.delete(word,0)) {
            System.out.println("Word deleted successfully: " + word);
        } else {
            System.out.println("Word not found: " + word);
        }
    }

    public boolean search(String word) {
        if (perfectHashTable.search(word,0)) {
            System.out.println("Word found: " + word);
            return true;
        } else {
            System.out.println("Word not found: " + word);
            return false;
        }
    }

    public void batchInsert(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (perfectHashTable.insert(line,0)) {
                    count++;
                }
            }
            System.out.println("Batch insert completed. " + count + " words inserted.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void batchDelete(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (perfectHashTable.delete(line,0)) {
                    count++;
                }
            }
            System.out.println("Batch delete completed. " + count + " words deleted.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}