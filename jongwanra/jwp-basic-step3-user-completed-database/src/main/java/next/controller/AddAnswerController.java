package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;

public class AddAnswerController extends AbstractController {
	private AnswerDao answerDao = new AnswerDao();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// Answer answer = new Answer(Long.parseLong(req.getParameter("questionId")), req.getParameter("writer"),
		// 	req.getParameter("contents"));
		//
		// answerDao.insert(answer);
		return super.execute(req, resp);
	}
}
