import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class EnglishDictionary {
    private final QuadraticPerfectHashTable perfectHashTable;

    public EnglishDictionary(String backendType) {
        // Initialize the dictionary based on the backend type
        if (backendType.equals("QuadraticPerfectHashTable")) {
            perfectHashTable = new QuadraticPerfectHashTable(2, new String[0]);
        } else {
            throw new IllegalArgumentException("Invalid backend type");
        }
    }

    public void insert(String word) {
        if (perfectHashTable.insert(word)) {
            System.out.println("Word inserted successfully: " + word);
        } else {
            System.out.println("Word already exists: " + word);
        }
        for (List s : perfectHashTable.table) {
            if (s != null) {
                System.out.print(s + "  ");
            }
        }
        System.out.println(Arrays.deepToString(perfectHashTable.table));
    }

    public void delete(String word) {
        if (perfectHashTable.delete(word)) {
            System.out.println("Word deleted successfully: " + word);
        } else {
            System.out.println("Word not found: " + word);
        }
    }

    public boolean search(String word) {
        if (perfectHashTable.search(word)) {
            System.out.println("Word found: " + word);
            return true;
        } else {
            System.out.println("Word not found: " + word);
            return false;
        }
    }

    public void batchInsert(String filePath) {
        perfectHashTable.batchInsert(filePath);
        System.out.println(Arrays.deepToString(perfectHashTable.table));
    }

    public void batchDelete(String filePath) {
        perfectHashTable.batchDelete(filePath);
        System.out.println(Arrays.deepToString(perfectHashTable.table));
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            int count = 0;
//            while ((line = reader.readLine()) != null) {
//                if (perfectHashTable.delete(line)) {
//                    count++;
//                }
//            }
//            System.out.println("Batch delete completed. " + count + " words deleted.");
//        } catch (IOException e) {
//            System.out.println("Error reading file: " + e.getMessage());
//        }
    }
}