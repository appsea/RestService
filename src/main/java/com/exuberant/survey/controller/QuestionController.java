package com.exuberant.survey.controller;

import com.exuberant.survey.model.Question;
import com.exuberant.survey.service.PaperSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class QuestionController {

    @Autowired
    private PaperSetter paperSetter;

    @RequestMapping("/questions")
    public Collection<Question> questions() throws Exception {
        return paperSetter.buildQuestionPaper();
    }
}