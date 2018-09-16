package com.exuberant.rest.survey.dvsa;

import lombok.Data;

import java.util.List;

@Data
public class DvsaTopics {
    private List<Topic> topics;

    public int getSize(){
        return topics.size();
    }
}
