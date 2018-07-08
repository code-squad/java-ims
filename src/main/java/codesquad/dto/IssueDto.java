package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import java.util.Objects;

public class IssueDto {
    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String comment;

    private User writer;

    @JsonIgnore
    private Milestone milestone;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
        milestone = null;
    }

    public IssueDto(String subject, String comment, User writer, Milestone milestone) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
        this.milestone = milestone;
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

    public Issue toIssue() {
        return new Issue(subject, comment);
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
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

    @Override
    public String toString() {
        return "IssueDto{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }
}
