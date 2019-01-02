package com.exuberant.rest.survey.parser;

import com.exuberant.rest.survey.LocalResourceLoader;
import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.parser.validator.GeneralQuestionValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GenericQuestionParserTest {

    private GenericQuestionParser genericQuestionParser = new GenericQuestionParser(new LocalResourceLoader(), new GeneralQuestionValidator());

    @Before
    public void setup() {

    }

    @Test
    public void test() throws Exception {
        String fileName = "first.txt";
        QuestionBank bank = new QuestionBank(fileName, 5, 10, 13, 10001, false, false);
        PatternParserFactory.addPatternParser(fileName, PatternParserFactory.getMainPatternParser());
        List<Question> questions = genericQuestionParser.parse(bank);
        System.err.println(questions.size());
    }

}