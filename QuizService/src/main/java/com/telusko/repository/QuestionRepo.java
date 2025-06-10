package com.telusko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.telusko.model.Question;
@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

	List<Question> findQuestionsByCategory(String category);
	 @Query(value = "SELECT q.id FROM question q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
	List<Integer> findRandomQuestionsByCategory(String categoryName, Integer numQuestions);

}
