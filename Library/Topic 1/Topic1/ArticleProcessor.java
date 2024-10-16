
/**
 * Reads text files from a specified directory. This code will load the content of all text files in the directory and store them in a list.
 *
 * @Tyler Poulin
 * 
 */
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleProcessor {
    private String directory;
    private List<String> articles;

    public ArticleProcessor(String directory) {
        this.directory = directory;
        this.articles = new ArrayList<>();
    }

    public void readArticles() {
        try {
            // Get all text files in the specified directory
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory), "*.txt");
            for (Path filePath : stream) {
                // Read the content of each file
                String content = new String(Files.readAllBytes(filePath), "UTF-8");
                articles.add(content);
            }
            System.out.println("Read " + articles.size() + " articles from " + directory + ".");
        } catch (IOException e) {
            System.err.println("An error occurred while reading articles: " + e.getMessage());
        }
    }

    public List<String> getArticles() {
        return articles;
    }
}
