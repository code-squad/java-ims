package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Issue extends AbstractEntity {
	@Column(nullable = false)
	private String subject;

	@Column(nullable = false)
	private String comment;

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

	public Issue setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public Issue setComment(String comment) {
		this.comment = comment;
		return this;
	}


}
