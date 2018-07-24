package com.exuberant.rest.survey.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by rakesh on 22-Sep-2017.
 */
public class Question {

    public static final Log log = LogFactory.getLog(Question.class);

    private String fileName;
    private String number;
    private StringBuilder description = new StringBuilder();
    private StringBuilder explanation = new StringBuilder();
    private Options options = new Options();
    private String errorMessage;
    private Exception exception;
    private String answer;
    private String submittedAnswer = new String();
    private String category;
    private Collection<Object> waste = new ArrayList<>();

    public Question(String fileName, String questionNumber) {
        this.fileName = fileName;
        this.number = questionNumber;
    }

    public Options getOptions() {
        return options;
    }

    public String getDescription() {
        return description.toString().replaceAll("\n$", "");
    }

    public String getExplanation() {
        return explanation.toString().replaceAll("\n$", "");
    }

    @JsonIgnore
    public boolean isComplete() {
        return description != null && !description.toString().equals("") && options.areValid();
    }

    @JsonIgnore
    public boolean isCorrect(String... answers) {
        return options.isCorrect(answers);
    }

    @JsonIgnore
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
        if (!StringUtils.isEmpty(option)) {
            if (!isComplete()) {
                options.add(new Option(option));
            } else {
                waste.add(option);
            }
        }
    }

    public void addAnswer(String tags) {
        tags = tags.replace(".", "").trim();
        this.setAnswer(tags);
        String[] answers = tags.split(",");
        options.addAnswer(Arrays.asList(answers));
    }

    public void setCategory(String category) {
        this.category = category.trim();
    }

    @JsonIgnore
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void appendQuestion(String question) {
        this.description.append(question).append("\n");
    }

    public void appendExplanation(String description) {
        this.explanation.append(description).append("\n");
    }

    @JsonIgnore
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(description);
        stringBuilder.append(options);
        return stringBuilder.toString();
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void printWaste() {
        if (waste.size() > 0) {
            log.info("File Name: " + fileName + ", " + number);
            for (Object o : waste) {
                log.info(o);
            }
        }
        if (exception != null) {
            log.info("File Name: " + fileName + ", " + number);
            log.info("Exception: " + exception.getMessage());
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

    @JsonIgnore
    public boolean hasAnsweredCorrectly() {
        return options.isCorrect(submittedAnswer.split(","));
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    public String getSubmittedAnswer() {
        return submittedAnswer;
    }

    public void setSubmittedAnswer(String submittedAnswer) {
        this.submittedAnswer = submittedAnswer != null ? submittedAnswer.toUpperCase() : "";
    }

    public void setNumber(int number) {
        this.number = Integer.toString(number);
    }

    public String getNumber() {
        return number;
    }

    public String getCategory() {
        return category;
    }
}
