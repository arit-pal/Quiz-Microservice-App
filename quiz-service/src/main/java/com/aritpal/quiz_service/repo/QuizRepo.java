package com.aritpal.quiz_service.repo;

import com.aritpal.quiz_service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository<Quiz, Integer> {

}
