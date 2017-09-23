package com.exuberant.survey.service;

import com.exuberant.survey.model.Option;
import com.exuberant.survey.model.Question;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by rakesh on 22-Sep-2017.
 */
public class QuestionParser {
    private Pattern newQuestionPattern;
    private final Pattern optionPattern;
    private final Pattern answerPattern;

    private String newQuestionRegex = "^QUESTION NO:.*";
    private String optionRegex = "^[A-D][.]\\s.*";
    private String answerRegex = "^Answer. .*";
    private List<Question> failedQuestions = new ArrayList<>();

    public QuestionParser() {
        newQuestionPattern = Pattern.compile(newQuestionRegex);
        optionPattern = Pattern.compile(optionRegex);
        answerPattern = Pattern.compile(answerRegex);
    }

    public List<Question> parse(String fileName) throws URISyntaxException, IOException {
        List<Question> questions = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource("211.txt").toURI()));
        Question question = null;
        StringBuilder previousOptionLine = new StringBuilder();
        boolean hasOptionStarted = false;
        for (String line : lines) {
            try {
                if (isNewQuestion(line)) {
                    question = new Question(fileName, line);
                    hasOptionStarted = false;
                    previousOptionLine = new StringBuilder();
                } else if (question != null) {
                    if (isAnswer(line)) {
                        question.addOption(previousOptionLine.toString());
                        question.addAnswer(line.substring(8));
                    } else if (isOption(line)) {
                        hasOptionStarted = true;
                        if (previousOptionLine.length() != 0) {
                            question.addOption(previousOptionLine.toString());
                        }
                        previousOptionLine = new StringBuilder(line);
                    } else if (hasOptionStarted) {
                        previousOptionLine.append("\n").append(line);
                    } else {
                        question.appendQuestion(line);
                    }
                }
            } catch (Exception e) {
                if (question != null) {
                    question.setErrorMessage(e.getMessage());
                    question.setException(e);
                    failedQuestions.add(question);
                }
            }
            if (question != null && question.isComplete()) {
                questions.add(question);
                question = null;
            }
        }
        return questions;
    }

    private boolean isAnswer(String line) {
        return answerPattern.matcher(line).matches();
    }

    private boolean isOption(String line) {
        return optionPattern.matcher(line).matches();
    }

    private boolean isNewQuestion(String line) {
        return newQuestionPattern.matcher(line).matches();
    }
}
