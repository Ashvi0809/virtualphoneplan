package virtualPlans.AccProject.service;

import virtualPlans.AccProject.model.invertIndexModel;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class invertIndexService {

    private final Map<String, List<String>> invertedIndex;
    private final Map<String, String> documentContents; // To store document contents for keyword search

    public invertIndexService() {
        this.invertedIndex = new HashMap<>();
        this.documentContents = new HashMap<>();
        createInvertedIndexFromStaticPaths(); // Initialize the inverted index with static paths
    }

    // Method to initialize the inverted index with static paths
    public void createInvertedIndexFromStaticPaths() {
        // Directory path where your text files are stored
        String directoryPath = "crawled-db/saved-pages/";

        // Create a File object for the directory
        File directory = new File(directoryPath);

        // List all .txt files in the directory
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        // If files exist, pass them to the createInvertedIndex method
        if (files != null && files.length > 0) {
            createInvertedIndex(files);
        } else {
            System.out.println("No .txt files found in the directory.");
        }
    }

    // Method to create the inverted index from the given files
    public void createInvertedIndex(File[] files) {
        if (files == null) return;

        // Iterate over each file and process it
        for (File file : files) {
            if (!file.exists()) {
                System.err.println("File not found: " + file.getAbsolutePath());
                continue;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder contentBuilder = new StringBuilder();

                // Read the file and build the content
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                    processLine(line, file.getName());
                }

                // Store the file content for keyword search later
                documentContents.put(file.getName(), contentBuilder.toString());

            } catch (IOException e) {
                System.err.println("Error reading file: " + file.getName());
            }
        }
    }

    // Process each line to build the inverted index
    private void processLine(String line, String documentName) {
        String[] cells = line.split(",");
        int index = 0; // To track the word position in the document
        for (String cell : cells) {
            String[] words = cell.trim().split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    String processedWord = word.toLowerCase();
                    addToInvertedIndex(processedWord, documentName, index);
                    index++;
                }
            }
        }
    }

    // Method to add a word and its corresponding document to the inverted index
    private void addToInvertedIndex(String word, String documentName, int index) {
        invertedIndex.putIfAbsent(word, new ArrayList<>());
        List<String> documentNames = invertedIndex.get(word);
        if (!documentNames.contains(documentName)) {
            documentNames.add(documentName); // Add the document name if it's not already there
        }
    }

    // Method to search for a word and get a list of documents that contain the word
    public List<String> searchWord(String searchWord) {
        return invertedIndex.getOrDefault(searchWord.toLowerCase(), new ArrayList<>());
    }

    // Method to rank pages by keyword frequency and return a combined response
    public Map<String, Object> getKeywordInfo(String keyword) {
        List<String> documentsContainingKeyword = searchWord(keyword);

        // Count keyword occurrences in each document
        Map<String, Integer> pageRank = new HashMap<>();
        Map<String, List<Integer>> positions = searchWordPositions(keyword);

        for (String document : documentsContainingKeyword) {
            int frequency = countKeywordInDocument(document, keyword);
            pageRank.put(document, frequency);
        }

        // Sort the documents by frequency in descending order
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(pageRank.entrySet());
        sortedList.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

        // Rebuild the map with sorted entries
        Map<String, Integer> sortedPageRank = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : sortedList) {
            sortedPageRank.put(entry.getKey(), entry.getValue());
        }

        // Prepare the combined response
        Map<String, Object> response = new HashMap<>();
        response.put("keyword", keyword);
        response.put("rankings", sortedPageRank);
        response.put("positions", positions);

        return response;
    }

    // Method to search for word positions in each document
    public Map<String, List<Integer>> searchWordPositions(String word) {
        Map<String, List<Integer>> wordPositions = new HashMap<>();
        String lowerCaseWord = word.toLowerCase();

        for (Map.Entry<String, String> entry : documentContents.entrySet()) {
            String documentName = entry.getKey();
            String content = entry.getValue().toLowerCase();
            List<Integer> positions = new ArrayList<>();
            int index = 0;

            // Split the content to find positions
            String[] words = content.split("\\W+");
            for (String currentWord : words) {
                if (currentWord.equals(lowerCaseWord)) {
                    positions.add(index);
                }
                index++;
            }

            if (!positions.isEmpty()) {
                wordPositions.put(documentName, positions);
            }
        }
        return wordPositions;
    }

    // Method to count the occurrences of the keyword in a document
    private int countKeywordInDocument(String document, String keyword) {
        String content = documentContents.get(document);
        if (content == null) return 0;

        // Use a case-insensitive search for the keyword
        return countOccurrences(content.toLowerCase(), keyword.toLowerCase());
    }

    // Helper method to count occurrences of the keyword in the text
    private int countOccurrences(String content, String keyword) {
        int count = 0;
        int index = content.indexOf(keyword);
        while (index != -1) {
            count++;
            index = content.indexOf(keyword, index + 1);
        }
        return count;
    }

    // Method to return the entire inverted index (for debugging or informational purposes)
    public Map<String, List<String>> getInvertedIndex() {
        return invertedIndex;
    }
}
