package codesquad.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.Milestone;
import codesquad.domain.User;

public class IssueDto {
	private long id;
	private User writer;
	private Milestone milestone;
	private List<Label> labels;

	@NotNull
	@Size(min = 3, max = 20)
	private String title;

	@NotNull
	private String contents;

	public IssueDto() {
	}

	public IssueDto(long id, User writer, String title, String contents, Milestone milestone, List<Label> label) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.milestone = milestone;
		this.labels = label;
	}

	public void addWriter(User loginUser) {
		this.writer = loginUser;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Issue _toIssue() {
		return new Issue(this.title, this.contents, this.writer, this.milestone, this.labels);
	}

	@Override
	public String toString() {
		return "IssueDto [writer=" + writer + ", milestone=" + milestone + ", title=" + title
				+ ", contents=" + contents + "]";
	}
}
