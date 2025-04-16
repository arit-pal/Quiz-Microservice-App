package com.aritpal.question_service.controller;

import com.aritpal.question_service.model.Questions;
import com.aritpal.question_service.model.QuestionsWrapper;
import com.aritpal.question_service.model.Response;
import com.aritpal.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService service;

    @GetMapping("/getAllQuestions")
    public ResponseEntity<List<Questions>> getAllQuestions() {
        return service.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Questions>> getQuestionsByCategory(@PathVariable String category) {
        return service.getQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addQuestions(@RequestBody Questions questions) {
        service.addQuestions(questions);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateQuestions(@RequestBody Questions questions) {
        service.updateQuestions(questions);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteQuestions(@PathVariable int id) {
        service.deleteQuestions(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String categoryName, @RequestParam int numQuestions) {
        return service.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionsWrapper>> getQuestionsById
            (@RequestBody List<Integer> questionIds) {
        return service.getQuestionsById(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return service.getScore(responses);
    }
}
