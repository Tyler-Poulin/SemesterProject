
/**
 * Write a description of class TextAnalyzer here.
 *Perform basic text analysis, including calculating word counts and ranking words by their frequency.
 * @author Tyler Poulin
 * 
 */
import java.util.*;

public class TextAnalyzer {
    private String text;
    private Map<String, Integer> wordFrequencyMap;

    public TextAnalyzer(String text) {
        this.text = text;
        this.wordFrequencyMap = new HashMap<>();
        analyzeText();
    }

    private void analyzeText() {
        String[] words = text.split("\\s+");
        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase(); // Clean and normalize the word
            if (!cleanWord.isEmpty()) {
                wordFrequencyMap.put(cleanWord, wordFrequencyMap.getOrDefault(cleanWord, 0) + 1);
            }
        }
    }

    public int getWordCount() {
        return wordFrequencyMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getUniqueWordCount() {
        return wordFrequencyMap.size();
    }

    public List<Map.Entry<String, Integer>> getRankedWords() {
        List<Map.Entry<String, Integer>> rankedWords = new ArrayList<>(wordFrequencyMap.entrySet());
        rankedWords.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Sort by frequency
        return rankedWords;
    }

    public int getStatementCount() {
        String[] statements = text.split("[.!?]");
        return statements.length; // Return the number of statements
    }

    public void printStatistics() {
        System.out.println("Total Words: " + getWordCount());
        System.out.println("Unique Words: " + getUniqueWordCount());
        System.out.println("Total Statements: " + getStatementCount());
        System.out.println("Top Words:");
        List<Map.Entry<String, Integer>> rankedWords = getRankedWords();
        for (int i = 0; i < Math.min(10, rankedWords.size()); i++) { // Print top 10 words
            System.out.println(rankedWords.get(i).getKey() + ": " + rankedWords.get(i).getValue());
        }
    }
}
