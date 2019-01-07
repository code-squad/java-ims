package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
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

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

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


    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
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
        List<DeleteHistory> histories = new ArrayList<>();
        if (!this.isOwner(loginUser)) throw new CannotDeleteException("you can't delete this issue");
        this.deleted = true;
        histories.add(new DeleteHistory(ContentType.ISSUE, this.getId(), this.getWriter()));
        return histories;
    }
}
