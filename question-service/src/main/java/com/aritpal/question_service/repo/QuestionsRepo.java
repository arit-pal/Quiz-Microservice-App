package com.aritpal.question_service.repo;

import com.aritpal.question_service.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepo extends JpaRepository<Questions, Integer> {

    @Query(value = "SELECT id FROM questions WHERE category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Integer> findQuestionsByCategory(String category, int numQ);

    List<Questions> findByCategory(String category);

}
