package com.exuberant.rest.survey.local;

import com.exuberant.rest.survey.model.Question;

import java.util.List;

public interface Finisher {

    List<Question> finish(List<Question> questions);
}
