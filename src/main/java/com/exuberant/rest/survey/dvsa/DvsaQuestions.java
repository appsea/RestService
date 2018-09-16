package com.exuberant.rest.survey.dvsa;

import lombok.Data;

import java.util.List;

@Data
public class DvsaQuestions {
    private List<DvsaQuestion> questions;

    public int getSize(){
        return questions.size();
    }
}
