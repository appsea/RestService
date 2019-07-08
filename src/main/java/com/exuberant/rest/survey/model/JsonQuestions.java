package com.exuberant.rest.survey.model;

import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.util.MultiValueMap;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by rakesh on 22-Sep-2017.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonQuestions {

    public static final Log log = LogFactory.getLog(JsonQuestions.class);
    public static final String IMAGE = "image:";
    @JsonIgnore
    private boolean enablePrashna;

    private List<JsonQuestion> questions = new ArrayList<>();
    private List<JsonQuestion> premium = new ArrayList<>();
    private int questionVersion;
    private int playStoreVersion;
    private int totalQuestions;
    private boolean ads;
    @JsonIgnore
    private MultiValueMap<String, Integer> multiValueMap;
    @JsonIgnore
    private Map<String, String> categoryIconMap = new HashMap<>();

    public JsonQuestions(List<Question> allQuestions, int questionVersion, int playStoreVersion, boolean ads, boolean enablePrashna) {
        this.questionVersion = questionVersion;
        this.playStoreVersion = playStoreVersion;
        this.ads = ads;
        this.enablePrashna = enablePrashna;
        this.initialiseMap();
        allQuestions.forEach(q -> this.questions.add(toQuestion(q)));
        this.categorise();
    }

    private void initialiseMap() {
        categoryIconMap.put("ALERTNESS", "0xf06e");
        categoryIconMap.put("ATTITUDE", "0xf118");
        categoryIconMap.put("SAFETY AND YOUR VEHICLE", "0xf3ed");
        categoryIconMap.put("SAFETY AND YOUR MOTORCYCLE", "0xf3ed");
        categoryIconMap.put("SAFETY MARGINS", "0xf560");
        categoryIconMap.put("HAZARD AWARENESS", "0xf071");
        categoryIconMap.put("VULNERABLE ROAD USERS", "0xf29d");
        categoryIconMap.put("OTHER TYPES OF VEHICLE", "0xf4df");
        categoryIconMap.put("VEHICLE HANDLING", "0xf1B9");
        categoryIconMap.put("MOTORCYCLE HANDLING", "0xf21c");
        categoryIconMap.put("MOTORWAY RULES", "0xf018");
        categoryIconMap.put("RULES OF THE ROAD", "0xf534");
        categoryIconMap.put("ROAD AND TRAFFIC SIGNS", "0xf637");
        categoryIconMap.put("DOCUMENTS", "0xf02d");
        categoryIconMap.put("INCIDENTS, ACCIDENTS AND EMERGENCIES", "0xf5e1");
        categoryIconMap.put("INCIDENTS ACCIDENTS AND EMERGENCIES", "0xf87b");
        categoryIconMap.put("VEHICLE LOADING", "0xf59d");
        categoryIconMap.put("MOTORCYCLE LOADING", "0xf59d");
    }

    public JsonQuestions(List<Question> allQuestions, QuestionBank questionBank) {
        this.questionVersion = questionBank.getQuestionVersion();
        this.playStoreVersion = questionBank.getPlayStoreVersion();
        this.ads = questionBank.isShowAd();
        this.enablePrashna = questionBank.isEnablePrashna();
        if(questionBank.enablePremium()){
            allQuestions.subList(0, questionBank.getFreeQuestions()).forEach(q -> this.questions.add(toQuestion(q)));
            allQuestions.subList(questionBank.getFreeQuestions(), allQuestions.size()).forEach(q -> this.premium.add(toQuestion(q)));
        }else{
            allQuestions.forEach(q -> this.questions.add(toQuestion(q)));
        }

        this.totalQuestions = allQuestions.size();
        this.categorise();
    }

    public void addQuestion(Question question) {
        questions.add(toQuestion(question));
    }

    private JsonQuestion toQuestion(Question question) {
        JsonQuestion jsonQuestion = new JsonQuestion();
        jsonQuestion.setNumber(Integer.parseInt(question.getNumber()));
        String descriptionWithImage = question.getDescription();
        if (enablePrashna) {
            Prashna prashna = toPrashna(descriptionWithImage);
            jsonQuestion.setPrashna(prashna);
        } else {
            jsonQuestion.setDescription(descriptionWithImage.replaceAll("\n$", ""));
        }
        jsonQuestion.setExplanation(question.getExplanation());
        jsonQuestion.setCategory(question.getCategory());
        for (Option option : question.getOptions().getOptions()) {
            JsonOption jsonOption = new JsonOption();
            jsonOption.setCorrect(option.isCorrect());
            jsonOption.setTag(option.getTag());
            String description = option.getDescription().replaceAll("\n$", "");
            jsonOption.setDescription(description);
            if (description.contains(IMAGE)) {
                int beginIndex = description.indexOf(IMAGE);
                jsonOption.setDescription(null);
                String image = description.substring(beginIndex + 7);
                jsonOption.setImage(image);
            }
            jsonQuestion.addOption(jsonOption);
        }
        return jsonQuestion;
    }


    public JsonQuestions() {
    }

    public static void main(String[] args) {
        JsonQuestions jsonQuestions = new JsonQuestions();
        String string = "What should you do as you approach this bridge?\nimage: cttk2000.jpg";
        jsonQuestions.toPrashna(string);
    }

    public Prashna toPrashna(String description) {
        Prashna prashna = new Prashna();
        if (description.contains(IMAGE)) {
            int beginIndex = description.indexOf(IMAGE);
            String text = description.substring(0, beginIndex).replaceAll("\n$", "");
            prashna.setText(text);
            String image = description.substring(beginIndex + 7);
            prashna.setImage(image);
        } else {
            prashna.setText(description.replaceAll("\n$", ""));
        }
        return prashna;
    }

    public List<Category> getCategories() {
        this.initialiseMap();
        List<Category> categories = new ArrayList<>();
        for (Map.Entry<String, Set<Integer>> stringSetEntry : multiValueMap.entries()) {
            categories.add(new Category(categoryIconMap.get(stringSetEntry.getKey().toUpperCase()), stringSetEntry.getKey(), stringSetEntry.getValue()));
        }
        return categories;
    }

    public void categorise() {
        multiValueMap = new MultiValueMap();
        for (JsonQuestion question : questions) {
            if (question.getCategory() != null) {
                multiValueMap.put(question.getCategory(), question.getNumber());
            }
        }
        for (JsonQuestion question : premium) {
            if (question.getCategory() != null) {
                multiValueMap.put(question.getCategory(), question.getNumber());
            }
        }
    }

    @JsonIgnore
    public List<JsonQuestion> getAllQuestions(){
        ArrayList<JsonQuestion> jsonQuestions = new ArrayList<>(questions);
        jsonQuestions.addAll(premium);
        return jsonQuestions;
    }
}
