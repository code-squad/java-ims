package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;

import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {
    @Size(min = 3, max = 100)
    private String subject;

    @Size(min = 3)
    private String comment;

    private User writer;

    private Milestone milestone;

    private boolean deleted;

    private boolean closed;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment, User writer, boolean deleted, boolean closed) {
        super();
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
        this.deleted = deleted;
        this.closed = closed;
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

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Issue _toIssue() {
        return new Issue(this.subject, this.comment, this.writer, this.deleted, this.closed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IssueDto)) return false;
        IssueDto issueDto = (IssueDto) o;
        return Objects.equals(subject, issueDto.subject) &&
                Objects.equals(comment, issueDto.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, comment);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
