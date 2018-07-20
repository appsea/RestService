package com.exuberant.rest.survey.local;

import com.exuberant.rest.survey.LocalResourceLoader;
import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.JsonQuestions;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.parser.GenericQuestionParser;
import com.exuberant.rest.survey.parser.QuestionParser;
import com.exuberant.rest.survey.parser.validator.GeneralQuestionValidator;
import com.exuberant.rest.survey.parser.validator.QuestionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.exuberant.rest.util.Constants.ADVANCE_SAS_QUESTIONS_FILE_NAME;
import static com.exuberant.rest.util.Constants.BASE_SAS_QUESTION_FILE_NAME;

public class JsonQuestionGenerator {

    private static Map<String, QuestionParser> questionParsersForFile = new HashMap<>();

    static {
        /*patternParsersForFile.put("Q1-A00-211qa204-20170130-17520396.txt", getFirstPatternParser());
        patternParsersForFile.put("Q2-1A00-201-Q&A-CertMagic-20170130-175206262.txt", getSecondPatternParser());
        patternParsersForFile.put("Q3-A00-201-Fx-20170130-175207599.txt", getThirdPatternParser());
        patternParsersForFile.put("Q4-A00-211-20170130-1752144.txt", getThirdPatternParser());
        patternParsersForFile.put("Q5-A00-211-Q&A-Demo-CertMagic-20170130-17521941.txt", getThirdPatternParser());
        patternParsersForFile.put("Q6-A00-211qa70-20170130-175156234.txt", getThirdPatternParser());*/
        LocalResourceLoader localResourceLoader = new LocalResourceLoader();
        QuestionValidator generalQuestionValidator = new GeneralQuestionValidator();
        GenericQuestionParser genericQuestionParser = new GenericQuestionParser(localResourceLoader, generalQuestionValidator);
        questionParsersForFile.put("Categories Base SAS.txt", genericQuestionParser);
        questionParsersForFile.put(BASE_SAS_QUESTION_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(ADVANCE_SAS_QUESTIONS_FILE_NAME, genericQuestionParser);
    }

    public void generateQuestions(QuestionBank questionBank) throws Exception {
        QuestionParser parser = questionParsersForFile.get(questionBank.getInputFile());
        List<Question> questions = parser.parse(questionBank);
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources", questionBank.getInputFile().replaceAll(".txt", ".json"));
        System.err.println("Created: " + path);
        JsonQuestions jsonQuestions = new JsonQuestions(questions, questionBank);
        //System.err.println("Without: " + jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).count());
        //jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).forEach(que -> System.err.println(que.getDescription()));
        //jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).forEach(System.out::println);
        Files.write(path, objectMapper.writeValueAsString(jsonQuestions).getBytes());
    }
}
