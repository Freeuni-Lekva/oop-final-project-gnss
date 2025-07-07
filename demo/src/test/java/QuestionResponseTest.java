import Models.Questions.QuestionResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionResponseTest {

    @Test
    void testEmpty(){
        Set<String> correctAnswers = new HashSet<>();
        QuestionResponse question = new QuestionResponse(77, "QuestionResponse", "Empty set?", correctAnswers , 1);
        assertEquals(0 , question.getScore("yes"));
        assertNull(question.getCorrectAnswers());
    }
    @Test
    void testSimple() {
        Set<String> correctAnswers = new HashSet<>();
        correctAnswers.add("BEST LANGUAGE");
        correctAnswers.add("JAVA:)");

        Set<String> fakeAnswers = new HashSet<>();
        fakeAnswers.add("PF");

        QuestionResponse question = new QuestionResponse(1, "QuestionResponse", "What is Java?", correctAnswers , 5);

        assertEquals(5, question.getScore("Java"));
        assertNotEquals(question.getCorrectAnswers(), fakeAnswers);
    }
    @Test
    void testMultipleCorrectAnswers() {
        Set<String> correctAnswers = new HashSet<>();
        correctAnswers.add("4");
        correctAnswers.add("four");
        correctAnswers.add("FOUR!");

        QuestionResponse question = new QuestionResponse(2, "QuestionResponse", "2 + 2 = ?", correctAnswers, 2);

        assertNotEquals("2+2=?", question.getQuestion());
        assertEquals(2, question.getScore("Four"));
        assertEquals(2, question.getScore("4"));
        assertEquals(0, question.getScore(null));
        assertEquals(0, question.getScore("five"));
    }
    @Test
    void testMultipleQuestions(){
        Set<String> firstCorrectAnswers = new HashSet<>();
        firstCorrectAnswers.add("BEST CM");
        firstCorrectAnswers.add("Nigger");

        Set<String> secondCorrectAnswers = new HashSet<>();
        secondCorrectAnswers.add("doe");
        secondCorrectAnswers.add("Maria Dolores dosSantos Aveiro ");

        QuestionResponse firstQuestion = new QuestionResponse(3, "QuestionResponse", "Jude bellingham is ", firstCorrectAnswers, 9);
        QuestionResponse secondQuestion = new QuestionResponse(4, "QuestionResponse", "What is the name of the goat’s mother?", secondCorrectAnswers, 9);

        assertEquals(0, secondQuestion.getScore("Nigger"));
        assertEquals(0, firstQuestion.getScore("Best central midfielder"));
        assertEquals(9, secondQuestion.getScore("mariadoloresdossantosaveiro"));

        assertEquals(firstQuestion.getQuestionType(),secondQuestion.getQuestionType());
        assertNotEquals(firstQuestion.getId(),secondQuestion.getId());
    }

    /**
     * Tests that the constructor of QuestionResponse throws an IllegalArgumentException
     * when the question string is null or empty (after trimming).
     */
    @Test
    void testEmptyOrNullQuestionException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new QuestionResponse(1, "QuestionResponse", null, new HashSet<>(Collections.singletonList("answer")), 5);
        });
        assertEquals("Question text must not be null or empty.", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new QuestionResponse(1, "QuestionResponse", "   ", new HashSet<>(Collections.singletonList("answer")), 5);
        });
        assertEquals("Question text must not be null or empty.", ex2.getMessage());
    }

    /**
     * Tests that the constructor of QuestionResponse throws an IllegalArgumentException
     * when the provided question ID is zero or negative.
     */
    @Test
    void testInvalidIdException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new QuestionResponse(0, "QuestionResponse", "Valid question", new HashSet<>(Collections.singletonList("answer")), 5);
        });
        assertEquals("ID must be a positive integer.", ex.getMessage());
    }
}
