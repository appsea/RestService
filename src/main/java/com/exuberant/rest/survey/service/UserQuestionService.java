package com.exuberant.rest.survey.service;

import com.exuberant.rest.survey.model.Question;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * Created by rakesh on 04-Nov-2017.
 */
public interface UserQuestionService {

    Question findWrongQuestion(User user);

    List<Question> findAllWrongQuestions(User user);

    void addWrongQuestion(User user, Question question);

}
