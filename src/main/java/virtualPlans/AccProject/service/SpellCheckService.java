package virtualPlans.AccProject.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpellCheckService {

    private final Set<String> vocabulary;
    private final List<Map<String, String>> csvData;

    public SpellCheckService() {
        // Load vocabulary from text file
        this.vocabulary = loadVocabularyFromTxt();

        // Load CSV data
        this.csvData = loadCsvData();
    }

    /**
     * Method to check spelling of a word and provide suggestions if necessary.
     * If the word is in the vocabulary, check the CSV file for matching data.
     */
    public Map<String, Object> checkSpellingAndFetchData(String word) {
        Map<String, Object> response = new HashMap<>();

        // Check if the word exists in the vocabulary
        if (vocabulary.contains(word.toLowerCase())) {
            // Check if the word is present in the CSV data
            List<Map<String, String>> matchedData = csvData.stream()
                    .map(data -> data.entrySet().stream()
                            .collect(Collectors.toMap(
                                    entry -> Character.toLowerCase(entry.getKey().charAt(0)) + entry.getKey().substring(1),
                                    Map.Entry::getValue
                            ))
                    )
                    .filter(data -> data.values().stream()
                            .anyMatch(value -> value.equalsIgnoreCase(word)))
                    .collect(Collectors.toList());

            response.put("status", true);
            response.put("data", matchedData.isEmpty() ? new ArrayList<>() : matchedData);
            response.put("suggestions", new ArrayList<>());
        } else {
            // If not found, provide suggestions
            List<String> suggestions = getSuggestions(word);
            response.put("status", false);
            response.put("data", new ArrayList<>());
            response.put("suggestions", suggestions.isEmpty() ? new ArrayList<>() : suggestions);
        }

        return response;
    }

    /**
     * Load vocabulary from a text file.
     */
    private Set<String> loadVocabularyFromTxt() {
        Set<String> words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("crawled-db/words.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.addAll(Arrays.asList(line.toLowerCase().split("\\s+")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    /**
     * Load data from a CSV file.
     */
    private List<Map<String, String>> loadCsvData() {
        List<Map<String, String>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("crawled-db/project.csv"))) {
            String headerLine = reader.readLine();
            if (headerLine == null) return data;

            String[] headers = headerLine.split(",");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> entry = new HashMap<>();
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    entry.put(headers[i].trim(), values[i].trim());
                }
                data.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get suggestions for the misspelled word.
     * Suggestions are words with an edit distance of 1 or 2.
     */
    private List<String> getSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();
        for (String vocabWord : vocabulary) {
            int editDistance = getEditDistance(word.toLowerCase(), vocabWord.toLowerCase());
            if (editDistance <= 2) {
                suggestions.add(vocabWord);
            }
        }
        return suggestions.isEmpty() ? new ArrayList<>() : suggestions;
    }

    /**
     * Calculate the edit distance between two words.
     */
    private int getEditDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[len1][len2];
    }
}
