package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.validation.constraints.Size;

public class IssueDto {
	@Size(min = 3, max = 100)
	private String subject;

	private String comment;

	public IssueDto() {
	}

	public IssueDto(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
	}

	public String getSubject() {
		return subject;
	}

	public IssueDto setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public IssueDto setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public Issue _toIssue(User writer) throws IllegalArgumentException {
		return new Issue(this.subject, this.comment, writer);
	}

	@Override
	public String toString() {
		return "IssueDto{" +
				"subject='" + subject + '\'' +
				", comment='" + comment + '\'' +
				'}';
	}
}
