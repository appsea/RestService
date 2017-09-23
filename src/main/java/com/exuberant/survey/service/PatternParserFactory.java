package com.exuberant.survey.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public class PatternParserFactory {

    private static Map<String, PatternParser> patternParsersForFile = new HashMap<>();

    static {
        patternParsersForFile.put("A00-211qa204-20170130-17520396.txt", getFirstPatternParser());
        patternParsersForFile.put("1A00-201-Q&A-CertMagic-20170130-175206262.txt", getSecondPatternParser());
        patternParsersForFile.put("A00-201-Fx-20170130-175207599.txt", getThirdPatternParser());
    }

    public static PatternParser getPatternParser(String fileName){
        return patternParsersForFile.get(fileName);
    }

    private static PatternParser getFirstPatternParser(){
        String newQuestionRegex = "^QUESTION NO:.*";
        String optionRegex = "^[A-D][.]\\s.*";
        String answerRegex = "^Answer. .*";
        return new CommonPatternParser(newQuestionRegex, optionRegex, answerRegex);
    }

    private static PatternParser getSecondPatternParser() {
        String newQuestionRegex = "^[0-9]+[.]\\s.*";
        String extractQuestionRegex = "^[0-9]+";
        String optionRegex = "^[A-D].\\s.*";
        String answerRegex = "^Answer. .*";
        return new CommonPatternParser(newQuestionRegex, extractQuestionRegex, optionRegex, answerRegex);
    }

    private static PatternParser getThirdPatternParser(){
        String newQuestionRegex = "^Question:\\s\\d+";
        String optionRegex = "^[A-D][.]\\s.*";
        //language=RegExp
        String answerRegex = "^Answer. .*";
        return new CommonPatternParser(newQuestionRegex, optionRegex, answerRegex);
    }
}
