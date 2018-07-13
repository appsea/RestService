package com.exuberant.rest.survey.parser.validator;

import com.exuberant.rest.survey.model.Question;

public class TetQuestionValidator implements QuestionValidator {

    @Override
    public boolean isComplete(Question question) {
        return question.getDescription() != null && !question.getDescription().toString().equals("") && question.getOptions().includesAnswer();
    }
}
