package next.model;

import java.time.LocalDateTime;

public class Question {
	private Long questionId;
	private String writer;
	private String title;
	private String contents;
	private LocalDateTime createdDate;
	private Integer countOfAnswer;

	public Question(Long questionId, String writer, String title, String contents, LocalDateTime createdDate,
		Integer countOfAnswer) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.countOfAnswer = countOfAnswer;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public Integer getCountOfAnswer() {
		return countOfAnswer;
	}

	@Override
	public String toString() {
		return "Question{" +
			"questionId=" + questionId +
			", writer='" + writer + '\'' +
			", title='" + title + '\'' +
			", contents='" + contents + '\'' +
			", createdDate=" + createdDate +
			", countOfAnswer=" + countOfAnswer +
			'}';
	}
}
