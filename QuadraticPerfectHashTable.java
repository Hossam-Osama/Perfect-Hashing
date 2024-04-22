
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class QuadraticPerfectHashTable {
    private UniversalHashFunction hashFunction;
    public List<String>[] table;

    ArrayList<int[][]> hashTables = new ArrayList<>();
    String[] dictionary;
    int sizeOfTable;

    int count;
    boolean maxCollision = false;
    int row;


    public QuadraticPerfectHashTable(int setSize, String[] dic) {
        sizeOfTable = setSize * setSize;
        dictionary = dic;
        hashFunction = new UniversalHashFunction();
        table = new ArrayList[sizeOfTable];

        row = (int) (Math.log(sizeOfTable) / Math.log(2)) + 1;
        hashTables.add(hashFunction.initRandomHash(row));
        count = 0;

    }

//    private void initializeTable(int setSize) {
//        for (int i = 0; i < setSize; i++) {
//            String element = dictionary[i];
//            int hashValue = hashFunction.hash(element);
//
//            if (table[hashValue] == null) {
//                table[hashValue] = new ArrayList<>();
//            } else if (table[hashValue].contains(element)) {
//                count++;
//                System.out.println(element);
//            } else {
//                // Collision occurred, try a new hash function
//                n = true;
//                count = 0;
//                hashFunction = new UniversalHashFunction(table.length);
//                for (int m = 0; m < table.length; m++) {
//                    table[m] = null;
//                }
//                initializeTable(setSize); // Recursive call to reinitialize the table
//                return;
//            }
//            table[hashValue].add(element);
//        }
//    }

    public boolean insert(String key, int numberOfCollison) {

        if (numberOfCollison > 10) {
            System.out.println("thats many hashs that enough" + maxCollision);
            return false;
        }

        int index = hashFunction.generateHash(key, hashTables.get(numberOfCollison), sizeOfTable);
        List<String> bin = table[index];
        if (bin == null) {
            bin = new ArrayList<>();
            bin.add(key);
            table[index] = bin;

        } else {
            if (bin.get(0).equals(key)) {
                System.out.println(" it is already exist");
                return false;
            } else {
                System.out.println("there is collision");
                hashTables.add(hashFunction.initRandomHash(row));
                if (insert(key, numberOfCollison + 1)) {
                    return true;
                } else {
                    return false;
                }
            }
        }


        return true;
    }

    public boolean delete(String key, int numberOfCollison) {
        if (numberOfCollison > 10) {
            System.out.println("thats many hashs that enough");
            return false;
        }

        int index = hashFunction.generateHash(key, hashTables.get(numberOfCollison), sizeOfTable);
        List<String> bin = table[index];
        if (bin == null) {
            bin = new ArrayList<>();
            table[index] = bin;
        } else {
            if (bin.get(0).equals(key)) {
                System.out.println(" it is  exist");
                table[index] = null;
                return true;
            } else {
                if (insert(key, numberOfCollison++)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean search(String key, int numberOfCollison) {
        if (numberOfCollison > 10) {
            System.out.println("thats many hashs that enough");
            return false;
        }

        int index = hashFunction.generateHash(key, hashTables.get(numberOfCollison), sizeOfTable);
        List<String> bin = table[index];
        if (bin == null) {
            bin = new ArrayList<>();
            table[index] = bin;
        } else {
            if (bin.get(0).equals(key)) {
                System.out.println(" it is  exist");
                return true;
            } else {
                if (insert(key, numberOfCollison++)) {
                    return true;
                } else {
                    return false;
                }
            }
        }


        return false; // Key not found
    }

    public void batchInsert(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int insertedCount = 0;
            int alreadyExistsCount = 0;

            while ((line = reader.readLine()) != null) {
                String key = line.trim();
                if (insert(key, 0)) {
                    insertedCount++;
                } else {
                    alreadyExistsCount++;
                }
            }

            System.out.println("Batch Insert Summary:");
            System.out.println("Inserted: " + insertedCount + " words");
            System.out.println("Already Exists: " + alreadyExistsCount + " words");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void batchDelete(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int deletedCount = 0;
            int nonExistingCount = 0;

            while ((line = reader.readLine()) != null) {
                String key = line.trim();
                if (delete(key, 0)) {
                    deletedCount++;
                } else {
                    nonExistingCount++;
                }
            }

            System.out.println("Batch Delete Summary:");
            System.out.println("Deleted: " + deletedCount + " words");
            System.out.println("Non-existing: " + nonExistingCount + " words");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

}