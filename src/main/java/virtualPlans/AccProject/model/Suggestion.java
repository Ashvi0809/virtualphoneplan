package virtualPlans.AccProject.model;

public class Suggestion {

    private String word;
    private int distance;

    public Suggestion(String word, int distance) {
        this.word = word;
        this.distance = distance;
    }

    // Getters and Setters
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
