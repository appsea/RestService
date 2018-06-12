package com.exuberant.rest.survey.parser;

import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.Question;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QuestionParserTest {

    private QuestionParser questionParser = new QuestionParser();

    @Before
    public void setup(){

    }

    @Test
    public void test() throws Exception {
        String fileName = "first.txt";
        QuestionBank bank = new QuestionBank(fileName, 1, 13);
        PatternParserFactory.addPatternParser(fileName, PatternParserFactory.getMainPatternParser());
        List<Question> questions = questionParser.parse(bank);
        System.err.println(questions.size());
    }

}