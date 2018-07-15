package codesquad.dto;

import codesquad.domain.Issue;

import javax.persistence.Lob;
import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {

    private long id;

    @Size (min = 3, max = 100)
    private String subject;

    @Size (min = 3)
    @Lob
    private String comment;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public IssueDto(long id, String subject, String comment) {
        this.id = id;
        this.subject = subject;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Issue toIssue() {
        return new Issue(subject, comment);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
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
}
