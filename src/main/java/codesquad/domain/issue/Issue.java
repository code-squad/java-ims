package codesquad.domain.issue;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
import codesquad.domain.deletehistory.ContentType;
import codesquad.domain.deletehistory.DeleteHistory;
import codesquad.domain.label.Label;
import codesquad.domain.milestone.Milestone;
import codesquad.domain.user.User;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity {

    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String subject;

    @Size(min = 3)
    @Lob
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)      //지연로딩을 하면, milestone에서 OneToMany관계를 안맺어도 됨?
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
    private Label label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    private boolean deleted = false;
    private boolean closed = false;

    public Issue() {
    }

    public Issue(String subject, String comment, User writer) {
        this(0L, subject, comment, writer);
    }

    public Issue(long id, String subject, String comment, User writer) {
        super(id);
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

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                ", milestone=" + milestone +
                ", label=" + label +
                ", assignee=" + assignee +
                ", deleted=" + deleted +
                ", closed=" + closed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Issue issue = (Issue) o;
        return Objects.equals(subject, issue.subject) &&
                Objects.equals(comment, issue.comment) &&
                Objects.equals(writer, issue.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subject, comment, writer);
    }

    public void update(User loginUser, Issue updatedIssue) {
        if (!this.writer.equals(loginUser)) throw new CannotUpdateException("you can't update this issue");
        this.subject = updatedIssue.subject;
        this.comment = updatedIssue.comment;
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!this.isOwner(loginUser)) throw new CannotDeleteException("you can't delete this issue");
        List<DeleteHistory> histories = new ArrayList<>();
        this.deleted = true;
        histories.add(new DeleteHistory(ContentType.ISSUE, this.getId(), this.getWriter()));
        return histories;
    }
}
