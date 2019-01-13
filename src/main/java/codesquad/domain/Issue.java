package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue extends AbstractEntity {
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    @Lob
    @Size(min = 3)
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_milestone"))
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_assignee"))
    private User assignee;

    @ManyToMany
    private List<Label> labels = new ArrayList<>();

    private boolean deleted = false;

    private boolean closed = false;

    public Issue() {
    }

    public Issue(String subject, String comment, User writer, boolean deleted, boolean closed) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
        this.deleted = deleted;
        this.closed = closed;
    }

    public Issue(long id, String subject, String comment, User writer, boolean deleted, boolean closed, User assignee) {
        super(id);
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
        this.deleted = deleted;
        this.closed = closed;
        this.assignee = assignee;
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

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isClosed() {
        return closed;
    }

    public String getOpen() {
        if (this.closed) return "Closed";
        return "Open";
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer, this.deleted, this.closed);
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isAsignee(User loginUser) {
        return assignee.equals(loginUser);
    }

    public void update(User loginUser, IssueDto updatedIssue) {
        if (!isOwner(loginUser)) throw new UnAuthorizedException();
        this.subject = updatedIssue.getSubject();
        this.comment = updatedIssue.getComment();
    }

    public List<DeleteHistory> delete(User loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) throw new CannotDeleteException("You can't delete this issue.");
        this.deleted = true;
        List<DeleteHistory> temp = new ArrayList<>();
        temp.add(new DeleteHistory(ContentType.ISSUE, getId(), loginUser));

        return temp;
    }

    public void close(User loginUser) throws Exception {
        if (this.closed) throw new Exception("This issue was already closed");
        if (!isAsignee(loginUser)) throw new UnAuthorizedException("You're not assignee");
        this.closed = true;
    }

    public boolean setLabel(Label label) {
        if (labels.contains(label)) {
            return labels.remove(label);
        }
        return labels.add(label);
    }
}