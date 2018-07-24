package com.exuberant.rest.survey.controller;

import com.exuberant.rest.survey.model.JsonQuestion;
import com.exuberant.rest.survey.service.FirebaseService;
import com.exuberant.rest.survey.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8808")
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/sas")
public class SasQuestionController {

    public static final Log log = LogFactory.getLog(SasQuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private FirebaseService firebaseService;

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping("/questions")
    public List<JsonQuestion> questions() throws Exception {
        return this.questionService.getQuestions();
    }

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping("/version")
    public String version() {
        return this.firebaseService.version();
    }

    @RequestMapping(path = "/suggestion", method = RequestMethod.POST)
    public void suggest(@RequestBody JsonQuestion jsonQuestion) {
        this.firebaseService.suggest(jsonQuestion);
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