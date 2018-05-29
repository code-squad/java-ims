package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	@Column(nullable = false)
	private String subject;

	@Lob
	@Column(nullable = false)
	private String comment;

	public Issue() {
	}

	public Issue(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
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

	public IssueDto toIssueDto() {
		return new IssueDto(getId(), this.subject, this.comment);
	}

	@Override
	public String toString() {
		return "Issue [writer=" + writer + ", subject=" + subject + ", comment=" + comment + "]";
	}

}
