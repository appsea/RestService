package com.exuberant.rest.survey.parser;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public interface PatternParser {

    boolean isAnswer(String line);

    boolean isOption(String line);

    boolean isNewQuestion(String line);

    String extractQuestionNumber(String line);

    String stripQuestionNumber(String line);

    boolean ignoreLine(String line);

    boolean isDescription(String line);
}
