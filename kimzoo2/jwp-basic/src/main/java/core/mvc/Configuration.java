package core.mvc;

import core.jdbc.JdbcTemplate;
import next.controller.HomeController;
import next.controller.qna.AddAnswerController;
import next.controller.qna.ApiDeleteQuestionController;
import next.controller.qna.ApiShowController;
import next.controller.qna.DeleteAnswerController;
import next.controller.qna.DeleteQuestionController;
import next.controller.qna.ShowController;
import next.dao.QuestionDao;
import next.service.AnswerService;
import next.service.QuestionService;

public class Configuration {

	private static QuestionDao questionDao;
	private static DeleteQuestionController deleteQuestionController;
	private static AddAnswerController addAnswerController;
	private static ApiDeleteQuestionController apiDeleteQuestionController;
	private static ShowController showController;
	private static DeleteAnswerController deleteAnswerController;
	private static ApiShowController apiShowController;
	private static HomeController homeController;

	private static JdbcTemplate jdbcTemplate;
	private static QuestionService questionService;
	private static AnswerService answerService;


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

	public static AnswerService answerService(){
		if(answerService == null){
			return new AnswerService(questionDao());
		}
		return answerService;
	}

	public static QuestionService questionService() {
		if(questionService == null){
			return new QuestionService(questionDao());
		}
		return questionService;
	}

	public static HomeController homeController(){
		if(homeController == null){
			return new HomeController(questionDao());
		}
		return homeController;
	}

	public static ApiShowController apiShowController(){
		if(apiShowController == null){
			return new ApiShowController(questionDao());
		}
		return apiShowController;
	}

	public static DeleteAnswerController deleteAnswerController() {
		if(deleteAnswerController == null){
			return new DeleteAnswerController(answerService());
		}
		return deleteAnswerController;
	}

	public static ShowController showController(){
		if(showController == null){
			return new ShowController(questionDao());
		}
		return showController;
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
