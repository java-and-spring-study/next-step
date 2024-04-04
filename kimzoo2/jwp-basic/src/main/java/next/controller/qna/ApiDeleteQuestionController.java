package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;
import next.service.QuestionService;

public class ApiDeleteQuestionController extends AbstractController {

	private final QuestionService questionService;

	public ApiDeleteQuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		questionService.delete(Long.parseLong(request.getParameter("questionId")));
		return jsonView().addObject("result", Result.ok());
	}
}
