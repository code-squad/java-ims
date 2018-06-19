package codesquad.dto;

import codesquad.domain.Issue;

import javax.validation.constraints.Size;

public class IssueDto {
    private Long id;

    @Size(min = 3, max = 100)
    private String subject;

    @Size(min = 3, max = 1000)
    private String comment;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment) {
        this(0L, subject, comment);
    }

    public IssueDto(long id, String subject, String comment) {
        this.id = id;
        this.subject = subject;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public IssueDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public IssueDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public IssueDto setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Issue toIssue() {
        return new Issue(subject, comment);
    }
}
