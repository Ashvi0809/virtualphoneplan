package virtualPlans.AccProject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualPlans.AccProject.service.WordFrequencyService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
@RequestMapping("/api")
public class WordFrequencyController {

    private final WordFrequencyService wordFrequencyService = new WordFrequencyService();

    // API to get the top 7 most frequent words
    @GetMapping("/topFrequentWords")
    public ResponseEntity<Map<String, Object>> getTopFrequentWords() {
        // Initialize the word frequency data from the file if not done already
        wordFrequencyService.initialize();
        var topFrequentWords = wordFrequencyService.getTopFrequentWords();

        // Prepare the response format
        Map<String, Object> response = Map.of(
                "Top 7 most frequent words", topFrequentWords
        );
        return ResponseEntity.ok(response);
    }

    // API to update word frequency using a query parameter
    @GetMapping("/updateWordFrequency")
    public ResponseEntity<Map<String, Object>> updateWordFrequency(@RequestParam String word) {
        // Update the word frequency and get the response structure with the updated top words
        Map<String, Object> updatedData = wordFrequencyService.updateWordFrequency(word);
        return ResponseEntity.ok(updatedData);
    }
}
