package virtualPlans.AccProject.model;

public class TreeNode {
    public String word;
    public int frequency;
    public TreeNode left, right;

    public TreeNode(String word) {
        this.word = word;
        this.frequency = 1; // Initialize frequency to 1
        this.left = null;
        this.right = null;
    }

    public void incrementFrequency() {
        this.frequency++;
    }
}
