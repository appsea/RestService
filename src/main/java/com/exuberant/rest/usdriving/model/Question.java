package com.exuberant.rest.usdriving.model;

import lombok.Data;

@Data
public class Question {
    int id;
    int topicId;
    String question;
    String imageName;
    String imageData;
    String a;
    String b;
    String c;
    String d;
    String correctAnswer;
    String explanation;
}
