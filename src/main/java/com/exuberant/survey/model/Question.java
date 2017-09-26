package com.exuberant.survey.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by rakesh on 22-Sep-2017.
 */
public class Question {
    private String fileName;
    private String number;
    private StringBuilder question = new StringBuilder();
    private Options options = new Options();
    private String errorMessage;
    private Exception exception;
    private String answer;
    private String submittedAnswer = new String();
    private Collection<Object> waste = new ArrayList<>();

    public Options getOptions() {
        return options;
    }

    public Question(String fileName, String questionNumber) {
        this.fileName = fileName;
        this.number = questionNumber;
    }

    public boolean isComplete() {
        return question != null && options.areValid();
    }

    public boolean isCorrect(String... answers) {
        return options.isCorrect(answers);
    }

    public boolean isAnsweredCorrectly() {
        return options.isCorrect(submittedAnswer.split(","));
    }

    public void addOption(Option option) {
        if (!isComplete()) {
            options.add(option);
        } else {
            waste.add(option);
        }
    }

    public void addOption(String option) {
        if (!isComplete()) {
            options.add(new Option(option));
        } else {
            waste.add(option);
        }
    }

    public void addAnswer(String tags) {
        this.setAnswer(tags);
        String[] answers = tags.split(",");
        options.addAnswer(Arrays.asList(answers));
    }

    public String getAnswer() {
        return answer;
    }

    public void appendQuestion(String question) {
        this.question.append(question).append("\n");
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(question);
        stringBuilder.append(options);
        return stringBuilder.toString();
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSubmittedAnswer(String submittedAnswer) {
        this.submittedAnswer = submittedAnswer != null ? submittedAnswer.toUpperCase() : "";
    }

    public void printWaste() {
        if (waste.size() > 0) {
            System.err.println("File Name: " + fileName + ", " + number);
            for (Object o : waste) {
                System.err.println(o);
            }
        }
        if (exception != null) {
            System.err.println("File Name: " + fileName + ", " + number);
            System.err.println("Exception: " + exception.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (fileName != null ? !fileName.equals(question.fileName) : question.fileName != null) return false;
        return number != null ? number.equals(question.number) : question.number == null;
    }

    public boolean hasAnsweredCorrectly() {
        return options.isCorrect(submittedAnswer.split(","));
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    public String getSubmittedAnswer() {
        return submittedAnswer;
    }
}
