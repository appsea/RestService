package com.exuberant.rest.survey;

import com.exuberant.rest.survey.exam.sas.SasQuestionBanker;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.model.QuestionWrapper;
import com.exuberant.rest.survey.parser.QuestionParser;
import com.exuberant.rest.survey.service.RandomPaperSetter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rakesh on 21-Sep-2017.
 */
public class Main {

    public static void main(String[] args) {
        try {
            Main main = new Main();
            //main.startExam();
            main.analyseQuestions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void analyseQuestions() throws Exception {
        SasQuestionBanker sasQuestionBanker = new SasQuestionBanker(new QuestionParser());
        List<Question> allQuestions = sasQuestionBanker.getAllQuestions();
        Set<QuestionWrapper> wrappers = new HashSet<>();
        for (Question question : allQuestions) {
            QuestionWrapper newQ = new QuestionWrapper(question);
            if(!wrappers.contains(newQ)){
                wrappers.add(newQ);
            }
        }
    }

    private void startExam() throws Exception {
        Examiner examiner = new Examiner(new RandomPaperSetter(new SasQuestionBanker(new QuestionParser())));
        examiner.beginExam();
    }
}
