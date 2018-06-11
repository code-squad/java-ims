package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.persistence.Lob;
import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {

    @Size(min = 3, max = 50)
    private String subject;

    @Size(min = 5)
    @Lob
    private String comment;

    private User writer;

    public IssueDto() {}

    public IssueDto(@Size(min = 3, max = 50) String subject, @Size(min = 5) String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public IssueDto(@Size(min = 3, max = 50) String subject, @Size(min = 5) String comment, User writer) {

        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
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


    public User getWriter() {
        return writer;
    }

    public IssueDto setWriter(User writer) {
        this.writer = writer;
        return this;
    }

    public Issue _toIssue() {
        return new Issue(this.subject, this.comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
