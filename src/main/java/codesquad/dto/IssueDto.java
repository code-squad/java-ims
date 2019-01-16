package codesquad.dto;

import codesquad.domain.*;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IssueDto {

    private Long id;

    @NotBlank
    @Size(min = 5, max = 200)
    private String subject;

    @Size(min = 5, max = 1000)
    @NotBlank
    private String comment;

    private User writer;

    public IssueDto() {

    }

    public IssueDto(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public IssueDto(Long id, String subject, String comment, User writer) {
        this(subject, comment, writer);
        this.id = id;
        this.writer = writer;
    }

    public Issue _toIssue() {
        return new Issue(new Content(this.subject, this.comment), this.writer);
    }

    public Issue _toIssue(User writer) {
        this.writer = writer;
        return _toIssue();
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "IssueDto{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }
}
