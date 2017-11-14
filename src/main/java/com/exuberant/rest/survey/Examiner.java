package com.exuberant.rest.survey;

import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.service.PaperSetter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by rakesh on 26-Sep-2017.
 */
public class Examiner {

    public static final Log log = LogFactory.getLog(Examiner.class);

    private final PaperSetter paperSetter;

    public Examiner(PaperSetter paperSetter) {
        this.paperSetter = paperSetter;
    }

    public void beginExam() throws Exception {
        List<Question> questionPaper = paperSetter.buildQuestionPaper();
        startExam(questionPaper);
        showResult(questionPaper);
    }

    private void startExam(List<Question> questionPaper) {
        int count = 1;
        Scanner scanner = new Scanner(System.in);
        for (Question question : questionPaper) {
            log.info("Question Number: " + count++ + " of " + questionPaper.size());
            log.info(question);
            String answer = scanner.nextLine();
            question.setSubmittedAnswer(answer);
        }
    }

    private void showResult(List<Question> questionPaper) {
        Set<Question> wrongAnsweredQuestions = new HashSet<>();
        Set<Question> correctAnsweredQuestions = new HashSet<>();
        for (Question question : questionPaper) {
            if (question.isAnsweredCdorrectly()) {
                correctAnsweredQuestions.add(question);
            } else {
                wrongAnsweredQuestions.add(question);
            }
        }
        for (Question wrongAnsweredQuestion : wrongAnsweredQuestions) {
            log.info("Question: " + wrongAnsweredQuestion);
            log.info("You Answered: " + wrongAnsweredQuestion.getSubmittedAnswer());
            log.info("Correct Answer: " + wrongAnsweredQuestion.getAnswer() + "\n");
        }
        log.info("Answered " + correctAnsweredQuestions.size() + " correctly!!!");
        log.info("Following questions were wrongly answered: Total " + wrongAnsweredQuestions.size());
    }
}
