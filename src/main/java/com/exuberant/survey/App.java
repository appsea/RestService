package com.exuberant.survey;

import com.exuberant.survey.model.Question;
import com.exuberant.survey.service.QuestionParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by rakesh on 21-Sep-2017.
 */
public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            App app = new App();
            app.go();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void go() throws IOException, URISyntaxException {
        QuestionParser questionParser = new QuestionParser();
        List<Question> questions = questionParser.parse("211.txt");
        if(!areValid(questions)){
            System.err.println("Invalid Questions Found!!!");
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
