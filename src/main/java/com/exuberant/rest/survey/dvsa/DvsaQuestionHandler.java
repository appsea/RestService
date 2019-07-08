package com.exuberant.rest.survey.dvsa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DvsaQuestionHandler {

    public static final String RAWINPUT_DVSA_DVSATOPICS_JSON = "rawinput/dvsa/ukmotorcycletopics.json";
    public static final String QUESTION_FILE = "rawinput/dvsa/ukmotorcycle.json";
    public static final String OUTPUT_FOLDER = "C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources";
    public static final String OUT_FILE_NAME = "dvsa_motor_renamed.txt";
    public static final String IMAGES_OUT_FOLDER = "C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources\\images\\motorcycle";
    private static int fileNameCount = 2000;

    public static void main(String[] args) {
        /*String renamed = copyAndRenameFile("ab2003.jpg");
        System.err.println("Renamed to " + renamed);*/
        ObjectMapper objectMapper  = new ObjectMapper();
        ClassPathResource questionsFile = new ClassPathResource(QUESTION_FILE);
        ClassPathResource topicsFile = new ClassPathResource(RAWINPUT_DVSA_DVSATOPICS_JSON);
        try {
            DvsaQuestions questions = objectMapper.readValue(questionsFile.getFile(), DvsaQuestions.class);
            System.err.println("Questions: " + questions.getSize());
            DvsaTopics topics = objectMapper.readValue(topicsFile.getFile(), DvsaTopics.class);
            System.err.println("Topics: " + topics.getSize());
            writeQuestionsWithCategory(questions, topics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeQuestionsWithCategory(DvsaQuestions questions, DvsaTopics topics) throws IOException {
        Path path = Paths.get(OUTPUT_FOLDER, OUT_FILE_NAME);
        List<String> lines = new ArrayList<>();
        for (DvsaQuestion question : questions.getQuestions()) {
            lines.add("Question:");
            lines.add(question.getText());
            if(question.getPicture()!=null){
                String renamedFileName = copyAndRenameFile(question.getPicture());
                System.err.println("Renamed " + question.getPicture() + " to " + renamedFileName);
                question.setPicture(renamedFileName);
                lines.add("image: " + question.getPicture());
            }
            lines.add("");
            List<String> tags = Arrays.asList("A. ", "B. ", "C. ", "D. ");
            List<DvsaOption> options = question.getAnswers();
            Collections.shuffle(options);
            int count = 0;
            String answer = "";
            for (DvsaOption option : options) {
                String tag = tags.get(count++);
                lines.add(tag + option.getText());
                if(option.getPicture()!=null){
                    String renamedFileName = copyAndRenameFile(option.getPicture());
                    System.err.println("Renamed " + option.getPicture() + " to " + renamedFileName);
                    option.setPicture(renamedFileName);
                    lines.add("image: " + option.getPicture());
                }
                if(option.getIsCorrect()){
                    answer = tag.replaceAll(". ", "");
                }
            }
            lines.add("");
            lines.add("Answer: " + answer);
            lines.add("Description: " + question.getHint());
            lines.add("Category: " + topics.getTopics().stream().filter(topic -> topic.getId() == question.getTopicId()).findFirst().get().getName());
            lines.add("");
        }
        Files.write(path, lines);
    }

    private static String copyAndRenameFile(String picture) {
        String extension = ".jpg";
        String actualName = picture.replaceAll(".gif", extension);
        String root = IMAGES_OUT_FOLDER;
        Path source = Paths.get(root, actualName);
        if(!source.toFile().exists()){
            extension = ".png";
            actualName = picture.replaceAll(".gif", extension);
            source = Paths.get(root, actualName);
        }
        String newFileName = "mttk" + fileNameCount + extension;
        fileNameCount = fileNameCount + 1;
        Path destination = Paths.get(root, File.separator, "renamed", newFileName);
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldnt copy " + picture);
        }
        return destination.getFileName().toString();
    }
}
