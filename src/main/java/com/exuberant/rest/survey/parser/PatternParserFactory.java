package com.exuberant.rest.survey.parser;

import java.util.HashMap;
import java.util.Map;

public class PatternParserFactory {

    private static Map<String, PatternParser> patternParsersForFile = new HashMap<>();

    static {
        patternParsersForFile.put("Q1-A00-211qa204-20170130-17520396.txt", getFirstPatternParser());
        patternParsersForFile.put("Q2-1A00-201-Q&A-CertMagic-20170130-175206262.txt", getSecondPatternParser());
        patternParsersForFile.put("Q3-A00-201-Fx-20170130-175207599.txt", getThirdPatternParser());
        patternParsersForFile.put("Q4-A00-211-20170130-1752144.txt", getThirdPatternParser());
        patternParsersForFile.put("Q5-A00-211-Q&A-Demo-CertMagic-20170130-17521941.txt", getThirdPatternParser());
        patternParsersForFile.put("Q6-A00-211qa70-20170130-175156234.txt", getThirdPatternParser());
        patternParsersForFile.put("Base SAS Question.txt", getMainPatternParser());
        patternParsersForFile.put("Advance-sas-questions.txt", getMainPatternParser());
    }

    public static PatternParser getPatternParser(String fileName) {
        return patternParsersForFile.get(fileName);
    }

    public static PatternParser getFirstPatternParser() {
        String newQuestionRegex = "^QUESTION NO:.*";
        String optionRegex = "^[A-D][.]\\s.*";
        String answerRegex = "^Answer. .*";
        return new DynamicPatternParser(newQuestionRegex, optionRegex, answerRegex);
    }

    private static PatternParser getSecondPatternParser() {
        String newQuestionRegex = "^[0-9]+[.]\\s.*";
        //language=RegExp
        String extractQuestionRegex = "^[0-9]+.";
        String optionRegex = "^[A-D].\\s.*";
        String answerRegex = "^Answer. .*";
        return new DynamicPatternParser(newQuestionRegex, extractQuestionRegex, optionRegex, answerRegex);
    }

    private static PatternParser getThirdPatternParser() {
        String newQuestionRegex = "^Question:\\s\\d+";
        String optionRegex = "^[A-D][.]\\s.*";
        String answerRegex = "^Answer. .*";
        return new DynamicPatternParser(newQuestionRegex, optionRegex, answerRegex);
    }

    public static PatternParser getMainPatternParser() {
        String newQuestionRegex = "^Question:.*";
        String optionRegex = "^[A-D][.]\\s.*";
        String answerRegex = "^Answer:.*";
        String descriptionRegex = "^Description:.*";
        //return new DynamicPatternParser(newQuestionRegex, newQuestionRegex, optionRegex, answerRegex, IGNORE_REGEX, descriptionRegex);
        return DynamicPatternParser.getParserWithDescription(newQuestionRegex, optionRegex, answerRegex, descriptionRegex);
    }

    public static void addPatternParser(String file, PatternParser patternParser){
        patternParsersForFile.put(file, patternParser);
    }
}
