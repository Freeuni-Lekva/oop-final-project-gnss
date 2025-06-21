package test.java;

import Questions.MultiAnswer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class MultiAnswerTests {

    @Test
    public void testCorrectAnswersMatchExactly() {
        List<String> correct = Arrays.asList("A", "B", "C", "D");
        MultiAnswer q = new MultiAnswer(1, 5, "Select all correct", true, correct);

        List<String> userInput = Arrays.asList("C", "A", "B", "D");

        assertEquals(0, q.getScore(userInput));

    }

    @Test
    public void testPartialAnswersAreIncorrect() {
        List<String> correct = Arrays.asList("A", "B", "C", "D");
        MultiAnswer q = new MultiAnswer(2, 5,"Select all correct", true, correct);

        List<String> userInput = Arrays.asList("A", "B", "C", "D");

        assertEquals(1, q.getScore(userInput));
    }

    @Test
    public void testExtraAnswersAreIncorrect() {
        List<String> correct = Arrays.asList("A", "B", "D", "C");
        MultiAnswer q = new MultiAnswer(3, 5,"Select all correct", false, correct);

        List<String> userInput = Arrays.asList("C", "B", "A", "D");

        assertEquals(1, q.getScore(userInput));
    }

    @Test
    public void testGetCorrectAnswers() {
        List<String> correct = Arrays.asList("A", "B", "C");
        MultiAnswer q = new MultiAnswer(4, 5, "Select all correct", false, correct);

        List<String> userInput = Arrays.asList("A", "B", "D");

        assertEquals(0, q.getScore(userInput));
    }

    @Test
    public void testDifferentSizeAnswerListWithoutOrder() {
        List<String> correct = Arrays.asList("A", "B", "C", "D");
        MultiAnswer q = new MultiAnswer(4, 5, "Select all correct", false, correct);

        List<String> userInput = Arrays.asList("A", "B", "D", "E", "F", "G", "H");

        assertEquals(0, q.getScore(userInput));
    }

    @Test
    public void testDifferentSizeAnswerListWithOrder() {
        List<String> correct = Arrays.asList("A", "B", "C", "D");
        MultiAnswer q = new MultiAnswer(4, 5, "Select all correct", true, correct);

        List<String> userInput = Arrays.asList("A", "B", "D", "E", "F", "G", "H");

        assertEquals(0, q.getScore(userInput));
    }

    @Test
    public void testWithoutOrderButDifferentSizes() {
        List<String> correct = Arrays.asList("A", "B", "C", "D");
        MultiAnswer q = new MultiAnswer(4, 5, "Select all correct", false, correct);

        List<String> userInput = Arrays.asList("A", "B");

        assertEquals(0, q.getScore(userInput));
    }

    @Test
    public void testStringInput() {
        List<String> correct = Arrays.asList("A");
        MultiAnswer q = new MultiAnswer(4, 5, "Select all correct", false, correct);

        String userInput = "A";

        assertThrows(UnsupportedOperationException.class, () -> {
            q.getScore(userInput);
        });
    }
}
