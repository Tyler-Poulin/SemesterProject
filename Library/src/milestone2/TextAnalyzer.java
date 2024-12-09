package milestone2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextAnalyzer {
    private String text;
    private Map<String, Integer> wordFrequencyMap;
    private Map<String, Double> lexiconMap;

    public TextAnalyzer(String text, String lexiconFilePath) {
        this.text = text;
        this.wordFrequencyMap = new HashMap<>();
        this.lexiconMap = new HashMap<>();
        analyzeText();
        loadLexicon(lexiconFilePath);
    }

    private void analyzeText() {
        String[] words = text.split("\\s+");
        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!cleanWord.isEmpty()) {
                wordFrequencyMap.put(cleanWord, wordFrequencyMap.getOrDefault(cleanWord, 0) + 1);
            }
        }
    }

    // Method to load the lexicon from a file
    private void loadLexicon(String filePath) {
        File lexiconFile = new File(filePath);
        if (!lexiconFile.exists()) {
            System.err.println("Lexicon file not found at: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(lexiconFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\t");
                if (parts.length == 2) {
                    String word = parts[0].toLowerCase();
                    
                    try {
                        double score = Double.parseDouble(parts[1].trim());
                        lexiconMap.put(word, score);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid score for word: " + word + " in lexicon file.");
                    }
                } else {
                    System.err.println("Skipping invalid line in lexicon file: " + line);
                }
            }
            System.out.println("Loaded " + lexiconMap.size() + " words from lexicon.");
        } catch (IOException e) {
            System.err.println("Error loading lexicon from " + filePath + ": " + e.getMessage());
        }
    }

    public String lexiconAnalyzer() {
        double score = 0.0;
        for (String word : wordFrequencyMap.keySet()) {
            if (lexiconMap.containsKey(word)) {
                score += lexiconMap.get(word) * wordFrequencyMap.get(word);
            }
        }

        if (score > 0) {
            return "Positive";
        } else if (score < 0) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }


    // Existing methods for getting word counts, unique word counts, etc.
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
        System.out.println("Sentiment: " + lexiconAnalyzer());
    }
}
