package com.exuberant.survey;

import com.exuberant.survey.model.Question;
import com.exuberant.survey.service.PaperSetter;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by rakesh on 26-Sep-2017.
 */
public class Examiner {

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
            System.err.println("Question Number: " + count++ + " of " + questionPaper.size());
            System.err.println(question);
            String answer = scanner.nextLine();
            question.setSubmittedAnswer(answer);
        }
    }

    private void showResult(List<Question> questionPaper) {
        Set<Question> wrongAnsweredQuestions = new HashSet<>();
        Set<Question> correctAnsweredQuestions = new HashSet<>();
        for (Question question : questionPaper) {
            if (question.isAnsweredCorrectly()) {
                correctAnsweredQuestions.add(question);
            } else {
                wrongAnsweredQuestions.add(question);
            }
        }
        for (Question wrongAnsweredQuestion : wrongAnsweredQuestions) {
            System.err.println("Question: " + wrongAnsweredQuestion);
            System.err.println("You Answered: " + wrongAnsweredQuestion.getSubmittedAnswer());
            System.err.println("Correct Answer: " + wrongAnsweredQuestion.getAnswer() + "\n");
        }
        System.out.println("Answered " + correctAnsweredQuestions.size() + " correctly!!!");
        System.out.println("Following questions were wrongly answered: Total " + wrongAnsweredQuestions.size());
    }
}
