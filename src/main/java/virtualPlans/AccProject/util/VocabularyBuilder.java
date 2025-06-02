package virtualPlans.AccProject.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VocabularyBuilder {

    /**
     * This method is used to build vocabulary from the given CSV files
     * @param filePaths list containing file paths to vocab files
     * @return Set<String> vocabulary
     */
    public static Set<String> createVocabularyList(List<String> filePaths) {
        Set<String> vocabulary = new HashSet<>();

        for (String filePath : filePaths) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s*,\\s*");
                    for (String word : words) {
                        String[] individualWords = word.split("\\W+");
                        for (String individualWord : individualWords) {
                            if (!individualWord.isEmpty()) {
                                vocabulary.add(individualWord.toLowerCase());
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading the file: " + filePath);
            }
        }
        return vocabulary;
    }
}
