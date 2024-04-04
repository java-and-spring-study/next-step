package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class ListQuestionController implements Controller {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<Question> questions = questionDao.findAll();

		return new ModelAndView(new JsonView()).addObject("questions", questions);
	}
}
