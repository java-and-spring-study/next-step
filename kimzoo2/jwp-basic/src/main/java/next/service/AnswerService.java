package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AnswerService {

	private AnswerDao answerDao = new AnswerDao();
	private QuestionDao questionDao;

	public AnswerService(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public int delete(long answerId){
		Answer foundAnswer = answerDao.findById(answerId);
		answerDao.delete(answerId);
		// 질문의 답변 수를 변경한다.
		Question foundQuestion = questionDao.findById(foundAnswer.getQuestionId());
		foundQuestion.minusCountOfComment();
		questionDao.updateCountOfComment(foundQuestion);
		return foundQuestion.getCountOfComment();
	}


}
