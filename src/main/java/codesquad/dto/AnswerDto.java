package codesquad.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import codesquad.domain.Answer;
import codesquad.domain.User;

public class AnswerDto {
	@Id
	@GeneratedValue
	private long id;
	
	@Size(min = 3, max = 300)
	@Column(nullable = false, length = 300)
	private String comment;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "answer_writer"))
	private User writer;
	
	private boolean deleted = false;
	
	private LocalDateTime createTime;
	
	public AnswerDto() {
	}
	
	public AnswerDto(String comment, User writer, LocalDateTime createTime) {
		this.comment = comment;
		this.writer = writer;
		this.createTime = createTime;
	}
	
	public Answer _toAnswer() {
		return new Answer(this.comment);
	}
	
	//getter() methods

	public long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public User getWriter() {
		return writer;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	@Override
	public String toString() {
		return "AnswerDto [id=" + id + ", comment=" + comment + ", writer=" + writer + ", deleted=" + deleted
				+ ", createTime=" + createTime + "]";
	}
}
