package com.exuberant.survey;

import com.exuberant.survey.model.Question;
import com.exuberant.survey.service.QuestionParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rakesh on 21-Sep-2017.
 */
public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            App app = new App();
            app.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void go() throws Exception {
        Collection<QuestionBank> bank = new ArrayList<>();
        bank.add(new QuestionBank("A00-211qa204-20170130-17520396.txt", 204));
        bank.add(new QuestionBank("1A00-201-Q&A-CertMagic-20170130-175206262.txt", 123));
        bank.add(new QuestionBank("A00-201-Fx-20170130-175207599.txt", 140));
        QuestionParser questionParser = new QuestionParser();
        for (QuestionBank questionBank : bank) {
            List<Question> firstQuestions = questionParser.parse(questionBank.getFileName());
            validateQuestions(firstQuestions, questionBank.getTotalQuestions());
        }
    }

    private void validateQuestions(List<Question> questions, int expectedQuestions) throws Exception {
        if(!areValid(questions)){
            System.err.println("Invalid Questions Found!!!");
        }
        if(questions.size()!= expectedQuestions){
            throw new Exception("Expected Question were "+ expectedQuestions + " but was " + questions.size());
        }
    }

    private boolean areValid(List<Question> questions) {
        boolean isValid = true;
        for (Question question : questions) {
            if (!question.isComplete()) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}
