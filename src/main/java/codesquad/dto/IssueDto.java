package codesquad.dto;

import codesquad.domain.Issue;

public class IssueDto {

	private long id;
	private String subject;
	private String comment;

	public IssueDto() {
	}

	public IssueDto(String subject, String comment) {
		this(0, subject, comment);
	}

	public IssueDto(long id, String subject, String comment) {
		this.id = id;
		this.subject = subject;
		this.comment = comment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Issue toIssue() {
		return new Issue(subject, comment);
	}

	@Override
	public String toString() {
		return "IssueDto [id=" + id + ", subject=" + subject + ", comment=" + comment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (id != other.id)
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

}
