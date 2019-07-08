package com.exuberant.rest.survey;

import com.exuberant.rest.survey.exam.sas.SasQuestionBanker;
import com.exuberant.rest.survey.local.JsonQuestionGenerator;
import com.exuberant.rest.survey.model.JsonQuestions;
import com.exuberant.rest.survey.model.Question;
import com.exuberant.rest.survey.model.QuestionWrapper;
import com.exuberant.rest.survey.parser.GenericQuestionParser;
import com.exuberant.rest.survey.parser.validator.GeneralQuestionValidator;
import com.exuberant.rest.survey.parser.validator.QuestionValidator;
import com.exuberant.rest.survey.service.RandomPaperSetter;
import com.exuberant.rest.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.exuberant.rest.util.Constants.*;

/**
 * Created by rakesh on 21-Sep-2017.
 */
public class Main {

    public static void main(String[] args) {
        try {
            Main main = new Main();
            main.generateQuestions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateQuestions() throws Exception {
        JsonQuestionGenerator jsonQuestionGenerator = new JsonQuestionGenerator();
        Collection<QuestionBank> banks = buildQuestionBank();
        for (QuestionBank questionBank : banks) {
            jsonQuestionGenerator.generateQuestions(questionBank);
        }
    }

    private Collection<QuestionBank> buildQuestionBank() {
        Collection<QuestionBank> bank = new ArrayList<>();
        bank.add(new QuestionBank(COMP_TIA_A_FILE_NAME, 600, 978, 2, 10019, true, true));
        bank.add(new QuestionBank(DVSA_FILE_NAME, 673, 773, 2, 10011, true, true, false));
        bank.add(new QuestionBank(DVSA_MOTOR_FILE_NAME, 600, 734, 1, 10001, true, true, true));
        // bank.add(new QuestionBank("CompTIA A+.txt", 500, 977, 1, 10001, true, true));
        bank.add(new QuestionBank(CATEGORIES_BASE_SAS_FILE_NAME, 200, 434, 13, 10001, true, true));
        bank.add(new QuestionBank(BASE_SAS_QUESTION_FILE_NAME,200, 435, 10, 10108, true, true));
        bank.add(new QuestionBank(ADVANCE_SAS_QUESTIONS_FILE_NAME, 150, 262, 7, 10072, true, true));
        return bank;
    }

    private void analyseQuestions() throws Exception {
        ResourceLoader resourceLoader = new LocalResourceLoader();
        QuestionValidator generalQuestionValidator = new GeneralQuestionValidator();
        GenericQuestionParser genericQuestionParser = new GenericQuestionParser(resourceLoader, generalQuestionValidator);
        genericQuestionParser.setResourceLoader(resourceLoader);
        SasQuestionBanker sasQuestionBanker = new SasQuestionBanker(genericQuestionParser);
        List<Question> allQuestions = sasQuestionBanker.getAllQuestions();
        Set<QuestionWrapper> wrappers = new HashSet<>();
        for (Question question : allQuestions) {
            QuestionWrapper newQ = new QuestionWrapper(question);
            if (!wrappers.contains(newQ)) {
                wrappers.add(newQ);
            }
        }
        allQuestions.clear();
        int count = 0;
        for (QuestionWrapper wrapper : wrappers) {
            Question question = wrapper.getQuestion();
            question.setNumber(++count);
            allQuestions.add(question);
        }
        System.err.println("TQ: " + wrappers.size());
        System.err.println("TQ: " + allQuestions.size());
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources", "sas_questions.json");
        //Path path = Paths.get("C:\\Data\\Rakesh\\Workspace\\Projects\\Java\\SasExam\\src\\main\\resources", "advanced_sas_questions.json");
        int questionVersion = 13;
        JsonQuestions jsonQuestions = new JsonQuestions(allQuestions, questionVersion, 10000, true, false);
        //System.err.println("Without: " + jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).count());
        jsonQuestions.getQuestions().stream().filter(que -> StringUtils.isEmpty(que.getExplanation())).forEach(que -> System.err.println(que.getDescription()));
        //jsonQuestions.getQuestions().stream().filter(que-> StringUtils.isEmpty(que.getExplanation())).forEach(System.out::println);
        Files.write(path, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonQuestions).getBytes());
        System.err.println("Created: " + path);
    }

    private void startExam() throws Exception {
        Examiner examiner = new Examiner(new RandomPaperSetter(new SasQuestionBanker(new GenericQuestionParser(new LocalResourceLoader(), new GeneralQuestionValidator()))));
        examiner.beginExam();
    }
}
