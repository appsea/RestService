package com.exuberant.rest.survey.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by rakesh on 22-Sep-2017.
 */
@Data
public class JsonQuestions {

    public static final Log log = LogFactory.getLog(JsonQuestions.class);

    private List<JsonQuestion> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(toQuestion(question));
    }

    private JsonQuestion toQuestion(Question question) {
        JsonQuestion jsonQuestion = new JsonQuestion();
        jsonQuestion.setNumber(question.getNumber());
        jsonQuestion.setDescription(question.getDescription());
        jsonQuestion.setExplanation(question.getExplanation());
        for (Option option : question.getOptions().getOptions()) {
            JsonOption jsonOption = new JsonOption();
            jsonOption.setCorrect(option.isCorrect());
            jsonOption.setTag(option.getTag());
            jsonOption.setDescription(option.getDescription());
            jsonQuestion.addOption(jsonOption);
        }
        return jsonQuestion;
    }
}
