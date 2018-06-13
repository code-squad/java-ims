package codesquad.dto;

import codesquad.domain.Issue;

import javax.validation.constraints.Size;

public class IssueDto {
    private Long id;

    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3, max = 1000)
    private String contents;

    public Long getId() {
        return id;
    }

    public IssueDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Issue toIssue() {
        return new Issue(title, contents);
    }
}
