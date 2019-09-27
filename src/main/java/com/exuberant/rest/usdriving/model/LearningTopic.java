package com.exuberant.rest.usdriving.model;

import lombok.Data;

@Data
public class LearningTopic {
    int id;
    String name;
    String description;
    int numberOfCard;
    String url;
}
