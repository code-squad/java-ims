package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {
	@Size(min = 3, max = 100)
	@Column(nullable = false, length = 100)
	private String subject;

	@Column(nullable = false)
	private String comment;

	public Issue() {
	}

	public Issue(String subject, String comment) throws IllegalArgumentException {
		super(0L);
		this.subject = subject;
		this.comment = comment;

		if (this.subject == null || this.comment == null) {
			throw new IllegalArgumentException();
		}
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

	@Override
	public String toString() {
		return "Issue{" +
				"subject='" + subject + '\'' +
				", comment='" + comment + '\'' +
				'}';
	}
}
