package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import next.service.QuestionService;

// 필수 13
public class DeleteQuestionController extends AbstractController {

	// 선택 14
	private final QuestionService questionService;

	public DeleteQuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		questionService.delete(Long.parseLong(request.getParameter("questionId")));
		return jspView("redirect:/");
	}
}
