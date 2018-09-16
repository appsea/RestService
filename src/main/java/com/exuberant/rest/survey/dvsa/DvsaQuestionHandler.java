package com.exuberant.rest.survey.dvsa;

import com.exuberant.rest.survey.model.Option;
import com.exuberant.rest.survey.model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DvsaQuestionHandler {

    public static void main(String[] args) {
        ObjectMapper objectMapper  = new ObjectMapper();
        ClassPathResource questionsFile = new ClassPathResource("rawinput/dvsa/dvsaquestions.json");
        ClassPathResource topicsFile = new ClassPathResource("rawinput/dvsa/dvsatopics.json");
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
        Path path = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources", "dvsa.txt");
        List<String> lines = new ArrayList<>();
        for (DvsaQuestion question : questions.getQuestions()) {
            lines.add("Question:");
            lines.add(question.getText());
            if(question.getPicture()!=null){
                lines.add("image: " + question.getPicture());
            }
            lines.add("");
            List<DvsaOption> options = question.getAnswers();
            List<String> tags = Arrays.asList("A. ", "B. ", "C. ", "D. ");
            int count = 0;
            String answer = "";
            for (DvsaOption option : options) {
                String tag = tags.get(count++);
                lines.add(tag + option.getText());
                if(option.getPicture()!=null){
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
}
