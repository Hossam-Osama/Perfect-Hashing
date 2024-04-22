import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;



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