
/**
 * Write a description of class StopWordFilter here.
 *Read the contents of stop words file into a data structure, such as a Set, for efficient look-up.
Filter the Text: Create a method to remove the stop words from a given piece of text.
 * @author Tyler Poulin
 * 
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class StopWordFilter {
    private Set<String> stopWords;

    public StopWordFilter(String stopWordsFilePath) {
        stopWords = new HashSet<>();
        loadStopWords(stopWordsFilePath);
    }

    private void loadStopWords(String filePath) {
        try {
            // Read all lines from the stop words file
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                // Split by whitespace and add to the set
                stopWords.addAll(Arrays.asList(line.split("\\s+")));
            }
            System.out.println("Loaded " + stopWords.size() + " stop words.");
        } catch (IOException e) {
            System.err.println("Error loading stop words: " + e.getMessage());
        }
    }

    public String filter(String text) {
        StringBuilder filteredText = new StringBuilder();
        String[] words = text.split("\\s+"); // Split the text into words

        for (String word : words) {
            // Remove punctuation and convert to lowercase for matching
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!stopWords.contains(cleanWord)) {
                filteredText.append(word).append(" "); // Preserve original word (with punctuation)
            }
        }

        return filteredText.toString().trim(); // Return the filtered text
    }
}
