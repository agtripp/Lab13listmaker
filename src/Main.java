import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private List<String> currentList;
    private String currentFileName;

    public Main() {
        currentList = new ArrayList<>();
        currentFileName = null;
    }

    public static void main(String[] args) {
        Main listManager = new Main();
        listManager.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Load List");
            System.out.println("2. Save List");
            System.out.println("3. Create New List");
            System.out.println("4. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    loadListPrompt();
                    break;
                case 2:
                    saveListPrompt();
                    break;
                case 3:
                    createNewListPrompt();
                    break;
                case 4:
                    exit = quitPrompt();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private void loadListPrompt() {
        if (isListModified()) {
            System.out.println("Save the current list before loading another one.");
            saveListPrompt();
        }

        System.out.print("Enter the filename to load: ");
        String fileName = getFileInput();

        try {
            loadList(fileName);
        } catch (IOException e) {
            System.out.println("Error loading the file: " + e.getMessage());
        }
    }

    private void saveListPrompt() {
        if (currentFileName == null) {
            System.out.println("No file loaded. Use 'Create New List' or 'Load List' first.");
            return;
        }

        System.out.println("Saving the list...");
        try {
            saveList(currentFileName);
            System.out.println("List saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }

    private void createNewListPrompt() {
        if (isListModified()) {
            System.out.println("Save the current list before creating a new one.");
            saveListPrompt();
        }

        System.out.print("Enter the basename for the new list: ");
        String basename = getFileInput();

        currentFileName = basename + ".txt";
        currentList.clear();

        System.out.println("New list created with filename: " + currentFileName);
    }

    private boolean quitPrompt() {
        if (isListModified()) {
            System.out.println("Save the current list before quitting.");
            saveListPrompt();
            return false;
        }
        System.out.println("Exiting the program. Goodbye!");
        return true;
    }

    private boolean isListModified() {
        return currentFileName != null && !currentList.isEmpty();
    }

    private String getFileInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private void loadList(String fileName) throws IOException {
        currentFileName = fileName;
        currentList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currentList.add(line);
            }
        }

        System.out.println("List loaded from file: " + fileName);
        printList();
    }

    private void saveList(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String item : currentList) {
                writer.write(item);
                writer.newLine();
            }
        }
    }

    private void printList() {
        if (!currentList.isEmpty()) {
            System.out.println("Current List:");
            for (String item : currentList) {
                System.out.println(item);
            }
        } else {
            System.out.println("The list is empty.");
        }
    }
}