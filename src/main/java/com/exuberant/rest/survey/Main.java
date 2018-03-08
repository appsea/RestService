package com.exuberant.rest.survey;

import com.exuberant.rest.survey.exam.sas.SasQuestionBanker;
import com.exuberant.rest.survey.model.JsonQuestions;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.model.QuestionWrapper;
import com.exuberant.rest.survey.parser.QuestionParser;
import com.exuberant.rest.survey.service.RandomPaperSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        allQuestions.clear();
        int count = 0;
        for (QuestionWrapper wrapper : wrappers) {
            Question question = wrapper.getQuestion();
            question.setNumber(++count);
            allQuestions.add(question);
        }
        System.err.println("TQ: " + wrappers.size());
        System.err.println("TQ: " + allQuestions.size());
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources", "sas_questions.json");
        System.err.println("Created: " + path);
        JsonQuestions jsonQuestions = new JsonQuestions();
        for (Question question : allQuestions) {
            jsonQuestions.addQuestion(question);
        }
        System.err.println("Without: " + jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).count());
        Files.write(path, objectMapper.writeValueAsString(jsonQuestions).getBytes());
    }

    private void startExam() throws Exception {
        Examiner examiner = new Examiner(new RandomPaperSetter(new SasQuestionBanker(new QuestionParser())));
        examiner.beginExam();
    }
}
