package com.exuberant.rest.survey.model;

import lombok.Data;

@Data
public class JsonOption {

    private String tag;
    private String description;
    private boolean correct;

    @Override
    public String toString() {
        return description;
    }
}
