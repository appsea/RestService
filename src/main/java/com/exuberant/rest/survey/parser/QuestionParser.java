package com.exuberant.rest.survey.parser;

import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.Question;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rakesh on 22-Sep-2017.
 */
@Service
public class QuestionParser {

    public static final Log log = LogFactory.getLog(QuestionParser.class);
    public static final Set<String> ignoredLines = new HashSet<>();
    private String re = "Description:(?!\\s )";
    private PatternParser patternParser;
    private List<Question> failedQuestions = new ArrayList<>();

    public List<Question> parse(QuestionBank questionBank) throws Exception {
        List<Question> questions = new ArrayList<>();
        String fileName = questionBank.getFileName();
        this.patternParser = PatternParserFactory.getPatternParser(fileName);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
        } catch (IOException | URISyntaxException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Invalid File Path: " + fileName);
        }
        Question question = null;
        StringBuilder previousOptionLine = new StringBuilder();
        boolean hasOptionStarted = false;
        boolean hasDescriptionStarted = false;
        for (String line : lines) {
            try {
                if (!patternParser.ignoreLine(line) || patternParser.isNewQuestion(line)) {
                    if (patternParser.isNewQuestion(line)) {
                        if (question != null && question.isComplete()) {
                            questions.add(question);
                            question = null;
                        } else if (question != null && !question.isComplete()) {
                            System.err.println("Incomplete: " + question.getOptions().size() + (question.getOptions().giveCorrectAnswers()) + " que: " + question);
                        }
                        String questionNumber = patternParser.extractQuestionNumber(line);
                        question = new Question(fileName, questionNumber);
                        String questionString = patternParser.stripQuestionNumber(line);
                        if (!StringUtils.isEmpty(questionString)) {
                            question.appendQuestion(questionString.trim());
                        }
                        hasOptionStarted = false;
                        hasDescriptionStarted = false;
                        previousOptionLine = new StringBuilder();
                    } else if (question != null) {
                        if (!hasDescriptionStarted && patternParser.isAnswer(line)) {
                            question.addOption(previousOptionLine.toString());
                            try {
                                question.addAnswer(line.substring(8).trim());
                            } catch (Exception e) {
                                System.err.println("Exce");
                            }
                        } else if (!hasDescriptionStarted && patternParser.isOption(line)) {
                            hasOptionStarted = true;
                            if (previousOptionLine.length() != 0) {
                                question.addOption(previousOptionLine.toString());
                            }
                            previousOptionLine = new StringBuilder(line);
                        } else if (patternParser.isDescription(line) || hasDescriptionStarted) {
                            question.appendExplanation(line.replaceFirst("Description:", "").trim());
                            hasDescriptionStarted = true;
                        } else if (!hasDescriptionStarted && hasOptionStarted) {
                            previousOptionLine.append("\n").append(line);
                        } else {
                            question.appendQuestion(line.trim());
                        }
                    }
                } else {
                    ignoredLines.add(line);
                }
            } catch (Exception e) {
                if (question != null) {
                    question.setErrorMessage(e.getMessage());
                    question.setException(e);
                    failedQuestions.add(question);
                }
            }
        }
        if (question != null && question.isComplete()) {
            questions.add(question);
        } else {
            System.err.println("Last Question Not Added....");
        }
        validateQuestions(questions, questionBank.getTotalQuestions());
        return questions;
    }

    private void validateQuestions(List<Question> questions, int expectedQuestions) throws Exception {
        if (questions.size() != expectedQuestions) {
            System.err.println("Expected Question were " + expectedQuestions + " but was " + questions.size());
        }
    }

    public void showIgnoredLines() {
        for (String ignoredLine : ignoredLines) {
            log.info(ignoredLine);
        }
    }
}