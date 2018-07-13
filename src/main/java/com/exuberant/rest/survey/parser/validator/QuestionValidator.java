package com.exuberant.rest.survey.parser.validator;

import com.exuberant.rest.survey.model.Question;

public interface QuestionValidator {

    boolean isComplete(Question question);
}
