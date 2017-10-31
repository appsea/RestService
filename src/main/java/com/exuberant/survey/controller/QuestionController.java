package com.exuberant.survey.controller;

import com.exuberant.survey.model.Question;
import com.exuberant.survey.service.PaperSetter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/sas")
public class QuestionController {

    public static final Log log = LogFactory.getLog(QuestionController.class);

    @Autowired
    private PaperSetter paperSetter;

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping("/questions")
    public Collection<Question> questions() throws Exception {
        return paperSetter.buildQuestionPaper();
    }

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping("/nextquestion")
    public Question nextQuestion() throws Exception {
        log.info("Next Question...");
        return paperSetter.pollNextQuestion();
    }


}