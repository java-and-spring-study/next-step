package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.jdbc.RDBJdbcTemplate;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private AnswerDao answerDao = new AnswerDao(new RDBJdbcTemplate());
    private QuestionDao questionDao = new QuestionDao(new RDBJdbcTemplate());

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        Answer answerToCreate = new Answer(req.getParameter("writer"), req.getParameter("contents"),
                questionId);

        Answer createdAnswer = answerDao.insert(answerToCreate);
        questionDao.increaseCountOfComment(questionId);

        return jsonView().addObject("answer", createdAnswer);
    }
}
