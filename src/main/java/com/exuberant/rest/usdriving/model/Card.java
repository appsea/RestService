package com.exuberant.rest.usdriving.model;

import lombok.Data;

@Data
public class Card {
    int id;
    int topicId;
    String term;
    String imageName;
    String imageData;
    String definition;
}
