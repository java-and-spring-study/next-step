package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateQuestionController.class);

    private QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        Question questionToUpdate = questionDao.findById(questionId);
        questionToUpdate.update(title, contents);
        questionDao.update(questionToUpdate);

        return jspView("/");
    }
}
