package com.exuberant.survey.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public class CommonPatternParser implements PatternParser {

    private final String newQuestionRegex;
    private final String extractQuestionRegex;
    private Pattern newQuestionPattern;
    private Pattern extractQuestionPattern;
    private Pattern optionPattern;
    private Pattern answerPattern;

    public CommonPatternParser(String newQuestionRegex, String optionRegex, String answerRegex) {
        this(newQuestionRegex, newQuestionRegex, optionRegex, answerRegex);
    }

    public CommonPatternParser(String newQuestionRegex, String extractQuestionRegex, String optionRegex, String answerRegex) {
        this.newQuestionRegex = newQuestionRegex;
        this.extractQuestionRegex = extractQuestionRegex;
        newQuestionPattern = Pattern.compile(newQuestionRegex);
        extractQuestionPattern = Pattern.compile(extractQuestionRegex);
        optionPattern = Pattern.compile(optionRegex);
        answerPattern = Pattern.compile(answerRegex);
    }

    public boolean isAnswer(String line) {
        return answerPattern.matcher(line).matches();
    }

    public boolean isOption(String line) {
        return optionPattern.matcher(line).matches();
    }

    public boolean isNewQuestion(String line) {
        return newQuestionPattern.matcher(line).matches();
    }

    @Override
    public String extractQuestionNumber(String line) {
        Matcher matcher = extractQuestionPattern.matcher(line);
        return matcher.find()?matcher.group():line;
    }

    @Override
    public String stripQuestionNumber(String line) {
        Matcher matcher = extractQuestionPattern.matcher(line);
        return matcher.find()?line.replaceAll(extractQuestionRegex+"."+"\\s", ""):line;
    }
}