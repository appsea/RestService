package com.exuberant.rest.survey.controller;

import com.exuberant.rest.survey.service.FirebaseService;
import com.exuberant.rest.survey.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8808")
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/app")
public class ApplicationController {

    public static final Log log = LogFactory.getLog(ApplicationController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private FirebaseService firebaseService;

    @RequestMapping("/url")
    public String questions(@RequestParam String app) throws Exception {
        return this.firebaseService.getUrl(app);
    }

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping(path = "/version", consumes = MediaType.APPLICATION_ATOM_XML_VALUE)
    public String version() {
        return this.firebaseService.version();
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