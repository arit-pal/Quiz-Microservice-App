package com.aritpal.quiz_service.controller;

import com.aritpal.quiz_service.model.QuestionsWrapper;
import com.aritpal.quiz_service.model.QuizDto;
import com.aritpal.quiz_service.model.Response;
import com.aritpal.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService service;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz
            (@RequestBody QuizDto quizDto) {
        return service.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionsWrapper>> getQuizQuestions(@PathVariable int id) {
        return service.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitSubmit
            (@PathVariable int id, @RequestBody List<Response> responses) {
        return service.calculateResult(id, responses);
    }
}
