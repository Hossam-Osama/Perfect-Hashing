
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

 class UniversalHashFunction {
    private int a;
    private int b;
    private int p;
    private int m;

    public UniversalHashFunction(int m) {
        this.m = m;

        Random random = new Random();
        this.p = BigInteger.probablePrime(31, random).intValue();
        this.a = random.nextInt(p - 1) + 1;
        this.b = random.nextInt(p);
    }

    public int hash(String key) {
        int hashCode = key.hashCode();
    int hashValue = (((a * hashCode + b) % p) % m + m) % m;
    System.out.println(hashValue);
    return hashValue;
    }
}

public class QuadraticPerfectHashTable {
    private UniversalHashFunction hashFunction;
    private List<String>[] table;
    String [] dictionary;
    int count ;
    boolean n=false;

    
    public QuadraticPerfectHashTable(int setSize,String [] dic ) {
        int tableSize = setSize * setSize;
        dictionary=dic;
        hashFunction = new UniversalHashFunction(tableSize);
        table = new ArrayList[tableSize];
         
        if(dictionary.length>=1){
        initializeTable(setSize);}
    }
    private void initializeTable(int setSize) {
        for (int i = 0; i < setSize; i++) {
            String element = dictionary[i];
            int hashValue = hashFunction.hash(element);
        
            if (table[hashValue] == null) {
                table[hashValue] = new ArrayList<>();
            } else if(table[hashValue].contains(element)) {
               count ++;
               System.out.println(element);
            }
            else{
                // Collision occurred, try a new hash function
                n=true;
                count =0;
                hashFunction = new UniversalHashFunction(table.length);
                for (int m = 0; m < table.length; m++) {
                    table[m] = null;
                }
                initializeTable(setSize); // Recursive call to reinitialize the table
                return;
            }
            table[hashValue].add(element);
        }
    }

        public boolean insert(String key) {
            int hashValue = hashFunction.hash(key);
            List<String> bin = table[hashValue];
            if (bin == null) {
                bin = new ArrayList<>();
                table[hashValue] = bin;
            }

            if (bin.contains(key)) {
                count++;
                return false; // Key already exists
            }

            bin.add(key);
            return true;
        }

        public boolean delete(String key) {
            int hashValue = hashFunction.hash(key);
            List<String> bin = table[hashValue];
            if (bin != null && bin.remove(key)) {
                return true; // Key found and deleted
            }

            return false; // Key not found
        }

        public boolean search(String key) {
            int hashValue = hashFunction.hash(key);
            List<String> bin = table[hashValue];
            if (bin != null && bin.contains(key)) {
                return true; // Key found
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
class EnglishDictionary {
    private final QuadraticPerfectHashTable perfectHashTable;

    public EnglishDictionary(String backendType) {
        // Initialize the dictionary based on the backend type
        if (backendType.equals("QuadraticPerfectHashTable")) {
            perfectHashTable = new QuadraticPerfectHashTable(10, new String[0]);
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (perfectHashTable.insert(line)) {
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
                if (perfectHashTable.delete(line)) {
                    count++;
                }
            }
            System.out.println("Batch delete completed. " + count + " words deleted.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

 class Main {
    public static void main(String[] args) {
        System.out.print("Enter the type of backend perfect hashing (e.g., QuadraticPerfectHashTable): ");
        Scanner scanner = new Scanner(System.in);
        String backendType = scanner.nextLine();

        System.out.println("Initializing dictionary...");
        EnglishDictionary dictionary = new EnglishDictionary(backendType);
        System.out.println("Dictionary initialized.");

        while (true) {
            System.out.println("\n----- Menu -----");
            System.out.println("1. Insert a word");
            System.out.println("2. Delete a word");
            System.out.println("3. Search for a word");
            System.out.println("4. Batch insert from file");
            System.out.println("5. Batch delete from file");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the word to insert: ");
                    String insertWord = scanner.nextLine();
                    dictionary.insert(insertWord);
                    break;
                case 2:
                    System.out.print("Enter the word to delete: ");
                    String deleteWord = scanner.nextLine();
                    dictionary.delete(deleteWord);
                    break;
                case 3:
                    System.out.print("Enter the word to search: ");
                    String searchWord = scanner.nextLine();
                    dictionary.search(searchWord);
                    break;
                case 4:
                    System.out.print("Enter the path to the file containing words to insert: ");
                    String insertFilePath = scanner.nextLine();
                    dictionary.batchInsert(insertFilePath);
                    break;
                case 5:
                    System.out.print("Enter the path to the file containing words to delete: ");
                    String deleteFilePath = scanner.nextLine();
                    dictionary.batchDelete(deleteFilePath);
                    break;
                case 0:
                         System.out.println("Exiting...");
                         scanner.close();
                         System.exit(0);
                default:
                System.out.println("Invalid choice. Please try again.");
                }
                }
                }
         }