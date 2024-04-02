package next.controller.qna;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.jdbc.RDBJdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class CreateQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);

    private QuestionDao questionDao = new QuestionDao(new RDBJdbcTemplate());

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {

        String writer = req.getParameter("writer");
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");

        System.out.println("writer = " + writer);
        System.out.println("title = " + title);
        System.out.println("contents = " + contents);

        questionDao.insert(new Question(writer, title, contents));

        return jspView("/");
    }
}
