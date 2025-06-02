package virtualPlans.AccProject.model;

import java.util.HashSet;
import java.util.Set;

public class webCrawlerModel {

    private String url; // The URL being crawled
    private Set<String> foundLinks; // Links found during the crawl

    // Default constructor
    public webCrawlerModel() {
        this.foundLinks = new HashSet<>(); // Initialize with an empty HashSet
    }

    // Parameterized constructor
    public webCrawlerModel(String url, Set<String> foundLinks) {
        this.url = url;
        this.foundLinks = foundLinks != null ? foundLinks : new HashSet<>();
    }

    // Getter for the URL
    public String getUrl() {
        return url;
    }

    // Setter for the URL
    public void setUrl(String url) {
        this.url = url;
    }

    // Getter for found links
    public Set<String> getFoundLinks() {
        return new HashSet<>(foundLinks); // Return a copy to maintain immutability
    }

    // Setter for found links
    public void setFoundLinks(Set<String> foundLinks) {
        this.foundLinks = foundLinks != null ? foundLinks : new HashSet<>();
    }

    // Method to add a single link to the set
    public void addFoundLink(String link) {
        if (this.foundLinks == null) {
            this.foundLinks = new HashSet<>();
        }
        this.foundLinks.add(link);
    }

    // Method to clear all found links
    public void clearFoundLinks() {
        if (this.foundLinks != null) {
            this.foundLinks.clear();
        }
    }

    // Method to check if a specific link is already in the set
    public boolean hasLink(String link) {
        return this.foundLinks != null && this.foundLinks.contains(link);
    }

    @Override
    public String toString() {
        return "WebCrawlerModel{" +
                "url='" + url + '\'' +
                ", foundLinks=" + foundLinks +
                '}';
    }
}
