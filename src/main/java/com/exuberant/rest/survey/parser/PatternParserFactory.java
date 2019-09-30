package com.exuberant.rest.survey.parser;

import java.util.HashMap;
import java.util.Map;

import static com.exuberant.rest.util.Constants.*;

public class PatternParserFactory {

    private static Map<String, PatternParser> patternParsersForFile = new HashMap<>();

    static {
        /*patternParsersForFile.put("Q1-A00-211qa204-20170130-17520396.txt", getFirstPatternParser());
        patternParsersForFile.put("Q2-1A00-201-Q&A-CertMagic-20170130-175206262.txt", getSecondPatternParser());
        patternParsersForFile.put("Q3-A00-201-Fx-20170130-175207599.txt", getThirdPatternParser());
        patternParsersForFile.put("Q4-A00-211-20170130-1752144.txt", getThirdPatternParser());
        patternParsersForFile.put("Q5-A00-211-Q&A-Demo-CertMagic-20170130-17521941.txt", getThirdPatternParser());
        patternParsersForFile.put("Q6-A00-211qa70-20170130-175156234.txt", getThirdPatternParser());*/
        patternParsersForFile.put(COMP_TIA_A_FILE_NAME, getCompTIAParser());
        patternParsersForFile.put(CATEGORIES_BASE_SAS_FILE_NAME, getCategoryParser());
        patternParsersForFile.put(DVSA_FILE_NAME, getCategoryParser());
        patternParsersForFile.put(DVSA_MOTOR_FILE_NAME, getCategoryParser());
        patternParsersForFile.put(DVSA_LGV_FILE_NAME, getCategoryParser());
        patternParsersForFile.put(BASE_SAS_QUESTION_FILE_NAME, getMainPatternParser());
        patternParsersForFile.put(ADVANCE_SAS_QUESTIONS_FILE_NAME, getMainPatternParser());
        patternParsersForFile.put(ISTQB, getCategoryParser());
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
        return DynamicPatternParser.getParserWithDescription(newQuestionRegex, optionRegex, answerRegex, descriptionRegex);
    }

    public static PatternParser getCategoryParser() {
        String newQuestionRegex = "^Question:.*";
        String optionRegex = "^[A-D][.]\\s.*";
        String answerRegex = "^Answer:.*";
        String descriptionRegex = "^Description:.*";
        String categoryRegex = "^Category:.*";
        return DynamicPatternParser.getParserWithCategory(newQuestionRegex, optionRegex, answerRegex, descriptionRegex, categoryRegex);
    }

    public static PatternParser getCompTIAParser() {
        String newQuestionRegex = "^QUESTION NO:.*";
        String optionRegex = "^[A-G][.]\\s.*";
        String answerRegex = "^Answer. .*";
        String descriptionRegex = "^Description:.*";
        DynamicPatternParser dynamicPatternParser = DynamicPatternParser.getParserWithDescription(newQuestionRegex, optionRegex, answerRegex, descriptionRegex);
        dynamicPatternParser.setIgnorePattern("^Topic\\s[0-9].*$|^\\s*Exam Name:.*|^\\s*Exam Type:.*|^\\s*Exam Code:.*|^\\s*Page\\s[0-9]+ of []0-9]+|[0-9]|[1-9][0-9]+|A00-211|A00-201|Question: [0-9]+|QUESTION NO: [0-9]+|^.*\\b(http://www.certmagic.com)\\b.*$");
        return dynamicPatternParser;
    }

    public static void addPatternParser(String file, PatternParser patternParser) {
        patternParsersForFile.put(file, patternParser);
    }
}
