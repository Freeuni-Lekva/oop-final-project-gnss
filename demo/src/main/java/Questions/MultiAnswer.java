package Questions;

import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MultiAnswer extends Question{
    private final boolean orderIsImportant;
    private final List<String> correctAnswer;
    /**
     * Constructs a Question with the specified id, type, and text.
     *
     * @param id               unique identifier for the question
     * @param question_type_id identifier for the type of question
     * @param question         the text content of the question
     */
    public MultiAnswer(int id, int question_type_id, String question, boolean orderIsImportant, List<String> correctAnswer) {
        super(id, question_type_id, question);
        this.orderIsImportant = orderIsImportant;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public Set<String> getCorrectAnswers() {
        return new HashSet<>(correctAnswer);
    }

    @Override
    public int getScore(String answer) {
        throw new UnsupportedOperationException("Use getScore List<String> for MultiAnswer questions.");
    }

    public int getScore(List<String> answer) {
        if (answer.size() != correctAnswer.size()) return 0;

        if (orderIsImportant) {
            for (int i = 0; i < correctAnswer.size(); ++i) {
                if (!answer.get(i).equalsIgnoreCase(correctAnswer.get(i))) {
                    return 0;
                }
            }
        } else {
            HashSet<String> correctAnswers = correctAnswer.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(HashSet::new));

            for (String writtenWord : answer) {
                if (!correctAnswers.contains(writtenWord.toLowerCase())) return 0;
            }

        }

        return 1;
    }
}
