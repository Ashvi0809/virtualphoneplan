package virtualPlans.AccProject.service;

import virtualPlans.AccProject.utils.FileUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFrequencyService {

    private CuckooHashing cuckooHashing;

    // Initialize the word frequency map from the CSV file
    public void initialize() {
        // Load word frequencies from the CSV file
        // Path to your CSV file
        String fileOriginPath = "crawled-db/project.csv";
        Map<String, Integer> initialWordFrequencies = FileUtils.parseCSV(fileOriginPath);
        cuckooHashing = new CuckooHashing(initialWordFrequencies);
    }

    // Method to get the top 7 frequent words, formatted as a list of maps
    public List<Map<String, Object>> getTopFrequentWords() {
        // Get the top frequent words as a list of Map entries
        List<Map.Entry<String, Integer>> topWords = cuckooHashing.getTopFrequentWords(7);

        // Convert the list of entries to a list of maps for the response format
        return topWords.stream()
                .map(entry -> Map.of(
                        "word", (Object) entry.getKey(),  // Cast key to Object (String)
                        "frequency", (Object) entry.getValue()  // Cast value to Object (Integer)
                ))
                .collect(Collectors.toList());
    }

    // Method to update word frequency and return updated data in the required format
    public Map<String, Object> updateWordFrequency(String word) {
        // Update word frequency
        cuckooHashing.updateAndPrintWordFrequency(word);

        // Ensure that the updated word is in the top 7 words list
        List<Map.Entry<String, Integer>> topWords = cuckooHashing.getTopFrequentWords(7);

        // Convert top words list into the correct response format
        List<Map<String, Object>> updatedTopWords = topWords.stream()
                .map(entry -> Map.of(
                        "word", (Object) entry.getKey(),  // Cast key to Object (String)
                        "frequency", (Object) entry.getValue()  // Cast value to Object (Integer)
                ))
                .collect(Collectors.toList());

        // Return the response with updated data
        Map<String, Object> response = Map.of(
                "updatedWord", word,
                "updatedFrequency", cuckooHashing.getWordFrequency(word),
                "topFrequentWords", updatedTopWords  // Ensure updated top words list is included
        );

        return response;
    }
}
