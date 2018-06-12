package com.exuberant.rest.survey.service;

import com.exuberant.rest.survey.model.JsonQuestion;
import com.exuberant.rest.survey.model.Option;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.model.QuestionId;

import java.util.List;

public interface QuestionService {

    Question findQuestion(QuestionId questionId);

    Question getNextQuestion();

    boolean isCorrect(QuestionId questionId, List<Option> options);

    boolean isCorrect(QuestionId questionId, Option option);

    List<JsonQuestion> getQuestions();

    int getVersion();
}
