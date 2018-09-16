package com.exuberant.rest.survey.dvsa;

import lombok.Data;

import java.util.List;

@Data
public class DvsaQuestion {
    private String text;
    private List<DvsaOption> answers;
    private String hint;
    private String identifier;
    private int numberOfCorrectAnswers;
    private int topicId;
    private String picture;
}
