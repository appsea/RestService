package com.exuberant.survey.service;

import com.exuberant.survey.exam.question.QuestionBanker;
import com.exuberant.survey.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by rakesh on 24-Sep-2017.
 */
@Service
public class RandomPaperSetter implements PaperSetter {
    public static int questionCount = 2;
    private final QuestionBanker questionBanker;

    public RandomPaperSetter(QuestionBanker questionBanker) {
        this.questionBanker = questionBanker;
    }

    @Override
    public List<Question> buildQuestionPaper() throws Exception {
        List<Question> questions = questionBanker.getAllQuestions();
        Random random = new Random();
        Set<Question> questionPaper = new HashSet<>();
        while (questionPaper.size() < questionCount) {
            questionPaper.add(questions.get(random.nextInt(questions.size())));
        }
        return new ArrayList<>(questionPaper);
    }
}
