package com.exuberant.rest.survey.model;

import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.util.MultiValueMap;
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
public class JsonQuestions {

    public static final Log log = LogFactory.getLog(JsonQuestions.class);

    private List<JsonQuestion> questions = new ArrayList<>();
    private int questionVersion;
    private int playStoreVersion;
    private boolean ads;
    private MultiValueMap<String, Integer> categories;

    public JsonQuestions(List<Question> allQuestions, int questionVersion, int playStoreVersion, boolean ads) {
        allQuestions.forEach(q -> this.questions.add(toQuestion(q)));
        this.categorise();
        this.questionVersion = questionVersion;
        this.playStoreVersion = playStoreVersion;
        this.ads = ads;
    }

    public JsonQuestions(List<Question> allQuestions, QuestionBank questionBank) {
        allQuestions.forEach(q -> this.questions.add(toQuestion(q)));
        this.categorise();
        this.questionVersion = questionBank.getQuestionVersion();
        this.playStoreVersion = questionBank.getPlayStoreVersion();
        this.ads = questionBank.isShowAd();
    }

    public void addQuestion(Question question) {
        questions.add(toQuestion(question));
    }

    private JsonQuestion toQuestion(Question question) {
        JsonQuestion jsonQuestion = new JsonQuestion();
        jsonQuestion.setNumber(question.getNumber());
        jsonQuestion.setDescription(question.getDescription());
        jsonQuestion.setExplanation(question.getExplanation());
        jsonQuestion.setCategory(question.getCategory());
        for (Option option : question.getOptions().getOptions()) {
            JsonOption jsonOption = new JsonOption();
            jsonOption.setCorrect(option.isCorrect());
            jsonOption.setTag(option.getTag());
            jsonOption.setDescription(option.getDescription().replaceAll("\n$", ""));
            jsonQuestion.addOption(jsonOption);
        }
        return jsonQuestion;
    }

    public Set<Map.Entry<String, Set<Integer>>> getCategories() {
        return categories.entries();
    }

    public void categorise(){
        categories = new MultiValueMap();
        for (JsonQuestion question : questions) {
            if(question.getCategory()!=null){
                categories.put(question.getCategory(), Integer.parseInt(question.getNumber()));
            }
        }
    }
}
