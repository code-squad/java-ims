package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

public class IssueDto {
    @Size(min = 5, max = 30)
    @Column(nullable = false, length = 30)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private User writer;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public Issue _toIssue() {
        return new Issue(this.subject, this.comment, this.writer);
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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }
}
