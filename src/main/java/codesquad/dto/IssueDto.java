package codesquad.dto;

import codesquad.domain.*;

import javax.persistence.Embedded;
import javax.validation.Valid;

public class IssueDto {

    @Embedded @Valid
    private Content content;

    private User writer;

    private Milestone milestone;

    private User assignee;

    private Label label;

    public IssueDto() {

    }

    public IssueDto(Content content) {
        this.content = content;
    }

    public IssueDto(Content content, User writer) {
        this(content);
        this.writer = writer;
    }

    public IssueDto(Content content, User writer, Milestone milestone, User assignee) {
        this(content, writer);
        this.milestone = milestone;
        this.assignee = assignee;
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

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "content=" + content +
                ", writer=" + writer +
                '}';
    }
}
