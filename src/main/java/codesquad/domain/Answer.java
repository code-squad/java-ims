package codesquad.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import codesquad.dto.AnswerDto;

@Entity
public class Answer {
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
	
	public Answer() {
	}
	
	public Answer(String comment) {
		this.comment = comment;
		this.createTime = LocalDateTime.now();
	}
	
	public AnswerDto _toAnswerDto() {
		return new AnswerDto(this.comment, this.writer, this.createTime);
	}
	
	public Answer writeBy(User loginUser) {
		this.writer = loginUser;
		return this;
	}
	
	public void delete(User loginUser) {
		if (!this.isOwner(loginUser)) {
			return;
		}
		this.deleted = true;
	}
	
	public boolean isOwner(User loginUser) {
		return this.writer.equals(loginUser);
	}
	
	//getter(), setter() methods.
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
		return "Answer [id=" + id + ", comment=" + comment + ", writer=" + writer + ", deleted=" + deleted
				+ ", createTime=" + createTime + "]";
	}
}
