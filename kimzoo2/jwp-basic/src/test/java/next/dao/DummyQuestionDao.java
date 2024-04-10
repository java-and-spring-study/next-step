package next.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import core.jdbc.JdbcTemplate;
import next.model.Question;

public class DummyQuestionDao extends QuestionDao {

	private final AtomicLong idGenerator = new AtomicLong();
	private Map<Long, Question> questionMap = new HashMap<>();
	public DummyQuestionDao(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Question insert(Question question) {
		long id = idGenerator.getAndIncrement();
		questionMap.put(id, question);
		return question;
	}

	public Question findById(long id){
		return questionMap.get(id);
	}

}
