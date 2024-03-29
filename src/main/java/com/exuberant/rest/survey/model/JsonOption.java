package com.exuberant.rest.survey.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonOption {

    private String tag;
    private String description;
    private String image;
    private boolean correct;

    @Override
    public String toString() {
        return description;
    }
}
