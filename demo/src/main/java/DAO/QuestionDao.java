package DAO;

import DB.DBConnection;
import Models.Questions.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionDao {

    public List<Question> getQuestionsByQuizId(long quizId) throws SQLException {
        String sql = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY question_order ASC";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, quizId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Question question = mapResultSetToQuestion(rs);
                    questions.add(question);
                }
            }
        }

        return questions;
    }

    public List<Question> getQuestionsByQuizId(long quizId, boolean randomized) throws SQLException {
        List<Question> questions = getQuestionsByQuizId(quizId);

        if (randomized) {
            Collections.shuffle(questions);
        }

        return questions;
    }

    public Question getQuestionById(long questionId) throws SQLException {
        String sql = "SELECT * FROM questions WHERE question_id = ?";
        Question question = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, questionId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    question = mapResultSetToQuestion(rs);
                }
            }
        }

        return question;
    }

    public void createQuestion(Question question) throws SQLException {
        String sql = "INSERT INTO questions (quiz_id, question_type, question_text, image_url, time_limit, question_order) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, question.getQuizId());
            stmt.setString(2, question.getQuestionType());
            stmt.setString(3, question.getQuestion());
            stmt.setString(4, question.getImageUrl());

            if (question.getTimeLimit() != -1) {
                stmt.setLong(5, question.getTimeLimit());
            } else {
                stmt.setNull(5, Types.BIGINT);
            }

            stmt.setInt(6, question.getQuestionOrder());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating question failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    question.setQuestionId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating question failed, no ID obtained.");
                }
            }
        }
    }

    public boolean deleteQuestion(long questionId) throws SQLException {
        String sql = "DELETE FROM questions WHERE question_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, questionId);
            return stmt.executeUpdate() > 0;
        }
    }

    private Question mapResultSetToQuestion(ResultSet rs) throws SQLException {
        Question question = new Question(rs.getInt("question_id"), rs.getLong("quiz_id"),
                rs.getString("question_type"), rs.getString("question_text"), "", -1, rs.getInt("question_order"));
        question.setImageUrl(rs.getString("image_url"));

        long timeLimit = rs.getLong("time_limit");
        if (rs.wasNull()) {
            question.setTimeLimit(-1);
        } else {
            question.setTimeLimit(timeLimit);
        }

        question.setQuestionOrder(rs.getInt("question_order"));
        return question;
    }
}