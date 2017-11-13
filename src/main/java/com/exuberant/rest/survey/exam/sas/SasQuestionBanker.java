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

    public SasQuestionBanker(QuestionParser questionParser) {
        this.questionParser = questionParser;
    }

    @Override
    public List<Question> getAllQuestions() throws Exception {
        Collection<QuestionBank> bank = buildQuestionBank();
        List<Question> uniqueQuestions = new ArrayList<>();
        for (QuestionBank questionBank : bank) {
            List<Question> questions = questionParser.parse(questionBank);
            uniqueQuestions.addAll(questions);
        }
        return uniqueQuestions;
    }

    private Collection<QuestionBank> buildQuestionBank() {
        Collection<QuestionBank> bank = new ArrayList<>();
        bank.add(new QuestionBank("Q1-A00-211qa204-20170130-17520396.txt", 204));
        bank.add(new QuestionBank("Q2-1A00-201-Q&A-CertMagic-20170130-175206262.txt", 123));
        bank.add(new QuestionBank("Q3-A00-201-Fx-20170130-175207599.txt", 140));
        bank.add(new QuestionBank("Q4-A00-211-20170130-1752144.txt", 127));
        bank.add(new QuestionBank("Q5-A00-211-Q&A-Demo-CertMagic-20170130-17521941.txt", 20));
        bank.add(new QuestionBank("Q6-A00-211qa70-20170130-175156234.txt", 70));
        return bank;
    }
}
