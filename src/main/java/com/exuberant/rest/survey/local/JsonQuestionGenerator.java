package com.exuberant.rest.survey.local;

import com.exuberant.rest.survey.LocalResourceLoader;
import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.JsonQuestions;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.parser.QuestionParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonQuestionGenerator {

    public void generateQuestions(QuestionBank questionBank) throws Exception {
        QuestionParser questionParser = new QuestionParser();
        questionParser.setResourceLoader(new LocalResourceLoader());
        List<Question> questions = questionParser.parse(questionBank);
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
