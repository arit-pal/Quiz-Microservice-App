package com.aritpal.question_service.service;

import com.aritpal.question_service.model.Questions;
import com.aritpal.question_service.model.QuestionsWrapper;
import com.aritpal.question_service.model.Response;
import com.aritpal.question_service.repo.QuestionsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionsRepo repo;

    public ResponseEntity<List<Questions>> getAllQuestions() {
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("e: ", e);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Questions>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(repo.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            log.error("e: ", e);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Boolean> addQuestions(Questions questions) {
        try {
            repo.save(questions);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            log.error("e: ", e);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Boolean> updateQuestions(Questions questions) {
        try {
            repo.save(questions);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            log.error("e: ", e);
        } return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Boolean> deleteQuestions(int id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            log.error("e: ", e);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
        List<Integer> questions = repo.findQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionsWrapper>> getQuestionsById(List<Integer> questionIds) {
        List<QuestionsWrapper> wrappers = new ArrayList<>();
        List<Questions> questions = new ArrayList<>();

        for(Integer id: questionIds) {
            questions.add(repo.findById(id).get());
        }

        for(Questions question : questions) {
            QuestionsWrapper wrapper = new QuestionsWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrapper.setCategory(question.getCategory());
            wrapper.setDifficultyLevel(question.getDifficultyLevel());
            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response response: responses) {
            Questions question = repo.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())) {
                right++;
            }
        }
        return new  ResponseEntity<>(right, HttpStatus.OK);
    }
}
