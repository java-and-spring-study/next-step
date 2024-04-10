package next.service;

import next.dao.QuestionDao;
import next.model.Question;

public class QuestionService {
	private final QuestionDao questionDao;

	public QuestionService(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public void delete(long questionId){
		Question foundQuestion = questionDao.findById(questionId);
		if(foundQuestion.isCommentCountZero()){
			throw new IllegalStateException("삭제할 수 없는 게시글입니다.");
		}
	}
}
