package codesquad.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import support.domain.AbstractEntity;

@Entity
public class Answer extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	@JsonProperty
	private User writer;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
	@JsonProperty
	private Issue issue;

	@Lob
	@JsonProperty
	private String comment;

	public Answer() {
	}

	public Answer(User loginUser, String comment, Issue issue) {
		this.writer = loginUser;
		this.issue = issue;
		this.comment = comment;
	}
  

	public User getWriter() {
		return writer;
	}

	public Issue getIssue() {
		return issue;
	}

	public String getComment() {
		return comment;
	}

}
