package com.exuberant.rest.survey.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public class DynamicPatternParser implements PatternParser {

    public static final String IGNORE_REGEX = "^\\s*Exam Name:.*|^\\s*Exam Type:.*|^\\s*Exam Code:.*|^\\s*Page\\s[0-9]+ of []0-9]+|[0-9]|[1-9][0-9]+|A00-211|A00-201|Question: [0-9]+|QUESTION NO: [0-9]+|^.*\\b(http://www.certmagic.com)\\b.*$";
    public static final String reg = "Question: [0-9]+";
    private final String extractQuestionRegex;
    private final Pattern ignorePattern;
    private Pattern newQuestionPattern;
    private Pattern extractQuestionPattern;
    private Pattern optionPattern;
    private Pattern answerPattern;
    private Pattern descriptionPattern;

    public DynamicPatternParser(String newQuestionRegex, String optionRegex, String answerRegex) {
        this(newQuestionRegex, newQuestionRegex, optionRegex, answerRegex, IGNORE_REGEX, null);
    }

    public DynamicPatternParser(String newQuestionRegex, String extractQuestionRegex, String optionRegex, String answerRegex) {
        this(newQuestionRegex, extractQuestionRegex, optionRegex, answerRegex, IGNORE_REGEX, null);
    }

    public DynamicPatternParser(String newQuestionRegex, String extractQuestionRegex, String optionRegex, String answerRegex, String ignoreRegex, String descriptionRegex) {
        newQuestionPattern = Pattern.compile(newQuestionRegex);
        this.extractQuestionRegex = extractQuestionRegex;
        extractQuestionPattern = Pattern.compile(extractQuestionRegex);
        optionPattern = Pattern.compile(optionRegex);
        answerPattern = Pattern.compile(answerRegex);
        ignorePattern = Pattern.compile(ignoreRegex);
        if (null != descriptionRegex) {
            descriptionPattern = Pattern.compile(descriptionRegex);
        }
    }

    public static DynamicPatternParser getParserWithDescription(String newQuestionRegex, String optionRegex, String answerRegex, String descriptionRegex){
        DynamicPatternParser dynamicPatternParser = new DynamicPatternParser(newQuestionRegex, optionRegex, answerRegex);
        dynamicPatternParser.setDescriptionPattern(descriptionRegex);
        return dynamicPatternParser;
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
        return matcher.find() ? matcher.group() : line;
    }

    @Override
    public String stripQuestionNumber(String line) {
        Matcher matcher = extractQuestionPattern.matcher(line);
        return matcher.replaceAll("");
    }

    @Override
    public boolean isDescription(String line) {
        return descriptionPattern != null && descriptionPattern.matcher(line).matches();
    }

    @Override
    public boolean ignoreLine(String line) {
        return line == null || ignorePattern.matcher(line).matches();
    }

    public void setDescriptionPattern(String descriptionPattern) {
        this.descriptionPattern = Pattern.compile(descriptionPattern);
    }
}