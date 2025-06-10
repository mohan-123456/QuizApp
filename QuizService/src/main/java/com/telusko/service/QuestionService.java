package com.telusko.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.telusko.model.Question;
import com.telusko.model.QuestionWrapper;
import com.telusko.model.Response;
import com.telusko.repository.QuestionRepo;

@Service
public class QuestionService {

	@Autowired
	QuestionRepo questionRepo;

	public ResponseEntity<List<Question>> getAllQuestions() {
		// TODO Auto-generated method stub
		try {
			return  new ResponseEntity(questionRepo.findAll(),HttpStatus.OK);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return  new ResponseEntity(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		// TODO Auto-generated method 
		try {
			return  new ResponseEntity(questionRepo.findQuestionsByCategory(category),HttpStatus.CONTINUE);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return  new ResponseEntity(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(Question question) {
		// TODO Auto-generated method stub
		 questionRepo.save(question);
		 return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	 public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
	        List<Integer> questions = questionRepo.findRandomQuestionsByCategory(categoryName, numQuestions);
	        return new ResponseEntity<>(questions, HttpStatus.OK);
	    }

	    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
	        List<QuestionWrapper> wrappers = new ArrayList<>();
	        List<Question> questions = new ArrayList<>();

	        for(Integer id : questionIds){
	            questions.add(questionRepo.findById(id).get());
	        }

	        for(Question question : questions){
	            QuestionWrapper wrapper = new QuestionWrapper();
	            wrapper.setId(question.getId());
	            wrapper.setQuestionTitle(question.getQuestionTitle());
	            wrapper.setOption1(question.getOption1());
	            wrapper.setOption2(question.getOption2());
	            wrapper.setOption3(question.getOption3());
	            wrapper.setOption4(question.getOption4());
	            wrappers.add(wrapper);
	        }

	        return new ResponseEntity<>(wrappers, HttpStatus.OK);
	    }
	    public ResponseEntity<Integer> getScore(List<Response> responses) {
	        int right = 0;
	        for(Response response : responses){
	            Question question = questionRepo.findById(response.getId()).get();
	            if(response.getResponse().equals(question.getRightAnswer()))
	                right++;
	        }
	        return new ResponseEntity<>(right, HttpStatus.OK);
	    }


}
