package com.exuberant.rest.survey.exam.sas;

import com.exuberant.rest.survey.QuestionBank;
import com.exuberant.rest.survey.exam.question.QuestionBanker;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.parser.QuestionParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rakesh on 26-Sep-2017.
 */
@Service
public class SasQuestionBanker implements QuestionBanker {
    private final QuestionParser questionParser;
    private final List<Question> allQuestions;

    public SasQuestionBanker(QuestionParser questionParser) throws Exception {
        this.questionParser = questionParser;
        allQuestions = readAllQuestions();
    }

    private List<Question> readAllQuestions() throws Exception {
        Collection<QuestionBank> bank = buildQuestionBank();
        List<Question> uniqueQuestions = new ArrayList<>();
        for (QuestionBank questionBank : bank) {
            List<Question> questions = questionParser.parse(questionBank);
            uniqueQuestions.addAll(questions);
        }
        return uniqueQuestions;
    }


    @Override
    public List<Question> getAllQuestions() throws Exception {
        return allQuestions;
    }

    private Collection<QuestionBank> buildQuestionBank() {
        Collection<QuestionBank> bank = new ArrayList<>();
        bank.add(new QuestionBank("Base SAS Question.txt", 434, 13, 10027, false));
        bank.add(new QuestionBank("Advance-sas-questions.txt", 261, 2, 10001, false));
        return bank;
    }
}
