package com.exuberant.survey.parser;

import com.exuberant.survey.QuestionBank;
import com.exuberant.survey.model.Question;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    
    private PatternParser patternParser;

    private List<Question> failedQuestions = new ArrayList<>();
    public static final Set<String> ignoredLines = new HashSet<>();

    public List<Question> parse(QuestionBank questionBank) throws Exception {
        List<Question> questions = new ArrayList<>();
        String fileName = questionBank.getFileName();
        this.patternParser = PatternParserFactory.getPatternParser(fileName);
        List<String> lines = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
        Question question = null;
        StringBuilder previousOptionLine = new StringBuilder();
        boolean hasOptionStarted = false;
        for (String line : lines) {
            try {
                if (!patternParser.ignoreLine(line)||patternParser.isNewQuestion(line)) {
                    if (patternParser.isNewQuestion(line)) {
                        String questionNumber = patternParser.extractQuestionNumber(line);
                        question = new Question(fileName, questionNumber);
                        String questionString = patternParser.stripQuestionNumber(line);
                        if(!StringUtils.isEmpty(questionString)){
                            question.appendQuestion(questionString.trim());
                        }
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
            if (question != null && question.isComplete()) {
                questions.add(question);
                question = null;
            }
        }
        validateQuestions(questions, questionBank.getTotalQuestions());
        return questions;
    }

    private void validateQuestions(List<Question> questions, int expectedQuestions) throws Exception {
        if (questions.size() != expectedQuestions) {
            throw new Exception("Expected Question were " + expectedQuestions + " but was " + questions.size());
        }
    }

    public void showIgnoredLines() {
        for (String ignoredLine : ignoredLines) {
            log.info(ignoredLine);
        }
    }
}