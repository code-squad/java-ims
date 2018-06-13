package codesquad.dto;

import codesquad.domain.Issue;

import javax.validation.constraints.Size;

public class IssueDto {
    private Long id;

    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3, max = 1000)
    private String contents;

    public IssueDto() {
    }

    public IssueDto(String title, String contents) {
        this(0L, title, contents);
    }

    public IssueDto(long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public IssueDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public IssueDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public IssueDto setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public Issue toIssue() {
        return new Issue(title, contents);
    }
}
