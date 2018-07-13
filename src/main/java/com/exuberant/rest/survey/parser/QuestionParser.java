package com.exuberant.rest.survey.parser;

import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.Question;

import java.util.List;

public interface QuestionParser {
    List<Question> parse(QuestionBank questionBank) throws Exception;
}
