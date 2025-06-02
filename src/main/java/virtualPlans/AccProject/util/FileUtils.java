package virtualPlans.AccProject.utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUtils {

    // Method to parse CSV and return a list of words along with their initial frequencies
    public static Map<String, Integer> parseCSV(String filePath) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] lineWords = line.split(",");
                for (String word : lineWords) {
                    wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordFrequencyMap;
    }
}
