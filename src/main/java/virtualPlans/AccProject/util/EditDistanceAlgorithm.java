package virtualPlans.AccProject.util;

public class EditDistanceAlgorithm {

    /**
     * This method calculates the edit distance between two words
     * @param firstWord  first word
     * @param secondWord second word
     * @return int edit distance
     */
    public static int getEditDistance(String firstWord, String secondWord) {
        int firstWordLength = firstWord.length();
        int secondWordLength = secondWord.length();
        int[][] dpTable = new int[firstWordLength + 1][secondWordLength + 1];

        for (int i = 0; i <= firstWordLength; i++) {
            for (int j = 0; j <= secondWordLength; j++) {
                if (i == 0) {
                    dpTable[i][j] = j;
                } else if (j == 0) {
                    dpTable[i][j] = i;
                } else if (firstWord.charAt(i - 1) == secondWord.charAt(j - 1)) {
                    dpTable[i][j] = dpTable[i - 1][j - 1];
                } else {
                    dpTable[i][j] = 1 + Math.min(dpTable[i - 1][j],
                            Math.min(dpTable[i][j - 1], dpTable[i - 1][j - 1]));
                }
            }
        }
        return dpTable[firstWordLength][secondWordLength];
    }
}
