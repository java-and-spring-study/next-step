package core.mvc;

import core.jdbc.JdbcTemplate;
import next.controller.qna.AddAnswerController;
import next.controller.qna.ApiDeleteQuestionController;
import next.controller.qna.DeleteQuestionController;
import next.dao.QuestionDao;
import next.service.QuestionService;

public class Configuration {

	private static QuestionDao questionDao;
	private static DeleteQuestionController deleteQuestionController;
	private static AddAnswerController addAnswerController;
	private static ApiDeleteQuestionController apiDeleteQuestionController;

	private static JdbcTemplate jdbcTemplate;
	private static QuestionService questionService;


	public static JdbcTemplate jdbcTemplate(){
		if(jdbcTemplate == null) {
			jdbcTemplate = new JdbcTemplate();
		}
		return jdbcTemplate;
	}

	public static QuestionDao questionDao(){
		if(questionDao == null) {
			return new QuestionDao(jdbcTemplate());
		}
		return questionDao;
	}

	public static QuestionService questionService() {
		if(questionService == null){
			return new QuestionService(questionDao());
		}
		return questionService;
	}

	public static ApiDeleteQuestionController apiDeleteQuestionController(){
		if(apiDeleteQuestionController == null){
			return new ApiDeleteQuestionController(questionService());
		}
		return apiDeleteQuestionController;
	}

	public static DeleteQuestionController deleteQuestionController(){
		if(deleteQuestionController == null) {
			return new DeleteQuestionController(questionService());
		}
		return deleteQuestionController;
	}

	public static AddAnswerController addAnswerController(){
		if(addAnswerController == null) {
			return new AddAnswerController(questionDao());
		}
		return addAnswerController;
	}
}
