package codesquad.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import codesquad.domain.Issue;
import codesquad.domain.Label;
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
	private List<User> manager;
	private Label label;
	private ArrayList<String> comments;
	private boolean deleted = false;

	public IssueDto() {
	}

	public IssueDto(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
	}
	
	public IssueDto(String subject, String comment, User writer, List<User> manager, Label label) {
		this.subject = subject;
		this.comment = comment;
		this.writer = writer;
		this.manager = manager;
		this.label = label;
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


	public Milestone getMilestone() {
		return milestone;
	}


	public List<User> getManager() {
		return manager;
	}


	public Label getLabel() {
		return label;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}
	
	@Override
	public String toString() {
		return "IssueDto [subject=" + subject + ", comment=" + comment + "]";
	}
}
