package codesquad.domain;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity {
	private static final Logger log = LoggerFactory.getLogger(Issue.class);

	@Column(nullable = false)
	private String subject;

	@Column(nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_milestone"))
	private Milestone milestone;

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

	public Issue(long id, User writer, Milestone milestone) {
		super(id);
		this.writer = writer;
		this.milestone = milestone;
	}

	public Milestone getMilestone() {
		return milestone;
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

	public void toMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Issue issue = (Issue) o;
		return Objects.equals(subject, issue.subject) &&
				Objects.equals(comment, issue.comment) &&
				Objects.equals(writer, issue.writer);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), subject, comment, writer);
	}

	@Override
	public String toString() {
		return "Issue{" +
				"subject='" + subject + '\'' +
				", comment='" + comment + '\'' +
				", writer=" + writer +
				", milestone=" + milestone +
				'}';
	}
}
