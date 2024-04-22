import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class QuadraticPerfectHashTable {
    private UniversalHashFunction hashFunction;
    public List<String>[] table;

    int[][] hashTables;
    String[] dictionary;
    int sizeOfTable;
    int setLength;

    int count;
    boolean maxCollision = false;
    int row;

    int loadFactor;


    public QuadraticPerfectHashTable(int setSize, String[] dic) {
        sizeOfTable = setSize * setSize;
        setLength = setSize;
        dictionary = dic;
        hashFunction = new UniversalHashFunction();
        table = new ArrayList[sizeOfTable];

        row = (int) (Math.log(sizeOfTable) / Math.log(2)) + 1;
        hashTables = hashFunction.initRandomHash(row);
        count = 0;

    }

    private void initializeTable(String key) {

        try {
            List<String>[] newTable;
            System.out.println(" count is" + count + "   length" + setLength);
            if (count > setLength) {
                sizeOfTable = count * count;
                setLength = count;
                newTable = new List[sizeOfTable];
            } else {
                newTable = new List[sizeOfTable];
            }


            row = (int) (Math.log(sizeOfTable) / Math.log(2)) + 1;
            hashTables = hashFunction.initRandomHash(row);

            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    int index = hashFunction.generateHash(table[i].get(0), hashTables, sizeOfTable);
                    System.out.println("the index is " + index);
                    if (newTable[index] != null) {
                        System.out.println(" collision ");
                        initializeTable(key);
                        return;
                    }
                    System.out.print(" " + table[i].get(0) + " ");
                    newTable[index] = new ArrayList<>();
                    newTable[index].add(table[i].get(0));
                }
            }
            int index = hashFunction.generateHash(key, hashTables, sizeOfTable);
            if (newTable[index] != null) {
                System.out.println(" collision ");
                initializeTable(key);
                return;
            }
            newTable[index] = new ArrayList<>();
            newTable[index].add(key);
            table = new List[sizeOfTable];
            table = newTable;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean insert(String key) {

        count++;
        int index = hashFunction.generateHash(key, hashTables, sizeOfTable);
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
                initializeTable(key);
            }
        }


        return true;
    }

    public boolean delete(String key) {


        int index = hashFunction.generateHash(key, hashTables, sizeOfTable);
        List<String> bin = table[index];
        if (bin == null) {
            System.out.println(" it is not found ");
        } else {
            if (bin.get(0).equals(key)) {
                System.out.println(" it is  exist");
                table[index] = null;
                return true;
            } else {
                System.out.println(" it is not found ");
                return false;
            }
        }

        return false;
    }

    public boolean search(String key) {
        count++;


        int index = hashFunction.generateHash(key, hashTables, sizeOfTable);
        List<String> bin = table[index];
        if (bin == null) {
            System.out.println(" it is not found ");

        } else {
            if (bin.get(0).equals(key)) {
                System.out.println(" it is  exist");
                return true;
            } else {
                System.out.println(" it is not found ");
                return false;
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
                if (insert(key)) {
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
                if (delete(key)) {
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