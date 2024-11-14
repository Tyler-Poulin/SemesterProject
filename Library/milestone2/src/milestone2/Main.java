package milestone2;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Correct path to the stopwords file
        StopWordFilter stopWordFilter = new StopWordFilter("C:\\Users\\nickp\\eclipse-workspace\\milestone2\\src\\milestone2\\stopwords.txt");

        // Correct path to the article directory
        ArticleProcessor articleProcessor = new ArticleProcessor("C:\\Users\\nickp\\eclipse-workspace\\milestone2\\src\\milestone2");

        // Read articles
        articleProcessor.readArticles();

        // Display articles and let the user select one
        List<String> articleFileNames = articleProcessor.getArticleFileNames();
        if (articleFileNames.isEmpty()) {
            System.out.println("No articles found.");
            return;
        }

        // Let the user select an article for analysis
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an article to analyze:");
        for (int i = 0; i < articleFileNames.size(); i++) {
            System.out.println((i + 1) + ". " + articleFileNames.get(i));
        }

        int choice = scanner.nextInt();
        scanner.close();

        if (choice < 1 || choice > articleFileNames.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String selectedArticle = articleProcessor.getArticles().get(choice - 1);
        String lex = stopWordFilter.filter("C:\\Users\\nickp\\eclipse-workspace\\milestone2\\src\\milestone2\\lexicon_scores.txt");
        TextAnalyzer textAnalyzer = new TextAnalyzer(stopWordFilter.filter(selectedArticle), lex);

        // Print statistics for the selected article
        textAnalyzer.printStatistics();
    }
}