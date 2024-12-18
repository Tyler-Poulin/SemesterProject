/**
 * Reads text files from a specified directory. This code will load the content of all text files in the directory and store them in a list.
 *
 * @author Tyler Poulin
 */
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleProcessor {
    private String directory;
    private List<String> articles;
    private List<String> articleFileNames; // List to hold the file names

    public ArticleProcessor(String directory) {
        this.directory = directory;
        this.articles = new ArrayList<>();
        this.articleFileNames = new ArrayList<>(); // Initialize the list of file names
    }

    public void readArticles() {
        try {
            // Get all text files in the specified directory
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory), "*.txt");
            for (Path filePath : stream) {
                String fileName = filePath.getFileName().toString();

                // Skip the stopwords.txt and README.txt files
                if (fileName.equalsIgnoreCase("stopwords.txt") || fileName.equalsIgnoreCase("README.txt")) {
                    continue;
                }

                // Read the content of each file
                String content = new String(Files.readAllBytes(filePath), "UTF-8");
                articles.add(content);
                articleFileNames.add(fileName); // Add file name to the list
            }
            System.out.println("Read " + articles.size() + " articles from " + directory + ".");
        } catch (IOException e) {
            System.err.println("An error occurred while reading articles: " + e.getMessage());
        }
    }

    public List<String> getArticles() {
        return articles;
    }

    public List<String> getArticleFileNames() {
        return articleFileNames; // Return the list of article file names
    }
}
