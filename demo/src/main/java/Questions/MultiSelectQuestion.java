package Questions;

import java.util.List;
import java.util.Set;

/*
    Multiple Choice Multiple Answers Question.
    User can select more than one response.

    Example:
    Please mark each statement below which is true:
    (1) Stanford was established in 1891
    (2) Stanford has the best computer science department in the world
    (3) Stanford will be going to a bowl game this year
*/
public class MultiSelectQuestion extends Question {
    private final List<String> possibleAnswers;
    private final Set<String> answers;

    /**
     * Constructs a multiple-select question where the user can choose multiple correct answers.
     *
     * @param id              The unique identifier of the question.
     * @param typeId          The type identifier used to classify the question (e.g., for rendering or processing).
     * @param question        The question prompt or text shown to the user.
     * @param possibleAnswers A list of all answer choices that the user can select from.
     * @param answers         A set of correct answers that will be used for scoring.
     */
    public MultiSelectQuestion(int id, int typeId, String question,
                               List<String> possibleAnswers, Set<String> answers) {
        super(id, typeId, question);
        this.possibleAnswers = possibleAnswers;
        this.answers = answers;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    @Override
    public Set<String> getCorrectAnswers() {
        return answers;
    }


    /**
     * @param answer User's Answer
     * @return total score
       In our implementation, `answer` is a merged string of answers
       submitted by the user for this question (comma-separated).

       Each correct item gives +1 point.
    */
    @Override
    public int getScore(String answer) {
        if (answer == null || answer.isEmpty()) return 0;

        String[] userAnswers = answer.trim().toUpperCase().split("\\s*,\\s*");
        int score = 0;

        for (String current : userAnswers) {
            if (this.answers.contains(current)) score++;
        }

        return score;
    }
}
