package codesquad.dto;

import codesquad.domain.*;

import javax.persistence.Embedded;
import javax.validation.Valid;

public class IssueDto {

    private Long id;

    @Embedded @Valid
    private Content content;

    private User writer;

    public IssueDto() {

    }

    public IssueDto(Content content) {
        this.content = content;
    }

    public IssueDto(Long id, Content content, User writer) {
        this(content);
        this.writer = writer;
        this.id = id;
    }

    public Issue _toIssue() {
        return new Issue(this.content, this.writer);
    }

    public Issue _toIssue(User writer) {
        this.writer = writer;
        return new Issue(content, this.writer);
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Content getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "content=" + content +
                ", writer=" + writer +
                '}';
    }
}
