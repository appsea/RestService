package com.exuberant.rest.survey.model;

import java.util.Set;

public class Category {
    private String name;
    private Set<Integer> questionNumbers;

    public Category(String name, Set<Integer> questionNumbers) {
        this.name = name;
        this.questionNumbers = questionNumbers;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getQuestionNumbers() {
        return questionNumbers;
    }
}
