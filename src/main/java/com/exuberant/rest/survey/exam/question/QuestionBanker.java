package com.exuberant.rest.survey.exam.question;

import com.exuberant.rest.survey.model.Question;

import java.util.List;

/**
 * Created by rakesh on 26-Sep-2017.
 */
public interface QuestionBanker {
    List<Question> getAllQuestions() throws Exception;
}