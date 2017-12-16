package com.exuberant.rest.survey.model;

import java.util.Collections;

public class QuestionWrapper {

    private Question question;

    public QuestionWrapper(Question question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        QuestionWrapper other = (QuestionWrapper) obj;
        boolean equals = this.question.getQuestion().equals(other.question.getQuestion()) && this.question.getOptions().equals(other.question.getOptions());
        if (equals) {
            if(!this.question.getId().equals(other.question.getId())){
                System.err.println(this.question.getId() + " = " + other.question.getId());
            }
        }
        return equals;
    }
}
