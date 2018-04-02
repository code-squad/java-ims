package codesquad.dto;

import javax.validation.constraints.Size;

import codesquad.domain.Issue;
import codesquad.domain.User;

public class IssueDto {
	@Size(min = 3, max = 20)
	private String subject;

	@Size(min = 6, max = 20)
	private String comment;
	
	private User writer;

	public IssueDto() {
	}

	public IssueDto(String subject, String comment, User writer) {
		this.subject = subject;
		this.comment = comment;
		this.writer = writer;
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

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public Issue _toIssue() {
		return new Issue(subject, comment, writer);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IssueDto other = (IssueDto) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

}
