package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.validation.constraints.Size;

public class IssueDto {
    @Size(min = 3, max = 100)
    private String subject;

    @Size(min = 3)
    private String comment;

    private User writer;

    public IssueDto() {

    }

    public IssueDto(String subject, String comment, User writer) {
        super();
        this.subject = subject;
        this.comment = comment;
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

    public User getWriter() {
        return writer;
    }

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public Issue _toIssue() {
        return new Issue(this.subject, this.comment, this.writer);
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
