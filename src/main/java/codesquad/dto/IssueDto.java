package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {
    @Size(min = 3, max = 100)
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    private User writer;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public boolean isOwner(User loginUser) {
        return this.writer.equals(loginUser);
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

    public Issue _toIssue() {
        return new Issue(this.subject, this.comment, this.writer);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueDto issueDto = (IssueDto) o;
        return Objects.equals(subject, issueDto.subject) &&
                Objects.equals(comment, issueDto.comment) &&
                Objects.equals(writer, issueDto.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, comment, writer);
    }
}
