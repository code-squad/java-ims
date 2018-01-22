package codesquad.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import codesquad.domain.Issue;
import codesquad.domain.User;

public class IssueDto {
	private User writer;
	
	@Size(min = 3)
	@NotNull
	private String title;
	
	@NotNull
	private String contents;

	public IssueDto() {
	}
	
	public void addWriter(User loginUser) {
		this.writer = loginUser;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public User getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}
	
	public Issue _toIssue() {
		return new Issue(this.title, this.contents, this.writer);
	}

	@Override
	public String toString() {
		return "IssueDto [writer=" + writer + ", title=" + title + ", contents=" + contents + "]";
	}
}
