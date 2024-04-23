import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class QuadraticPerfectHashTable implements IPerfectHashTable {
    private UniversalHashFunction hashFunction;
    public List<String>[] table;

    int[][] hashTables;

    int sizeOfTable;
    int setLength;

    int count;
    //    boolean maxCollision = false;
    int row;

    int totalInserted;
    int totalDeleted;
    int failedDeleted;
    int failedInserted;
    int numberOfResh;


    public QuadraticPerfectHashTable(int setSize, String[] dic) {
        sizeOfTable = setSize * setSize;
        setLength = setSize;

        hashFunction = new UniversalHashFunction();
        table = new ArrayList[sizeOfTable];

        row = (int) (Math.log(sizeOfTable) / Math.log(2)) + 1;


        hashTables = hashFunction.initRandomHash(row);
        count = 0;
        totalInserted = 0;
        totalDeleted = 0;
        failedDeleted = 0;
        failedInserted = 0;
        numberOfResh = 0;

    }

    private void initializeTable(ArrayList<String> key) {

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
            numberOfResh++;

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

            for (int k = 0; k < key.size(); k++) {

                int index = hashFunction.generateHash(key.get(k), hashTables, sizeOfTable);
                if (newTable[index] != null) {
                    System.out.println(" collision ");
                    initializeTable(key);
                    return;
                }
                newTable[index] = new ArrayList<>();
                newTable[index].add(key.get(k));
            }
            table = new List[sizeOfTable];
            table = newTable;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean insert(String key) {

        totalInserted++;
        int index = hashFunction.generateHash(key, hashTables, sizeOfTable);
        List<String> bin = table[index];
        if (bin == null) {
            bin = new ArrayList<>();
            bin.add(key);
            table[index] = bin;

        } else {
            if (bin.get(0).equals(key)) {
                System.out.println(" it is already exist");
                failedInserted++;
                return false;
            } else {
                count++;
                System.out.println("there is collision");
                ArrayList<String> ke = new ArrayList<>();
                ke.add(key);
                initializeTable(ke);
            }
        }


        return true;
    }

    public boolean delete(String key) {


        totalDeleted++;
        int index = hashFunction.generateHash(key, hashTables, sizeOfTable);
        List<String> bin = table[index];
        if (bin == null) {
            System.out.println(" it is not found ");
            failedDeleted++;

        } else {
            if (bin.get(0).equals(key)) {
                System.out.println(" it is  exist");
                table[index] = null;
                count--;
                return true;
            } else {
                System.out.println(" it is not found ");
                failedDeleted++;
                return false;
            }
        }

        return false;
    }

    public boolean search(String key) {

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

            ArrayList<String> keys = new ArrayList<>();

            int inserted = count;
            while ((line = reader.readLine()) != null) {
                keys.add(line);
                count++;
            }
            initializeTable(keys);
            System.out.println("Batch insert completed. " +( count - inserted)+ " words inserted.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void batchDelete(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int deletedCount = count;
//            int nonExistingCount = 0;

            while ((line = reader.readLine()) != null) {
                delete(line);
            }

            System.out.println("Batch Delete Summary:");

            System.out.println("  count is " + count);
            System.out.println(Arrays.deepToString(table));
            System.out.println("Deleted: " + (deletedCount - count) + " words");
//            System.out.println("Non-existing: " + nonExistingCount + " words");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public int getTotalInserted()
    {
        
    return totalInserted;
    }
    public int getTotalDeleted()
    {
        

        return totalDeleted;
    }
    public int getFailedDeleted()
    {return failedDeleted;
    }
    public int getfailedInserted()
    {
        

        return failedInserted;
    }
    public int getnumberOfResh()
    {
        
     return numberOfResh;
    }
    public int getnumberOfelement()
    {     
     return count;
    }

}
