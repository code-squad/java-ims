package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity implements UriGeneratable{

    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false, length = 50)
    private String subject;

    @Size(min = 5)
    @Lob
    @Column(nullable = false)
    private String comment;

    @Column
    private Boolean deleted = false;

    @Column
    private Boolean openState = true;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    @Enumerated(EnumType.STRING)
    private Label currentLabel;

    public Issue () {}

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
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

    public Label getCurrentLabel() {
        return currentLabel;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Issue delete() {
        this.deleted = true;
        return this;
    }

    public Issue writtenBy(User loginUser) {
        this.writer = loginUser;
        return this;
    }

    public Issue assignDirector(User assignee) {
        this.assignee = assignee;
        return this;
    }

    public void assignLabel(Label label) {
        this.currentLabel = label;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public User getWriter() {
        return writer;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(subject, comment);
    }

    public boolean isWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public Issue update(IssueDto target) {
        this.subject = target.getSubject();
        this.comment = target.getComment();
        return this;
    }

    @Override
    public String generateUrl() {
        return String.format("/issues/%d", getId());
    }

    public boolean isOpen() {
        return openState;
    }

    public boolean isClosed() {
        return !openState;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public User getAssignee() {
        return assignee;
    }

    public Issue registerMilestone(Milestone milestone) {
        this.milestone = milestone;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Issue issue = (Issue) o;
        return Objects.equals(subject, issue.subject) &&
                Objects.equals(comment, issue.comment) &&
                Objects.equals(writer, issue.writer) &&
                Objects.equals(deleted, issue.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), subject, comment, writer, deleted);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }

}

