package virtualPlans.AccProject.controller;

import virtualPlans.AccProject.service.invertIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
@RequestMapping("/api/inverted-index")
public class invertIndexController {

    private final invertIndexService indexService;

    @Autowired
    public invertIndexController(invertIndexService indexService) {
        this.indexService = indexService;
    }

    // Combined endpoint to get both ranking and word positions
    @GetMapping("/search-info")
    public Map<String, Object> searchAndRankWithPositions(@RequestParam("word") String word) {
        return indexService.getKeywordInfo(word);
    }
}
