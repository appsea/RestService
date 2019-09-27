package com.exuberant.rest.survey.local;

import com.exuberant.rest.survey.LocalResourceLoader;
import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.model.*;
import com.exuberant.rest.survey.parser.GenericQuestionParser;
import com.exuberant.rest.survey.parser.QuestionParser;
import com.exuberant.rest.survey.parser.validator.GeneralQuestionValidator;
import com.exuberant.rest.survey.parser.validator.QuestionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.exuberant.rest.util.Constants.*;

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
        questionParsersForFile.put(COMP_TIA_A_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(CATEGORIES_BASE_SAS_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(BASE_SAS_QUESTION_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(ADVANCE_SAS_QUESTIONS_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(DVSA_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(DVSA_MOTOR_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(DVSA_LGV_FILE_NAME, genericQuestionParser);
        questionParsersForFile.put(ISTQB, genericQuestionParser);

        finisher.put("CompTIA A+.txt", new CompTiaAPlusFinisher());
    }

    public void generateQuestions(QuestionBank questionBank) throws Exception {
        QuestionParser parser = questionParsersForFile.get(questionBank.getInputFile());
        List<Question> questions = parser.parse(questionBank);
        if (finisher.containsKey(questionBank.getInputFile())) {
            Finisher fileFinisher = finisher.get(questionBank.getInputFile());
            questions = fileFinisher.finish(questions);
        }
        /*if (questionBank.getInputFile().toLowerCase().contains("comp")) {
            validateUrls(questions);
        }*/

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
        validateImages(jsonQuestions, questionBank);
    }

    private void validateUrls(List<Question> questions) {
        for (Question question : questions) {
            List<String> urls = extractUrls(question.getExplanation());
            for (String url : urls) {
                if(validateHttpUrl(url)){
                    System.err.println("URL is valid " + url);
                } else {
                    System.err.println("URL is invalid: " + url);
                }
            }
        }
    }

    public boolean validateHttpUrl(String string) {
        boolean valid = false;
        if (!string.toLowerCase().startsWith("http")) {
            string = "http://" + string;
        }
        URL u;
        try {
            u = new URL(string);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");  //OR  huc.setRequestMethod ("HEAD");
            huc.connect();
            int code = huc.getResponseCode();
            System.out.println(code);
            if (code == 200) {
                valid = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valid;

    }

    public boolean isValidUrl(String string) {
        boolean isValid = false;
        try {
            URL url = new URL(string);
            URLConnection conn = url.openConnection();
            conn.connect();
            isValid = true;
        } catch (MalformedURLException e) {
            // the URL is not in a valid form
        } catch (IOException e) {
            // the connection couldn't be established
        }
        return isValid;
    }

    public static List<String> extractUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "\\(?\\b(http://|www[.]|https://)[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            String urlString = urlMatcher.group();
            if (urlString.startsWith("(") && urlString.endsWith(")")) {
                urlString = urlString.substring(1, urlString.length() - 1);
            }
            containedUrls.add(urlString);
        }

        return containedUrls;
    }

    private void validateImages(JsonQuestions jsonQuestions, QuestionBank questionBank) {
        String folder = questionBank.getImageLocation();
        if (folder != null) {
            for (JsonQuestion jsonQuestion : jsonQuestions.getAllQuestions()) {
                String image = jsonQuestion.getPrashna().getImage();
                if (!StringUtils.isEmpty(image)) {
                    File file = new File(folder + File.separator + image);
                    if (!file.exists()) {
                        System.err.println("Missing image " + file.getPath());
                    }
                }
                for (JsonOption jsonOption : jsonQuestion.getOptions()) {
                    image = jsonOption.getImage();
                    if (!StringUtils.isEmpty(image)) {
                        File file = new File(folder + File.separator + image);
                        if (!file.exists()) {
                            System.err.println("Missing image " + file.getPath());
                        }
                    }
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
            if (!valid) {
                path = "C:\\Data\\Rakesh\\Workspace\\Projects\\Nativescript\\DvsaMotorcycle\\app\\images" + File.separator + image;
                file = new File(path);
                valid = file.exists();
            }
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
