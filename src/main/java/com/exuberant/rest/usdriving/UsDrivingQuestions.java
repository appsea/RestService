package com.exuberant.rest.usdriving;

import com.exuberant.rest.usdriving.model.Card;
import com.exuberant.rest.usdriving.model.CardTopic;
import com.exuberant.rest.usdriving.model.Question;
import com.exuberant.rest.usdriving.model.Topic;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sqlitetutorial.net
 */
public class UsDrivingQuestions {

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\rakeshgirase\\Downloads\\usdriving.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public List<CardTopic> cardTopics() {
        List<CardTopic> cardTopics = new ArrayList<>();
        String sql = "SELECT id, name, description, numberOfCard, url FROM USA_LearningTopics";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                CardTopic cardTopic = new CardTopic();
                cardTopic.setId(rs.getInt("id"));
                cardTopic.setName(rs.getString("name"));
                cardTopic.setDescription(rs.getString("description"));
                cardTopic.setNumberOfCard(rs.getInt("numberOfCard"));
                cardTopic.setUrl(rs.getString("url"));
                cardTopics.add(cardTopic);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cardTopics;
    }

    /**
     * select all rows in the warehouses table
     */
    public List<Card> learningCards() {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT id, topicId, term, imageName, imageData, definition   FROM USA_LearningCards";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getInt("id"));
                card.setTopicId(rs.getInt("topicId"));
                card.setTerm(rs.getString("term"));
                card.setImageName(rs.getString("imageName"));
                card.setImageData(rs.getString("imageData"));
                card.setDefinition(rs.getString("definition"));
                cards.add(card);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cards;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UsDrivingQuestions app = new UsDrivingQuestions();
        List<Card> cards = app.learningCards();
        System.err.println("cards" + cards.size());

        List<CardTopic> cardTopics = app.cardTopics();
        System.err.println("cardTopics" + cardTopics.size());

        List<Topic> states = app.states();
        System.err.println("states" + states.size());

        List<Question> questions = app.questions();
        System.err.println("questions" + questions.size());

        List<Topic> topics = app.topics();
        System.err.println("topics" + topics.size());

    }

    private List<Topic> states() {
        List<Topic> states = new ArrayList<>();
        String sql = "SELECT id, stateName FROM USA_States";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Topic state = new Topic();
                state.setId(rs.getInt("id"));
                state.setName(rs.getString("stateName"));
                states.add(state);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return states;
    }

    private List<Question> questions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM USA_TestQuestions";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setTopicId(rs.getInt("topicId"));
                question.setQuestion(rs.getString("question"));
                question.setImageName(rs.getString("imageName"));
                question.setImageData(rs.getString("imageData"));
                question.setA(rs.getString("a"));
                question.setB(rs.getString("b"));
                question.setC(rs.getString("c"));
                question.setD(rs.getString("d"));
                question.setCorrectAnswer(rs.getString("correctAnswer"));
                questions.add(question);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }



    private List<Topic> topics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM USA_TestTopics";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id"));
                topic.setName(rs.getString("name"));
                topic.setNumberOfQuestion(rs.getInt("numberOfQuestion"));
                topic.setUrl(rs.getString("url"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return topics;
    }

}