package com.exuberant.rest.survey;

import com.exuberant.rest.survey.exam.sas.SasQuestionBanker;
import com.exuberant.rest.survey.parser.QuestionParser;
import com.exuberant.rest.survey.service.RandomPaperSetter;

/**
 * Created by rakesh on 21-Sep-2017.
 */
public class App {

    public static void main(String[] args) {
        try {
            App app = new App();
            app.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void go() throws Exception {
        Examiner examiner = new Examiner(new RandomPaperSetter(new SasQuestionBanker(new QuestionParser())));
        examiner.beginExam();
    }
}
