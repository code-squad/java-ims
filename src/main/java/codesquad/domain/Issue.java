package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {
	@Size(min = 3, max = 100)
	@Column(nullable = false, length = 100)
	private String subject;

	@Column(nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	public Issue() {
	}

	public Issue(String subject, String comment, User writer) throws IllegalArgumentException {
		this(0L, subject, comment, writer);
	}

	public Issue(long id, String subject, String comment, User writer) throws IllegalArgumentException {
		super(id);

		if (subject == null || comment == null || writer == null) {
			throw new IllegalArgumentException();
		}

		this.subject = subject;
		this.comment = comment;
		this.writer = writer;
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

	public boolean isOwner(User loginUser) {
		return writer.equals(loginUser);
	}

	public void update(User loginUser, IssueDto target) {
		if (!isOwner(loginUser)) {
			throw new UnAuthorizedException();
		}

		this.subject = target.getSubject();
		this.comment = target.getComment();
	}

	public IssueDto _toIssueDto() {
		return new IssueDto(this.subject, this.comment);
	}

	@Override
	public String toString() {
		return "Issue{" +
				"subject='" + subject + '\'' +
				", comment='" + comment + '\'' +
				", writer=" + writer +
				'}';
	}
}
