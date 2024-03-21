package next.model;

import java.time.LocalDateTime;

public class Answer {
	private Long answerId;
	private String writer;
	private String contents;
	private LocalDateTime createdDate;

	private Question question;

	public Answer(Long answerId, String writer, String contents, LocalDateTime createdDate, Question question) {
		this.answerId = answerId;
		this.writer = writer;
		this.contents = contents;
		this.createdDate = createdDate;
		this.question = question;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public String getWriter() {
		return writer;
	}

	public String getContents() {
		return contents;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public Question getQuestion() {
		return question;
	}
}
