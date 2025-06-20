package Questions;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A matching-type question where users pair items from two lists.
 */
public class MatchingQuestion extends Question {
    protected List<String> leftItems;
    protected List<String> rightItems;
    protected Map<String, String> correctMatches;

    public MatchingQuestion(int id, int typeId, String question, List<String> leftItems, List<String> rightItems, Map<String, String> correctMatches) {
        super(id, typeId, question);
        this.correctMatches = correctMatches;
        this.leftItems = leftItems;
        this.rightItems = rightItems;
    }



    @Override
    public Set<String> getCorrectAnswers() {
        throw new UnsupportedOperationException("Use getCorrectMatches() instead.");
    }

    @Override
    public int getScore(String answer) {
        throw new UnsupportedOperationException("Use getScore(Map<String, String>) instead.");
    }


    /**
     * Calculates score based on correct key-value matches.
     * Each correct match gives +1 point.
     *
     * @param userAnswers user-submitted matches
     * @return total score
     */
    public int getScore(Map<String, String> userAnswers) {
        if (userAnswers == null) return 0;

        int score = 0;
        for (String current : userAnswers.keySet()) {
            String expected = correctMatches.get(current);
            String actual = userAnswers.get(current);

            if (expected != null && expected.equalsIgnoreCase(actual)) {
                score++;
            }
        }

        return score;
    }

    public Map<String, String> getCorrectMatches() {
        return this.correctMatches;
    }

    public List<String> getLeftItems() {
        return leftItems;
    }

    public List<String> getRightItems() {
        return rightItems;
    }
}
