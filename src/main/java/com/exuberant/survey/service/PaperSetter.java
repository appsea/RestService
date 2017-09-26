package com.exuberant.survey.service;

import com.exuberant.survey.model.Question;

import java.util.List;

public interface PaperSetter {

    List<Question> buildQuestionPaper() throws Exception;
}
