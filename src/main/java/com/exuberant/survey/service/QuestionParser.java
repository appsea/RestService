package com.exuberant.survey.service;

import com.exuberant.survey.model.Question;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 22-Sep-2017.
 */
public class QuestionParser {

    private PatternParser patternParser;

    private List<Question> failedQuestions = new ArrayList<>();

    public List<Question> parse(String fileName) throws URISyntaxException, IOException {
        List<Question> questions = new ArrayList<>();
        this.patternParser = PatternParserFactory.getPatternParser(fileName);
        List<String> lines = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
        Question question = null;
        StringBuilder previousOptionLine = new StringBuilder();
        boolean hasOptionStarted = false;
        for (String line : lines) {
            try {
                if (patternParser.isNewQuestion(line)) {
                    String questionNumber = patternParser.extractQuestionNumber(line);
                    question = new Question(fileName, questionNumber);
                    String questionString = patternParser.stripQuestionNumber(line);
                    question.appendQuestion(questionString);
                    hasOptionStarted = false;
                    previousOptionLine = new StringBuilder();
                } else if (question != null) {
                    if (patternParser.isAnswer(line)) {
                        question.addOption(previousOptionLine.toString());
                        question.addAnswer(line.substring(8));
                    } else if (patternParser.isOption(line)) {
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
}