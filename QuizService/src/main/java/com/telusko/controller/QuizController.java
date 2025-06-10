package com.telusko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.model.Question;
import com.telusko.model.QuestionWrapper;
import com.telusko.model.Response;
import com.telusko.service.QuestionService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/question")
@Tag(name = "Question API", description = "Operations for managing questions")
public class QuizController {
	@Autowired
	QuestionService questionService;
	@GetMapping("/allQuestions")
	public  ResponseEntity<List<Question>> getAllQuestions() {
		return new ResponseEntity(questionService.getAllQuestions(),HttpStatus.OK);
	}
	
	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category)
	{
		return new ResponseEntity(questionService.getQuestionsByCategory(category),HttpStatus.OK);
	
	}
	
	@PostMapping("addQuestion")
	public ResponseEntity<String> addQuestion(@RequestBody Question question)
	{
		return new ResponseEntity(questionService.addQuestion(question),HttpStatus.CREATED);
	}
	
	@GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String categoryName, @RequestParam Integer numQuestions ){
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        //System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
    {
        return questionService.getScore(responses);
    }
	
	
	

}
