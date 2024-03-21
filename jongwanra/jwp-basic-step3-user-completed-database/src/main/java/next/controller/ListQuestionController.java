package next.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;

public class ListQuestionController implements Controller {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions = questionDao.findAll();

		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();

		ObjectMapper objectMapper = new ObjectMapper();
		writer.write(objectMapper.writeValueAsString(questions));
		return null;
	}
}
