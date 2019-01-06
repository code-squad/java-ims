package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.validation.constraints.Size;

public class IssueDto {
    @Size(min = 3, max = 50)
    private String subject;

    @Size(min = 3, max = 500)
    private String comment;

    private User writer;

    private boolean deleted;

    public IssueDto() {
    }

    public IssueDto(String subject, String content, User writer) {
        this.subject = subject;
        this.comment = content;
        this.writer = writer;
    }

    public void writeBy(User user) {
        this.writer = user;
    }

    public String getSubject() {
        return subject;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Issue _toIssue() {
        return new Issue(this.subject, this.comment, this.writer, this.deleted);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
