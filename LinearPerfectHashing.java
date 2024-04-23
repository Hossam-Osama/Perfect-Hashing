import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearPerfectHashing 
{
    private QuadraticPerfectHashTable table[];
    private UniversalHashFunction hashFunction;
    private int[][]hashFunctiontable;
    private int sizeOfTable;
    
    public LinearPerfectHashing (int setSize, String[] dic) {
        sizeOfTable = setSize;
        hashFunction = new UniversalHashFunction();
        table = new QuadraticPerfectHashTable[sizeOfTable];
        int row = (int) (Math.log(sizeOfTable) / Math.log(2)) + 1;
        hashFunctiontable = hashFunction.initRandomHash(row);
    }
    
    public boolean insert(String key)
    {
        int index = hashFunction.generateHash(key,hashFunctiontable , sizeOfTable);
        QuadraticPerfectHashTable bin = table[index];
        if (bin == null) {
            bin = new QuadraticPerfectHashTable(1,new String[0]);
            bin.insert(key);
            table[index] = bin;
            return true;

        } else {
            if (bin.search(key)) {
                System.out.println(" it is already exist");
                return false;
            } else {
                System.out.println("there is collision");
                bin.insert(key);
                return true;
            }
        }
    }


    public boolean delete(String key) {


        int index = hashFunction.generateHash(key, hashFunctiontable, sizeOfTable);
        QuadraticPerfectHashTable bin = table[index];
        if (bin == null) {
            System.out.println(" it is not found ");
        } else {
            if (bin.search(key)) {
                System.out.println(" it is  exist");
                table[index].delete(key);
                return true;
            } else {
                System.out.println(" it is not found ");
                return false;
            }
        }
          
        return false;
    }
    public boolean search(String key) {
        int index = hashFunction.generateHash(key, hashFunctiontable, sizeOfTable);
        QuadraticPerfectHashTable bin = table[index];
        if (bin == null) {
            System.out.println(" it is not found ");

        } else {
            if (bin.search(key)) {
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

            

             int inserted=0;
            while ((line = reader.readLine()) != null) {
                int x=hashFunction.generateHash(line, hashFunctiontable, sizeOfTable);
                if(table[x]==null)
                {
                    table[x]=new QuadraticPerfectHashTable(1, new String[0]);
                    table[x].insert(line);
                    inserted++;
                }else
                {
                    if(table[x].insert(line))inserted++;
                }
                
            }
           
           
           
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void batchDelete(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
           
//            int nonExistingCount = 0;
                int deleted=0;
            while ((line = reader.readLine()) != null) {
                int x=hashFunction.generateHash(line, hashFunctiontable, sizeOfTable);
                if(table[x]!=null)
                {
                    if(table[x].delete(line))deleted++;
                }
            }

            System.out.println("Batch Delete Summary:");

            System.out.println("  count is " +getnumberOfelement() );
            System.out.println(Arrays.deepToString(table));
            System.out.println("Deleted: " +deleted + " words");
//            System.out.println("Non-existing: " + nonExistingCount + " words");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    // int totalInserted;
    // int totalDeleted;
    // int failedDeleted;
    // int failedInserted;
    // int numberOfResh;

    public int getTotalInserted()
    {
        int total=0;
        for(QuadraticPerfectHashTable q:table)
        {
            if(q!=null)total+=q.totalInserted;
        } 

        return total;
    }
    public int getTotalDeleted()
    {
        int total=0;
        for(QuadraticPerfectHashTable q:table)
        {
            if(q!=null)total+=q.totalDeleted;
        } 

        return total;
    }
    public int getFailedDeleted()
    {
        int total=0;
        for(QuadraticPerfectHashTable q:table)
        {
            if(q!=null)total+=q.failedDeleted;
        } 

        return total;
    }
    public int getfailedInserted()
    {
        int total=0;
        for(QuadraticPerfectHashTable q:table)
        {
            if(q!=null)total+=q.failedInserted;
        } 

        return total;
    }
    public int getnumberOfResh()
    {
        int total=0;
        for(QuadraticPerfectHashTable q:table)
        {
            if(q!=null)total+=q.numberOfResh;
        } 

       
     return total;
    }
    public int getnumberOfelement()
    {
        int total=0;
        for(QuadraticPerfectHashTable q:table)
        {
            if(q!=null)total+=q.count;
        } 

       
     return total;
    }

    
 
    
}
