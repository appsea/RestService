package com.exuberant.rest.survey.model;

import java.util.Set;

public class Category {
    private String icon;
    private String name;
    private Set<Integer> questionNumbers;

    public Category(String icon, String name, Set<Integer> questionNumbers) {
        this.icon = icon;
        this.name = name;
        this.questionNumbers = questionNumbers;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getQuestionNumbers() {
        return questionNumbers;
    }
}
