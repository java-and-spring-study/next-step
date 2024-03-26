package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.View;
import next.dao.AnswerDao;
import next.model.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class ListAnswerController implements Controller {
    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnswerDao answerDao = new AnswerDao();

        List<Answer> answers = answerDao.findAll();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(answers));

        return new JsonView(answers);
    }
}
