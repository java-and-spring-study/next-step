package next.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;
import next.model.Question;

public class AnswerDao {
	public List<Answer> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM ANSWERS";
		RowMapper<Answer> rm = rs -> {
			Long answerId = Long.parseLong(rs.getString("answerId"));
			String writer = rs.getString("writer");
			String contents = rs.getString("contents");
			LocalDateTime createdDate = parseStringToLocalDateTime(rs.getString("createdDate"));
			Long questionId = Long.parseLong(rs.getString("questionId"));
			Question question = new QuestionDao().findBy(questionId);
			return new Answer(answerId, writer, contents, createdDate, question);
		};

		return jdbcTemplate.query(sql, rm);
	}

	private LocalDateTime parseStringToLocalDateTime(String notParsed) {
		String createdDateNotParsed = notParsed.split("\\.")[0];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return LocalDateTime.parse(createdDateNotParsed, formatter);
	}
}
