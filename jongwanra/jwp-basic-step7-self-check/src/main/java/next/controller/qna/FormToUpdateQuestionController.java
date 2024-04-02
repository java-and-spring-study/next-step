package next.controller.qna;

import core.jdbc.RDBJdbcTemplate;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class FormToUpdateQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(FormToUpdateQuestionController.class);

    private QuestionDao questionDao = new QuestionDao(new RDBJdbcTemplate());

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        User user = UserSessionUtils.getUserFromSession(req.getSession());
        if(user == null) {
            return jspView("/users/loginForm");
        }

        Long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = questionDao.findById(questionId);

        if(!isWriter(user, question.getWriter())){
            throw new RuntimeException("작성자만 글 수정이 가능합니다:)");
        }

        return jspView("/qna/updateForm.jsp").addObject("question", question);
    }

    private boolean isWriter(User user, String writer) {
        return user.getName().equals(writer);
    }
}
