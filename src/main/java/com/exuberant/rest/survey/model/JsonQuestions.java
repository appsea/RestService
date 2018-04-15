package com.exuberant.rest.survey.model;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 22-Sep-2017.
 */
@Data
public class JsonQuestions {

    public static final Log log = LogFactory.getLog(JsonQuestions.class);

    private List<JsonQuestion> questions = new ArrayList<>();
    private int version = 5;

    public void addQuestion(Question question) {
        questions.add(toQuestion(question));
    }

    private JsonQuestion toQuestion(Question question) {
        JsonQuestion jsonQuestion = new JsonQuestion();
        jsonQuestion.setNumber(question.getNumber());
        jsonQuestion.setDescription(question.getDescription().replaceAll("\n$", ""));
        jsonQuestion.setExplanation(question.getExplanation().replaceAll("\n$", ""));
        for (Option option : question.getOptions().getOptions()) {
            JsonOption jsonOption = new JsonOption();
            jsonOption.setCorrect(option.isCorrect());
            jsonOption.setTag(option.getTag());
            jsonOption.setDescription(option.getDescription().replaceAll("\n$", ""));
            jsonQuestion.addOption(jsonOption);
        }
        return jsonQuestion;
    }
}
