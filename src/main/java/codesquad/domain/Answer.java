package codesquad.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquad.UnAuthenticationException;
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

	public Result checkOwnerResult(User loginUser) {
		if (!isOwner(loginUser)) {
			return Result.fail();
		}
		return Result.success();
	}

	public void checkOwner(User loginUser) throws UnAuthenticationException{
		if (!isOwner(loginUser)) {
			throw new UnAuthenticationException();
		}
	}

	public boolean isOwner(User loginUser) {
		return writer.equals(loginUser);
	}

	public void update(User loginUser, String comment) throws UnAuthenticationException {
		checkOwner(loginUser);
		this.comment = comment;
	}
	
}
