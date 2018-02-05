package codesquad.domain;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Issue extends AbstractEntity {
	@Column(nullable = false)
	private String subject;

	@Column(nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	public Issue() {
	}

	public Issue(String subject, String comment) {
		this(0L, subject, comment);
	}

	public Issue(long id, String subject, String comment) {
		super(id);
		this.subject = subject;
		this.comment = comment;
	}

	public String getSubject() {
		return subject;
	}

	public String getComment() {
		return comment;
	}

	public User getWriter() {
		return writer;
	}

	public void writeBy(User loginUser) {
		this.writer = loginUser;
	}

	public boolean isOwner(User loginUser) {
		if (writer.equals(loginUser))
			return true;

		return false;
	}

	public void update(User loginUser, Issue target) {
		if (!isOwner(loginUser))
			throw new UnAuthorizedException();

		this.subject = target.subject;
		this.comment = target.comment;
	}
}
