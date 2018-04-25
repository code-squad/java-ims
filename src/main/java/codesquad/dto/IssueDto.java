package codesquad.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;

public class IssueDto {
	@Size(min = 3, max = 100)
	@Column(unique = false, nullable = false, length = 100)
	private String subject;

	@Size(min = 3, max = 300)
	@Column(nullable = false, length = 300)
	private String comment;
	
	private User writer;
	private Milestone milestone;
	private ArrayList<String> comments;
	private boolean deleted = false;

	public IssueDto() {
	}

	public IssueDto(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
	}
	
	public IssueDto(String subject, String comment, User writer) {
		this.subject = subject;
		this.comment = comment;
		this.writer = writer;
	}

	public Issue _toIssue() {
		return new Issue(this.subject, this.comment);
	}

	//getter() setter() methods
	public String getSubject() {
		return subject;
	}

	public List<String> getComments() {
		return comments;
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

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "IssueDto [subject=" + subject + ", comment=" + comment + ", writer=" + writer + ", milestone="
				+ milestone + ", comments=" + comments + ", deleted=" + deleted + "]";
	}
}
