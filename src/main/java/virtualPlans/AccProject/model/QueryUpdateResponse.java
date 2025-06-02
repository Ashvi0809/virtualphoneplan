package virtualPlans.AccProject.model;

import java.util.List;

public class QueryUpdateResponse {
    private String query;
    private int updatedFrequency;
    private List<WordFrequency> topWords;

    public QueryUpdateResponse(String query, int updatedFrequency, List<WordFrequency> topWords) {
        this.query = query;
        this.updatedFrequency = updatedFrequency;
        this.topWords = topWords;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getUpdatedFrequency() {
        return updatedFrequency;
    }

    public void setUpdatedFrequency(int updatedFrequency) {
        this.updatedFrequency = updatedFrequency;
    }

    public List<WordFrequency> getTopWords() {
        return topWords;
    }

    public void setTopWords(List<WordFrequency> topWords) {
        this.topWords = topWords;
    }
}
