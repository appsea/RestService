package com.exuberant.rest.survey.service;

import com.exuberant.rest.survey.exam.question.QuestionBanker;
import com.exuberant.rest.survey.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by rakesh on 24-Sep-2017.
 */
@Service
public class RandomPaperSetter implements PaperSetter {
    public static int questionCount = 2;
    private final QuestionBanker questionBanker;
    private Random random = new Random();
    private List<Question> questions;

    public RandomPaperSetter(QuestionBanker questionBanker) throws Exception {
        this.questionBanker = questionBanker;
        this.questions = questionBanker.getAllQuestions();
    }

    @Override
    public List<Question> buildQuestionPaper() throws Exception {
        Set<Question> questionPaper = new HashSet<>();
        while (questionPaper.size() < questionCount) {
            questionPaper.add(questions.get(random.nextInt(questions.size())));
        }
        return new ArrayList<>(questionPaper);
    }

    @Override
    public Question pollNextQuestion() {
        Question question = questions.get(random.nextInt(questions.size()));
        return question;
    }
}
