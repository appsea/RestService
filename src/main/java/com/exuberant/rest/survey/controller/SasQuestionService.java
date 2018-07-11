package com.exuberant.rest.survey.controller;

import com.exuberant.rest.survey.exam.sas.SasQuestionBanker;
import com.exuberant.rest.survey.model.*;
import com.exuberant.rest.survey.parser.QuestionParser;
import com.exuberant.rest.survey.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SasQuestionService implements QuestionService {

    private JsonQuestions jsonQuestions = new JsonQuestions(Collections.emptyList(), -1, -1, true);

    @Autowired
    private QuestionParser questionParser;

    @PostConstruct
    public void init() throws Exception {
        jsonQuestions = this.readQuestion();
    }

    private JsonQuestions readQuestion() throws Exception {
        SasQuestionBanker sasQuestionBanker = new SasQuestionBanker(questionParser);
        List<Question> allQuestions = sasQuestionBanker.getAllQuestions();
        Set<QuestionWrapper> wrappers = new HashSet<>();
        for (Question question : allQuestions) {
            QuestionWrapper newQ = new QuestionWrapper(question);
            if (!wrappers.contains(newQ)) {
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
        int version = 13;
        int playStoreVersion = 10001;
        JsonQuestions jsonQuestions = new JsonQuestions(allQuestions, version, playStoreVersion, false);
        return jsonQuestions;
    }

    @Override
    public Question findQuestion(QuestionId questionId) {
        return null;
    }

    @Override
    public Question getNextQuestion() {
        return null;
    }

    @Override
    public boolean isCorrect(QuestionId questionId, List<Option> options) {
        return false;
    }

    @Override
    public boolean isCorrect(QuestionId questionId, Option option) {
        return false;
    }

    @Override
    public List<JsonQuestion> getQuestions() {
        return this.jsonQuestions.getQuestions();
    }

    @Override
    public int getVersion() {
        return this.jsonQuestions.getQuestionVersion();
    }
}
