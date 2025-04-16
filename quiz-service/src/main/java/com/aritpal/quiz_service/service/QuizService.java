package com.aritpal.quiz_service.service;

import com.aritpal.quiz_service.feign.QuizInterface;
import com.aritpal.quiz_service.model.QuestionsWrapper;
import com.aritpal.quiz_service.model.Quiz;
import com.aritpal.quiz_service.model.Response;
import com.aritpal.quiz_service.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepo repo;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        repo.save(quiz);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionsWrapper>> getQuizQuestions(int id) {
        Quiz quiz = repo.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionsWrapper>> questions = quizInterface.getQuestionsById(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
