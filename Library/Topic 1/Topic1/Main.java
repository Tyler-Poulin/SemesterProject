
/**
 * Write a description of class Main here.
 *  Coordinates the application flow, calling methods from the other classes.
 * @author Tyler Poulin
 * 
 */
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Specify the base directory containing topic folders
        String baseDirectory = "C:\\Users\\typou\\OneDrive\\Documents\\Programming Folder\\Programming Lab Project\\Library\\Topic 1\\Topic1";

        // Specify the path to the stop words file
        String stopWordsFilePath = baseDirectory + "\\stopwords.txt";

        // Create an instance of StopWordFilter
        StopWordFilter stopWordFilter = new StopWordFilter(stopWordsFilePath);

        // List all topic directories
        File baseDir = new File(baseDirectory);
        File[] topicDirectories = baseDir.listFiles(File::isDirectory);

        if (topicDirectories != null) {
            for (File topicDir : topicDirectories) {
                System.out.println("Processing topic: " + topicDir.getName());

                ArticleProcessor articleProcessor = new ArticleProcessor(topicDir.getAbsolutePath());
                articleProcessor.readArticles();
                List<String> articles = articleProcessor.getArticles();

                // Process each article
                for (String article : articles) {
                    String filteredText = stopWordFilter.filter(article); // Filter out stop words
                    
                    // Create an instance of TextAnalyzer with the filtered text
                    TextAnalyzer textAnalyzer = new TextAnalyzer(filteredText);
                    
                    // Print statistics
                    System.out.println("Analyzing Article:");
                    textAnalyzer.printStatistics();
                    System.out.println(); // Add a blank line between articles
                }
            }
        } else {
            System.out.println("No topic directories found in " + baseDirectory);
        }
    }
}
