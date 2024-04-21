
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        initializeTable(setSize);
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
    
}
 class Main {
    public static void main(String[] args) {
        String[] dictionary = {"apple", "ppale", "apple", "dog", "apple", "fish", "grape", "horse", "iguana"};
        int setSize = dictionary.length;

        QuadraticPerfectHashTable perfectHashTable = new QuadraticPerfectHashTable(setSize, dictionary);
        
        // Insertion example
        boolean inserted = perfectHashTable.insert("lemon");
        if (inserted) {
            System.out.println("Key inserted successfully!");
        } else {
            System.out.println("Key already exists!");
        }

        // Deletion example
        boolean deleted = perfectHashTable.delete("cat");
        if (deleted) {
            System.out.println("Key deleted successfully!");
        } else {
            System.out.println("Key not found!");
        }

        // Search example
        boolean found = perfectHashTable.search("dog");
        if (found) {
            System.out.println("Key found!");
        } else {
            System.out.println("Key not found!");
        }
        System.out.println(perfectHashTable.count);
        if (perfectHashTable.n) {
            System.out.println("collision");
        }
    }
}