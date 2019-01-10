package codesquad.domain;

import codesquad.dto.AnswerDto;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Issue extends AbstractEntity {

    @Embedded
    private Content content;

    @Embedded
    private Answers answers = new Answers();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
    private Label label;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    private boolean deleted = false;

    public Issue() {

    }

    public Issue(Content content, User writer) {
        this.writer = writer;
        this.content = content;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public IssueDto _toIssueDto() {
        return content.createIssueDto(getId(), writer);
    }

    public boolean isOneSelf(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public Issue update(Issue issue) {
        this.content = issue.content;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "content=" + content +
                ", writer=" + writer +
                ", milestone=" + milestone +
                ", assignee=" + assignee +
                ", deleted=" + deleted +
                '}';
    }

    public boolean isAssignee() {
        return assignee != null;
    }

    public boolean isMilestone() {
        return milestone != null;
    }

    public boolean isLabel() {
        return label != null;
    }

    public void addAnswer(Answer answer) {
        answers.addAnswer(answer);
    }

    public Answer updateAnswer(Answer originAnswer, Answer updatedAnswer) {
        return answers.updateAnswer(originAnswer, updatedAnswer);
    }

    public List<AnswerDto> obtainAnswerDtos() {
        return answers.obtainAnswerDtos();
    }
}
