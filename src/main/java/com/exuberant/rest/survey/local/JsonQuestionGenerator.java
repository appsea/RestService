package com.exuberant.rest.survey.local;

import com.exuberant.rest.survey.LocalResourceLoader;
import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.*;
import com.exuberant.rest.survey.parser.GenericQuestionParser;
import com.exuberant.rest.survey.parser.QuestionParser;
import com.exuberant.rest.survey.parser.validator.GeneralQuestionValidator;
import com.exuberant.rest.survey.parser.validator.QuestionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.exuberant.rest.util.Constants.ADVANCE_SAS_QUESTIONS_FILE_NAME;
import static com.exuberant.rest.util.Constants.BASE_SAS_QUESTION_FILE_NAME;

public class JsonQuestionGenerator {

    private static Map<String, QuestionParser> questionParsersForFile = new HashMap<>();
    private static Map<String, Finisher> finisher = new HashMap<>();

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
        questionParsersForFile.put("CompTIA A+.txt", genericQuestionParser);
        questionParsersForFile.put("Categories Base SAS.txt", genericQuestionParser);
        questionParsersForFile.put(BASE_SAS_QUESTION_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(ADVANCE_SAS_QUESTIONS_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put("dvsa.txt", genericQuestionParser);

        finisher.put("CompTIA A+.txt", new CompTiaAPlusFinisher());
    }

    public void generateQuestions(QuestionBank questionBank) throws Exception {
        QuestionParser parser = questionParsersForFile.get(questionBank.getInputFile());
        List<Question> questions = parser.parse(questionBank);
        if(finisher.containsKey(questionBank.getInputFile())){
            Finisher fileFinisher = finisher.get(questionBank.getInputFile());
            questions = fileFinisher.finish(questions);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Path jsonPath = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources\\output", questionBank.getInputFile().replaceAll(".txt", ".json"));
        Path wordPath = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources\\output", questionBank.getInputFile().replaceAll(".txt", ".docx"));
        Path pdfPath = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources\\output", questionBank.getInputFile().replaceAll(".txt", ".pdf"));
        System.err.println("Created: " + jsonPath);
        JsonQuestions jsonQuestions = new JsonQuestions(questions, questionBank);
        //System.err.println("Without: " + jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).count());
        //jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).forEach(que -> System.err.println(que.getDescription()));
        //jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).forEach(System.out::println);
        new WordFileWriter().write(jsonQuestions, wordPath);
        Files.write(jsonPath, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonQuestions).getBytes());
        if (questionBank.getInputFile().contains("dvsa")){
            validateDvsaQuestions(jsonQuestions);
        }
    }

    private void validateDvsaQuestions(JsonQuestions jsonQuestions) {
        for (JsonQuestion jsonQuestion : jsonQuestions.getAllQuestions()) {
            String image = jsonQuestion.getPrashna().getImage();
            if(!isValidImage(image)){
                System.err.println("Missing image " + image);
            }
            for (JsonOption jsonOption : jsonQuestion.getOptions()) {
                image = jsonOption.getImage();
                if(!isValidImage(image)){
                    System.err.println("Missing image " + image);
                }
            }
        }
    }

    private boolean isValidImage(String image) {
        boolean valid = true;
        if (null != image) {
            String path = "C:\\Data\\Rakesh\\Workspace\\Projects\\Nativescript\\Dvsa\\app\\images" + File.separator + image;
            File file = new File(path);
            valid = file.exists();
        }
        return valid;
    }

    private void writeQuestionsWithRandomDescription(List<Question> questions) throws IOException {
        List<String> categories = Arrays.asList("First", "Second", "Third");
        Path path = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources", "categories.txt");
        List<String> lines = new ArrayList<>();
        for (Question question : questions) {
            lines.add("Question:");
            lines.add(question.getDescription());
            lines.add("");
            List<Option> options = question.getOptions().getOptions();
            for (Option option : options) {
                lines.add(option.getDescription());
            }
            lines.add("");
            lines.add("Answer: " + options.stream().filter(option -> option.isCorrect()).findFirst().get().getTag());
            lines.add("Description: " + question.getExplanation());
            lines.add("Category: " + categories.get(ThreadLocalRandom.current().nextInt(0, 3)));
            lines.add("");
        }
        Files.write(path, lines);
    }
}
