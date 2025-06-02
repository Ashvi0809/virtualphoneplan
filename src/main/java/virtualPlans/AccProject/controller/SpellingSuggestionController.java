package virtualPlans.AccProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import virtualPlans.AccProject.service.SpellCheckService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
@RequestMapping("/api/spellcheck")
public class SpellingSuggestionController {

    private final SpellCheckService spellCheckService;

    @Autowired
    public SpellingSuggestionController(SpellCheckService spellCheckService) {
        this.spellCheckService = spellCheckService;
    }

    // Endpoint to check spelling and get corresponding data from the second CSV file
    @GetMapping("/check")
    public Map<String, Object> checkSpelling(@RequestParam String word) {
        return spellCheckService.checkSpellingAndFetchData(word);
    }
}
