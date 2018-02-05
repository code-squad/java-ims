package codesquad.dto;

import codesquad.domain.Issue;

public class IssueDto {
	private String title;
	private String contents;

	public IssueDto() {
	}

	public IssueDto(String title, String contents) {
		this.title = title;
		this.contents = contents;
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

	public Issue toIssue() {
		return new Issue(this.title, this.contents);
	}
}
