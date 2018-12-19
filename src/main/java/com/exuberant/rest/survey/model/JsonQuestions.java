package com.exuberant.rest.survey.model;

import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.util.MultiValueMap;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private boolean ads;
    @JsonIgnore
    private MultiValueMap<String, Integer> multiValueMap;

    public JsonQuestions(List<Question> allQuestions, int questionVersion, int playStoreVersion, boolean ads, boolean enablePrashna) {
        this.questionVersion = questionVersion;
        this.playStoreVersion = playStoreVersion;
        this.ads = ads;
        this.enablePrashna = enablePrashna;
        allQuestions.forEach(q -> this.questions.add(toQuestion(q)));
        this.categorise();
    }

    public JsonQuestions(List<Question> allQuestions, QuestionBank questionBank) {
        this.questionVersion = questionBank.getQuestionVersion();
        this.playStoreVersion = questionBank.getPlayStoreVersion();
        this.ads = questionBank.isShowAd();
        this.enablePrashna = questionBank.isEnablePrashna();
        allQuestions.subList(0, questionBank.getPremiumSize()).forEach(q -> this.questions.add(toQuestion(q)));
        allQuestions.subList(questionBank.getPremiumSize(), allQuestions.size()).forEach(q -> this.premium.add(toQuestion(q)));
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
        List<Category> categories = new ArrayList<>();
        for (Map.Entry<String, Set<Integer>> stringSetEntry : multiValueMap.entries()) {
            categories.add(new Category(stringSetEntry.getKey(), stringSetEntry.getValue()));
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
    }
}
