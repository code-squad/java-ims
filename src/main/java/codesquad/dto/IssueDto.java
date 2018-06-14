package codesquad.dto;

import codesquad.domain.Issue;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {
    @Size(min = 3, max = 50)
    @Column
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    public IssueDto() {};

    public IssueDto(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public IssueDto(long id, String subject, String comment) {
        super();
        this.subject = subject;
        this.comment = comment;
    }

    public Issue toIssue() {
        return new Issue(this.subject, this.comment);
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public IssueDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public IssueDto setComment(String comment) {
        this.comment = comment;
        return this;
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
