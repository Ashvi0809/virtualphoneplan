package virtualPlans.AccProject.model;

import java.util.List;
import java.util.Map;

public class invertIndexModel {
    private String searchWord;
    private List<String> documentNames;
    private Map<String, Integer> pageRanking;

    // Getters and Setters
    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public List<String> getDocumentNames() {
        return documentNames;
    }

    public void setDocumentNames(List<String> documentNames) {
        this.documentNames = documentNames;
    }

    public Map<String, Integer> getPageRanking() {
        return pageRanking;
    }

    public void setPageRanking(Map<String, Integer> pageRanking) {
        this.pageRanking = pageRanking;
    }
}
