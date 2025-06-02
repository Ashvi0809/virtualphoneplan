package virtualPlans.AccProject.service;

import java.util.*;

public class CuckooHashing {

    private final Map<String, Integer> wordFrequencyMap;

    public CuckooHashing(Map<String, Integer> initialWordFrequencies) {
        this.wordFrequencyMap = new HashMap<>(initialWordFrequencies);
    }

    // Update the frequency of a word
    public void updateAndPrintWordFrequency(String word) {
        wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
    }

    // Get frequency of a specific word
    public int getWordFrequency(String word) {
        return wordFrequencyMap.getOrDefault(word, 0);
    }

    // Get the top N frequent words
    public List<Map.Entry<String, Integer>> getTopFrequentWords(int N) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordFrequencyMap.entrySet());
        entries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));  // Sort by frequency
        return entries.subList(0, Math.min(N, entries.size()));
    }
}
