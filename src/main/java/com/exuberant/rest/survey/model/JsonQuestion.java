package com.exuberant.rest.survey.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JsonQuestion {

    private String number;
    private String description;
    private String explanation;
    private List<JsonOption> options = new ArrayList<>();

    public void addOption(JsonOption jsonOption) {
        options.add(jsonOption);
    }

    @Override
    public String toString() {
        return "number='" + number + '\'' +
                "\n description='" + description + '\'' +
                "\n options=" + options +
                '}';
    }
}
