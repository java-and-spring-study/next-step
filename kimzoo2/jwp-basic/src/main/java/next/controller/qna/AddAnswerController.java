package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private AnswerDao answerDao = new AnswerDao();
    private QuestionDao questionDao;

    public AddAnswerController(QuestionDao questionDao) {
        questionDao = questionDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
            questionId);
        log.debug("answer : {}", answer);
        Answer savedAnswer = answerDao.insert(answer);

        // 질문의 답변 수를 변경한다.
        Question foundQuestion = questionDao.findById(questionId);
        foundQuestion.addCountOfComment();
        questionDao.updateCountOfComment(foundQuestion);

        return jsonView().addObject("answer", savedAnswer)
            .addObject("countOfComment", foundQuestion.getCountOfComment());
    }
}
