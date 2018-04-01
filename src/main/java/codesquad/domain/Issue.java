package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {
	@Size(min = 3, max = 20)
	@Column(unique = true, nullable = false, length = 20)
	private String subject;

	@Size(min = 6, max = 20)
	@Column(nullable = false)
	private String comment;

	public Issue() {
	}

	public Issue(String subject, String comment) {
		super(0L);
		this.subject = subject;
		this.comment = comment;
	}

	public IssueDto _toIssueDto() {
		return new IssueDto(subject, comment);
	}

}
