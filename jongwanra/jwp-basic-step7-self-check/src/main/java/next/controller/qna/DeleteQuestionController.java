package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DeleteQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);

    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();


    /**
     * 1. 질문자와 답변자가 다른 경우가 한 개라도 있으면 삭제가 불가능하다.
     * 2. 웹과 모바일 앱에서 전부 지원하려고 한다. 웹브라우저를 통해 접근할 경우 Jsp Redirect / 앱을 토애 접근할 경우 JsonView 활용
     */

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        User user = UserSessionUtils.getUserFromSession(req.getSession());
        if(user == null) {
            return jspView("/users/loginForm");
        }

        Long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        if(!isWriter(user, question)) {
            throw new RuntimeException("삭제할 수 있는 권한이 없습니다:)");
        }

        if(!canDelete(question, answers)) {
            throw new RuntimeException("다른 답변자가 존재하여 게시글을 지울 수 없습니다:)");
        }

        deleteAll(question, answers);

        // 웹과 모바일 앱에서 전부 지원하려고 한다. 웹브라우저를 통해 접근할 경우 Jsp Redirect / 앱을 토애 접근할 경우 JsonView 활용
        return handleResponse();
    }

    protected abstract ModelAndView handleResponse();



    private void deleteAll(Question question, List<Answer> answers) {
        answerDao.delete(toIds(answers));
        questionDao.delete(question.getQuestionId());
    }

    private List<Long> toIds(List<Answer> answers) {
        return answers.stream()
                .map(Answer::getAnswerId)
                .collect(Collectors.toList());
    }

    private  boolean isWriter(User user, Question question) {
        return user.getName().equals(question.getWriter());
    }

    private boolean canDelete(Question question, List<Answer> answers) {
        final String questionWriter = question.getWriter();
        for (Answer answer : answers) {
            final String answerWriter = answer.getWriter();
            if(!questionWriter.equals(answerWriter)) {
                return false;
            }
        }
        return true;
    }
}
