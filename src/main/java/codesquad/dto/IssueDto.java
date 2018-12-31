package codesquad.dto;

import codesquad.domain.Issue;

import javax.validation.constraints.Size;

public class IssueDto {
    @Size(min = 6, max = 20)
    private String userId;

    @Size(min = 3, max = 20)
    private String subject;

    @Size(min = 5, max = 100)
    private String comment;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Issue _toIssue() {
        return new Issue(this.subject, this.comment);
    }
}
