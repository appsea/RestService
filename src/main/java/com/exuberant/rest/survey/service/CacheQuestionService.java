package com.exuberant.rest.survey.service;

import com.exuberant.rest.survey.exam.question.QuestionBanker;
import com.exuberant.rest.survey.model.JsonQuestion;
import com.exuberant.rest.survey.model.Option;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.model.QuestionId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CacheQuestionService implements QuestionService{

    @Autowired
    private QuestionBanker questionBanker;

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
        return null;
    }

    @Override
    public int getVersion() {
        return 0;
    }
}
