package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.View;
import next.dao.QuestionDao;
import next.model.Question;

public class ListQuestionController implements Controller {
	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions = questionDao.findAll();

		return new JsonView(questions);
	}
}
