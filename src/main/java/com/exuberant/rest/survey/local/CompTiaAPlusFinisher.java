package com.exuberant.rest.survey.local;

import com.exuberant.rest.survey.model.Question;

import java.util.List;

public class CompTiaAPlusFinisher implements Finisher{

    @Override
    public List<Question> finish(List<Question> questions) {
        System.err.println("Inside CompTiaAPlusFinisher......");
        for (Question question : questions) {
            question.setDescription(question.getDescription().replaceAll("\n", " ").trim());
        }
        return questions;
    }
}
