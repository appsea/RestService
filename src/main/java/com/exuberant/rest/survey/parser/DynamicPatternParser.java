package com.exuberant.rest.survey.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public class DynamicPatternParser implements PatternParser {

    public static final String reg = "Question: [0-9]+";
    private Pattern ignorePattern;
    private static  String IGNORE_REGEX = "^\\s*Exam Name:.*|^\\s*Exam Type:.*|^\\s*Exam Code:.*|^\\s*Page\\s[0-9]+ of []0-9]+|[0-9]|[1-9][0-9]+|A00-211|A00-201|Question: [0-9]+|QUESTION NO: [0-9]+|^.*\\b(http://www.certmagic.com)\\b.*$";
    private Pattern newQuestionPattern;
    private Pattern extractQuestionPattern;
    private Pattern optionPattern;
    private Pattern answerPattern;
    private Pattern descriptionPattern;
    private Pattern categoryPattern;

    public DynamicPatternParser(String newQuestionRegex, String optionRegex, String answerRegex) {
        this(newQuestionRegex, newQuestionRegex, optionRegex, answerRegex, IGNORE_REGEX, null, null);
    }

    public DynamicPatternParser(String newQuestionRegex, String extractQuestionRegex, String optionRegex, String answerRegex) {
        this(newQuestionRegex, extractQuestionRegex, optionRegex, answerRegex, IGNORE_REGEX, null, null);
    }

    public DynamicPatternParser(String newQuestionRegex, String extractQuestionRegex, String optionRegex, String answerRegex, String ignoreRegex, String descriptionRegex, String categoryPatternRegex) {
        newQuestionPattern = Pattern.compile(newQuestionRegex);
        extractQuestionPattern = Pattern.compile(extractQuestionRegex);
        optionPattern = Pattern.compile(optionRegex);
        answerPattern = Pattern.compile(answerRegex);
        ignorePattern = Pattern.compile(ignoreRegex);
        if (null != descriptionRegex) {
            descriptionPattern = Pattern.compile(descriptionRegex);
        }
        if (null != categoryPatternRegex) {
            categoryPattern = Pattern.compile(categoryPatternRegex);
        }
    }

    public static DynamicPatternParser getParserWithDescription(String newQuestionRegex, String optionRegex, String answerRegex, String descriptionRegex) {
        DynamicPatternParser dynamicPatternParser = new DynamicPatternParser(newQuestionRegex, optionRegex, answerRegex);
        dynamicPatternParser.setDescriptionPattern(descriptionRegex);
        return dynamicPatternParser;
    }

    public static DynamicPatternParser getParserWithCategory(String newQuestionRegex, String optionRegex, String answerRegex, String descriptionRegex, String categoryRegex) {
        DynamicPatternParser dynamicPatternParser = new DynamicPatternParser(newQuestionRegex, optionRegex, answerRegex);
        dynamicPatternParser.setDescriptionPattern(descriptionRegex);
        dynamicPatternParser.setCategoryPattern(categoryRegex);
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
    public boolean isCategory(String line) {
        return categoryPattern != null && categoryPattern.matcher(line).matches();
    }

    @Override
    public boolean ignoreLine(String line) {
        return line == null || ignorePattern.matcher(line).matches();
    }

    public void setDescriptionPattern(String descriptionPattern) {
        this.descriptionPattern = Pattern.compile(descriptionPattern);
    }

    public void setCategoryPattern(String categoryPattern) {
        this.categoryPattern = Pattern.compile(categoryPattern);
    }

    public void setIgnorePattern(String ignoreRegex) {
        ignorePattern = Pattern.compile(ignoreRegex);
    }
}