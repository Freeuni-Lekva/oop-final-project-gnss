package Models.Questions;

import java.util.Set;

/**
 * Represents an abstract base class for different types of questions.
 * Each question has a unique identifier, a type identifier, and a question text.
 * Subclasses must implement methods to provide correct answers and scoring logic.
 */


public  class Question {
    private int id;
    private final String question_type;
    private final String question;
    private String image_url;
    private long time_limit;
    private int question_order;
    private long quiz_id;

    /**
     * Constructs a Question with the specified id, type, and text.
     * Validates that the ID and question type ID are positive integers,
     * and that the question text is not null or empty.
     *
     * @param id unique identifier for the question
     * @param question_type identifier for the type of question
     * @param question the text content of the question
     */
    public Question(int id, long quiz_id, String question_type, String question, String image_url, long time_limit, int question_order) {
        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text must not be null or empty.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive integer.");
        }
        this.question = question;
        this.id = id;
        this.quiz_id = quiz_id;
        this.question_type = question_type;
        this.image_url = image_url;
        this.time_limit = time_limit;
        this.question_order = question_order;
    }

    /**
     *
     *  Constructor for child classes
     *
     */

    public Question(int id, String typeId, String question) {
        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text must not be null or empty.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive integer.");
        }
        this.id = id;
        this.question_type = typeId;
        this.question = question;
    }

    /**
     *
     * Sets question id
     *
     */

    public void setQuestionId(int id) {
        this.id = id; 
    }

    /**
     *
     * Returns quiz id
     *
     */

    public long getQuizId() {
        return quiz_id;
    }

    /**
     * Returns the text of the question.
     *
     * @return the question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns a set of all acceptable correct answers for this question.
     *
     * @return a set containing correct answers as strings
     */
    public Set<String> getCorrectAnswers() {
        return null;
    }

    /**
     * Calculates the score awarded for a given answer.
     * The implementation defines how answers are evaluated and scored.
     *
     * @param answer the answer provided by a user
     * @return an integer score (e.g., 1 for correct, 0 for incorrect)
     */
    public int getScore(String answer) {
        return 0;
    }

    /**
     * Returns the unique identifier of this question.
     *
     * @return the question ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the type identifier of this question.
     *
     * @return the question type ID
     */
    public String getQuestionType() {
        return question_type;
    }

    /**
     * Returns the URL of an image associated with the question.
     *
     * @return the image URL, or null if no image is associated
     */
    public String getImageUrl() {
        return image_url;
    }

    /**
     * Sets the URL of an image associated with the question.
     *
     * @param image_url the image URL to set
     */
    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    /**
     * Returns the time limit for the question.
     *
     * @return the time limit in milliseconds, or 0/negative if no limit
     */
    public long getTimeLimit() {
        return time_limit;
    }

    /**
     * Sets the time limit for the question.
     *
     * @param time_limit the time limit in milliseconds
     */
    public void setTimeLimit(long time_limit) {
        this.time_limit = time_limit;
    }

    /**
     * Returns the display order of the question within a quiz.
     *
     * @return the question order
     */
    public int getQuestionOrder() {
        return question_order;
    }

    /**
     * Sets the display order of the question within a quiz.
     *
     * @param question_order the question order to set
     */
    public void setQuestionOrder(int question_order) {
        this.question_order = question_order;
    }
}