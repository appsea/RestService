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
        /*bank.add(new QuestionBank("Q1-A00-211qa204-20170130-17520396.txt", 201));
        bank.add(new QuestionBank("Q2-1A00-201-Q&A-CertMagic-20170130-175206262.txt", 93));
        bank.add(new QuestionBank("Q3-A00-201-Fx-20170130-175207599.txt", 133));
        bank.add(new QuestionBank("Q4-A00-211-20170130-1752144.txt", 66));
        bank.add(new QuestionBank("Q5-A00-211-Q&A-Demo-CertMagic-20170130-17521941.txt", 17));*/
        //bank.add(new QuestionBank("Q6-A00-211qa70-20170130-175156234.txt", 66));
        //bank.add(new QuestionBank("Main-All-Questions.txt", 436));
        bank.add(new QuestionBank("Advance-sas-questions.txt", 436));
        //bank.add(new QuestionBank("files/Advance-sas-questions.txt", 260));
        //Total were 1142 then reduced to 1034
        return bank;
    }
}
