package virtualPlans.AccProject.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class webCrawlerService {

    private static final String BASE_DIRECTORY = "saved_pages";
    private final Set<String> visitedUrls = new HashSet<>();

    // Crawl a URL
    public void crawl(String url) {
        try {
            if (visitedUrls.contains(url)) {
                throw new IllegalArgumentException("URL already crawled: " + url);
            }

            System.out.println("Crawling: " + url);

            // Fetch and process the page
            Document doc = Jsoup.connect(url).get();
            saveHtmlToFile(doc, url);
            saveTextToFile(doc, url);

            visitedUrls.add(url);

            // Extract and store links
            Elements links = doc.select("a[href]");
            for (var link : links) {
                String absoluteUrl = link.absUrl("href");
                if (!visitedUrls.contains(absoluteUrl)) {
                    visitedUrls.add(absoluteUrl);
                    System.out.println("Found link: " + absoluteUrl);
                }
            }
        } catch (IOException e) {
            System.out.println("IOException while crawling: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
        }
    }

    // Retrieve the set of visited URLs
    public Set<String> getFoundLinksSet() {
        return visitedUrls;
    }

    // Save HTML to a file
    private void saveHtmlToFile(Document doc, String url) throws IOException {
        File directory = new File(BASE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filename = createFileName(url, "html");
        File file = new File(directory, filename);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(doc.html());
        }
        System.out.println("Saved HTML to: " + file.getAbsolutePath());
    }

    // Save text content to a .txt file
    private void saveTextToFile(Document doc, String url) throws IOException {
        File directory = new File(BASE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filename = createFileName(url, "txt");
        File file = new File(directory, filename);

        try (FileWriter writer = new FileWriter(file)) {
            String textContent = doc.body().text();
            writer.write(textContent);
        }
        System.out.println("Saved text to: " + file.getAbsolutePath());
    }

    // Utility method to create a sanitized file name
    private String createFileName(String url, String extension) {
        return url.replace("https://", "")
                .replace("http://", "")
                .replaceAll("[^a-zA-Z0-9.-]", "_") + "." + extension;
    }
}
