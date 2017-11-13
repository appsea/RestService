package com.exuberant.rest.survey.service;

import com.exuberant.rest.survey.model.Question;

import java.util.List;

public interface PaperSetter {

    List<Question> buildQuestionPaper() throws Exception;

    Question pollNextQuestion();
}
