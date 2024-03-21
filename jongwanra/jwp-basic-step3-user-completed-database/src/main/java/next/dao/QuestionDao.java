package next.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Question;

public class QuestionDao {
	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";

		RowMapper rm = rs -> {
			Long questionId = Long.parseLong(rs.getString("questionId"));
			String writer = rs.getString("writer");
			String title = rs.getString("title");

			LocalDateTime createdDate = parseStringToLocalDateTime(rs.getString("createdDate"));
			Integer countOfAnswer = Integer.parseInt(rs.getString("countOfAnswer"));

			return new Question(questionId, writer, title,
				rs.getString("contents"), createdDate, countOfAnswer);
		};

		return jdbcTemplate.query(sql, rm);
	}

	private LocalDateTime parseStringToLocalDateTime(String notParsed) {
		String createdDateNotParsed = notParsed.split("\\.")[0];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return LocalDateTime.parse(createdDateNotParsed, formatter);
	}

	public Question findBy(Long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";

		RowMapper<Question> rm = rs -> {
			Long id = Long.parseLong(rs.getString("questionId"));
			String writer = rs.getString("writer");
			String title = rs.getString("title");
			LocalDateTime createdDate = parseStringToLocalDateTime(rs.getString("createdDate"));
			Integer countOfAnswer = Integer.parseInt(rs.getString("countOfAnswer"));

			return new Question(id, writer, title,
				rs.getString("contents"), createdDate, countOfAnswer);
		};

		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}
}
