package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import codesquad.UnAuthenticationException;

@Entity
public class Issue {
	@Id
	@GeneratedValue
	private long id;

	@Size(min = 3, max = 100)
	@Column(unique = false, nullable = false, length = 100)
	private String subject;

	@Size(min = 3, max = 300)
	@Column(nullable = false, length = 300)
	private String comment;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_writer"))
	private User writer;

	private boolean deleted = false;

	public Issue() {
	}

	public Issue(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
	}

	public Issue writeBy(User loginUser) {
		this.writer = loginUser;
		return this;
	}
	
	public Issue update(User loginUser, String newComment) throws UnAuthenticationException {
		if (!this.isOwner(loginUser)) {
			throw new UnAuthenticationException();
		}
		this.comment = newComment;
		return this;
	}
	
	public void delete(User loginUser) throws UnAuthenticationException {
		if (!this.isOwner(loginUser)) {
			throw new UnAuthenticationException();
		}
		this.deleted = true;
	}
	
	public boolean isOwner(User loginUser) {
		return this.writer.equals(loginUser);
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	// === getter setter methods (behind) ===
	public long getId() {
		return id;
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
	
	public void setWriter(User writer) {
		this.writer = writer;
	}
}
