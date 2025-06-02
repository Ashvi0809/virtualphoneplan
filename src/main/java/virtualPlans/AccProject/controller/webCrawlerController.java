package virtualPlans.AccProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualPlans.AccProject.service.webCrawlerService;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
@RequestMapping("/crawler")
public class webCrawlerController {

    @Autowired
    private webCrawlerService crawlerService;

    // Endpoint to crawl a single URL
    @PostMapping("/crawl")
    public ResponseEntity<String> crawl(@RequestParam("url") String url) {
        try {
            crawlerService.crawl(url); // Trigger crawling in the service
            return ResponseEntity.ok("Crawling successful for URL: " + url);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    // Endpoint to get the list of visited URLs
    @GetMapping("/visited-set")
    public ResponseEntity<Set<String>> getVisitedUrlsSet() {
        Set<String> visitedUrlsSet = crawlerService.getFoundLinksSet();
        return ResponseEntity.ok(visitedUrlsSet);
    }
}
