package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.validation.constraints.Size;

public class IssueDto {

    @Size(min = 5, max = 200)
    private String subject;

    @Size(min = 5, max = 1000)
    private String comment;

    private User writer;

    public IssueDto() {

    }

    public IssueDto(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public IssueDto(String subject, String comment, User writer) {
        this(subject, comment);
        this.writer = writer;
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
        return new Issue(this.subject, this.comment, this.writer);
    }

    public Issue _toIssue(User writer) {
        this.writer = writer;
        return new Issue(this.subject, this.comment, this.writer);
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }
}
