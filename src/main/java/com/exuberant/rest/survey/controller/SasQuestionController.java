package com.exuberant.rest.survey.controller;

import com.exuberant.rest.survey.model.JsonQuestion;
import com.exuberant.rest.survey.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8808")
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/sas")
public class SasQuestionController {

    public static final Log log = LogFactory.getLog(SasQuestionController.class);

    @Autowired
    private QuestionService questionService;

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping("/questions")
    public List<JsonQuestion> questions() throws Exception {
        return this.questionService.getQuestions();
    }

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping("/version")
    public int version() {
        return this.questionService.getVersion();
    }

    /*@CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping("/questionsOld")
    public Collection<Question> questionsOld() throws Exception {
        return paperSetter.buildQuestionPaper();
    }

    //@PreAuthorize("hasRole('USER')")
    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping(path = "/nextquestion", method = RequestMethod.GET)
    public Question nextQuestion() throws Exception {
        log.info("Next Question...");
        return paperSetter.pollNextQuestion();
    }*/
}