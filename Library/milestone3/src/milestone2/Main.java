package milestone2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StopWordFilter stopWordFilter = new StopWordFilter("C:\\Users\\nickp\\eclipse-workspace\\milestone2\\src\\milestone2\\stopwords.txt");
        ArticleProcessor articleProcessor = new ArticleProcessor("C:\\Users\\nickp\\eclipse-workspace\\milestone2\\src\\milestone2");

        articleProcessor.readArticles();

        while (true) {
            System.out.println("\n--- Text Analysis Application ---");
            System.out.println("1. View and analyze existing articles");
            System.out.println("2. Add a new article");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Display and analyze existing articles
                    analyzeExistingArticles(articleProcessor, stopWordFilter, scanner);
                    break;
                case 2:
                    // Add a new article
                    addNewArticle(articleProcessor, scanner);
                    break;
                case 3:
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void analyzeExistingArticles(ArticleProcessor articleProcessor, StopWordFilter stopWordFilter, Scanner scanner) {
        List<String> articleFileNames = articleProcessor.getArticleFileNames();
        if (articleFileNames.isEmpty()) {
            System.out.println("No articles found.");
            return;
        }

        System.out.println("\nSelect an article to analyze:");
        for (int i = 0; i < articleFileNames.size(); i++) {
            System.out.println((i + 1) + ". " + articleFileNames.get(i));
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (choice < 1 || choice > articleFileNames.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String selectedArticle = articleProcessor.getArticles().get(choice - 1);
        TextAnalyzer textAnalyzer = new TextAnalyzer(stopWordFilter.filter(selectedArticle),
                "C:\\Users\\nickp\\eclipse-workspace\\milestone2\\src\\milestone2\\lexicon_scores.txt");

        textAnalyzer.printStatistics();
    }

    private static void addNewArticle(ArticleProcessor articleProcessor, Scanner scanner) {
        System.out.println("\n--- Add New Article ---");
        System.out.println("1. Provide a file path");
        System.out.println("2. Enter text directly");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String directoryPath = "C:\\Users\\nickp\\eclipse-workspace\\milestone2\\src\\milestone2";
        String newFileName;

        try {
            if (option == 1) {
                // Add article from file
                System.out.print("Enter the full path of the text file: ");
                String filePath = scanner.nextLine();
                File sourceFile = new File(filePath);
                if (!sourceFile.exists() || !sourceFile.isFile()) {
                    System.out.println("Invalid file path.");
                    return;
                }

                newFileName = sourceFile.getName();
                File destinationFile = new File(directoryPath, newFileName);
                Files.copy(sourceFile.toPath(), destinationFile.toPath());
                System.out.println("Article added successfully!");

            } else if (option == 2) {
                // Add article from direct input
                System.out.print("Enter a title for the new article: ");
                newFileName = scanner.nextLine() + ".txt";
                System.out.println("Enter the article text (end input with an empty line):");
                StringBuilder content = new StringBuilder();
                while (true) {
                    String line = scanner.nextLine();
                    if (line.isEmpty()) break;
                    content.append(line).append("\n");
                }

                File newFile = new File(directoryPath, newFileName);
                try (FileWriter writer = new FileWriter(newFile)) {
                    writer.write(content.toString());
                }
                System.out.println("Article added successfully!");

            } else {
                System.out.println("Invalid option. Returning to the main menu.");
                return;
            }

            // Refresh the article list
            articleProcessor.readArticles();

        } catch (IOException e) {
            System.err.println("Error adding new article: " + e.getMessage());
        }
    }
}
