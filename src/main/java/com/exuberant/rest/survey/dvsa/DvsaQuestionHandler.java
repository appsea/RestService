package com.exuberant.rest.survey.dvsa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class DvsaQuestionHandler {

    /*public static final String RAWINPUT_DVSA_DVSATOPICS_JSON = "rawinput/dvsa/uklgvtopics.json";
    public static final String QUESTION_FILE = "rawinput/dvsa/uklgv.json";
    public static final String OUTPUT_FOLDER = "C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources\\input";
    public static final String OUT_FILE_NAME = "dvsa_lgv.txt";
    public static final String IMAGES_SOURCE_FOLDER = "C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources\\images\\lgv";
    public static final String IMAGES_OUT_FOLDER = "C:\\Data\\Rakesh\\Workspace\\Projects\\Nativescript\\DvsaLgvTheory\\app\\images";
    public static final String IMAGE_PREFIX = "lttk";*/
    public static final String RAWINPUT_DVSA_DVSATOPICS_JSON = "rawinput/dvsa/ukmotorcycletopics.json";
    public static final String QUESTION_FILE = "rawinput/dvsa/ukmotorcycle.json";
    public static final String OUTPUT_FOLDER = "C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources\\input";
    public static final String OUT_FILE_NAME = "dvsa_motor.txt";
    public static final String IMAGES_SOURCE_FOLDER = "C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources\\images\\motorcycle";
    public static final String IMAGES_OUT_FOLDER = "C:\\Data\\Rakesh\\Workspace\\Projects\\Nativescript\\DvsaMotorcycle\\app\\images";
    public static final String IMAGE_PREFIX = "mttk";

    private static int fileNameCount = 2000;
    private static Map<String, String> renamedMap = new HashMap<>();

    public static void main(String[] args) {
        ObjectMapper objectMapper  = new ObjectMapper();
        ClassPathResource questionsFile = new ClassPathResource(QUESTION_FILE);
        ClassPathResource topicsFile = new ClassPathResource(RAWINPUT_DVSA_DVSATOPICS_JSON);
        try {

            deleteFiles();
            DvsaQuestions questions = objectMapper.readValue(questionsFile.getFile(), DvsaQuestions.class);
            System.err.println("Questions: " + questions.getSize());
            DvsaTopics topics = objectMapper.readValue(topicsFile.getFile(), DvsaTopics.class);
            System.err.println("Topics: " + topics.getSize());
            writeQuestionsWithCategory(questions, topics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteFiles() {
        File imageFolder = new File(IMAGES_OUT_FOLDER);
        for (File s : imageFolder.listFiles()) {
            if(s.getName().startsWith(IMAGE_PREFIX)){
                s.delete();
            }
        }
    }

    private static void writeQuestionsWithCategory(DvsaQuestions questions, DvsaTopics topics) throws IOException {
        Path path = Paths.get(OUTPUT_FOLDER, OUT_FILE_NAME);
        List<String> lines = new ArrayList<>();
        for (DvsaQuestion question : questions.getQuestions()) {
            lines.add("Question:");
            lines.add(question.getText());
            String picture = question.getPicture();
            if(picture !=null){
                String renamedPicture = copyIfRequired(picture);
                question.setPicture(renamedPicture);
                lines.add("image: " + renamedPicture);
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
                    String renamedFileName = copyIfRequired(option.getPicture());
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
        System.err.println("Created file:" + path.toString());
        Files.write(path, lines);
    }

    private static String copyIfRequired(String picture) {
        String renamedFileName;
        if(!renamedMap.containsKey(picture.toUpperCase())){
            renamedFileName = copyAndRenameFile(picture);
            renamedMap.put(picture.toUpperCase(), renamedFileName);
            System.err.println("Renamed " + picture + " to " + renamedFileName);
        } else {
            renamedFileName = renamedMap.get(picture.toUpperCase());
            System.err.println("Reusing " + picture + " with " + renamedFileName);
        }
        return renamedFileName;
    }

    private static String copyAndRenameFile(String picture) {
        String extension = ".jpg";
        String actualName = picture.replaceAll(".gif", extension);
        String root = IMAGES_SOURCE_FOLDER;
        Path source = Paths.get(root, actualName);
        if(!source.toFile().exists()){
            extension = ".png";
            actualName = picture.replaceAll(".gif", extension);
            source = Paths.get(root, actualName);
        }
        String newFileName = IMAGE_PREFIX + fileNameCount + extension;
        fileNameCount = fileNameCount + 1;
        Path destination = Paths.get(IMAGES_OUT_FOLDER, File.separator, newFileName);
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldnt copy " + picture);
        }
        return destination.getFileName().toString();
    }
}
