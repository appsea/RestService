package com.exuberant.rest.survey.parser;

import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.parser.validator.QuestionValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by rakesh on 22-Sep-2017.
 */
@Service
public class GenericQuestionParser implements QuestionParser {

    public static final Log log = LogFactory.getLog(GenericQuestionParser.class);
    public static final Set<String> ignoredLines = new HashSet<>();
    private PatternParser patternParser;
    private List<Question> failedQuestions = new ArrayList<>();

    private ResourceLoader resourceLoader;
    private QuestionValidator questionValidator;

    @Autowired
    public GenericQuestionParser(ResourceLoader resourceLoader, QuestionValidator questionValidator) {
        this.resourceLoader = resourceLoader;
        this.questionValidator = questionValidator;
    }

    @Override
    public List<Question> parse(QuestionBank questionBank) throws Exception {
        List<Question> questions = readAllQuestions(questionBank);
        /*Uncomment to find duplicate questions*/
        /*Set<QuestionWrapper> wrappers = new HashSet<>();
        for (Question question : questions) {
            QuestionWrapper newQ = new QuestionWrapper(question);
            if(!wrappers.contains(newQ)){
                wrappers.add(newQ);
            }
        }
        questions.clear();*/
        int count = 0;
        for (Question question : questions) {
            question.setNumber(++count);
            //questions.add(question);
        }
        System.err.println("TQ: " + questions.size());
        return questions;
    }

    private List<Question> readAllQuestions(QuestionBank questionBank) throws Exception {
        List<Question> questions = new CopyOnWriteArrayList<>();
        String fileName = questionBank.getInputFile();
        this.patternParser = PatternParserFactory.getPatternParser(fileName);
        List<String> lines = new ArrayList<>();
        try {
            String filePath = "classpath:" + fileName;
            Resource resource = resourceLoader.getResource(filePath);
            InputStream is = resource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            while (bufferedReader.ready()) {
                lines.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Invalid File Path: " + fileName + e.getMessage());
        }
        Question question = null;
        StringBuilder previousOptionLine = new StringBuilder();
        boolean hasOptionStarted = false;
        boolean hasDescriptionStarted = false;
        for (String line : lines) {
            try {
                if (!patternParser.ignoreLine(line) || patternParser.isNewQuestion(line)) {
                    if (patternParser.isNewQuestion(line)) {
                        if (question != null && questionValidator.isComplete(question)) {
                            questions.add(question);
                            question = null;
                        } else if (question != null && !questionValidator.isComplete(question)) {
                            System.err.println("Incomplete: " + question.getOptions().size() + (question.getOptions().giveCorrectAnswers()) + " que: " + question);
                        }
                        String questionNumber = patternParser.extractQuestionNumber(line);
                        question = new Question(fileName, questionNumber);
                        String questionString = patternParser.stripQuestionNumber(line);

                        if (!StringUtils.isEmpty(questionString) || !StringUtils.isEmpty(question.getDescription())) {
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
                        } else if (patternParser.isCategory(line)) {
                            question.setCategory(line.replaceFirst("Category:", ""));
                            hasDescriptionStarted = false;
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
        if (question != null && questionValidator.isComplete(question)) {
            questions.add(question);
        } else {
            System.err.println("Last Question Not Added....");
        }
        validateQuestions(questions, questionBank.getTotalQuestions());
        return questions;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
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