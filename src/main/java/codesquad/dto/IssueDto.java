package codesquad.dto;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import codesquad.domain.Issue;

public class IssueDto {
	@Size(min = 3, max = 100)
	@Column(unique = false, nullable = false, length = 100)
	private String subject;

	@Size(min = 3, max = 300)
	@Column(nullable = false, length = 300)
	private String comment;

	public IssueDto() {
	}

	public IssueDto(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
	}

	public Issue _toIssue() {
		return new Issue(this.subject, this.comment);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "IssueDto [subject=" + subject + ", comment=" + comment + "]";
	}
}
