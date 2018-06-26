package codesquad.domain;

import codesquad.UnAuthorizedException;
import support.domain.AbstractEntity;
import support.domain.UriGeneratable;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity implements UriGeneratable {
    private static final String ROOT_PATH = "/issues/";

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private User writer;

    @Column(length = 30, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

    @Enumerated(EnumType.STRING)
    private Label label;

    @OneToOne
    private Milestone milestone;

    @OneToOne
    private User assignee;

    @OneToMany(mappedBy = "issue")
    private List<Comment> comments;

    private boolean deleted;

    public Issue() {
    }

    public Issue(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Issue(long id, String title, String content) {
        super(id);
        this.title = title;
        this.content = content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
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

    public Issue setMilestone(User loginUser, Milestone milestone) {
        if (!loginUser.equals(writer)) {
            throw new UnAuthorizedException();
        }
        this.milestone = milestone.addIssue(this);;

        return this;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "writer=" + writer +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", label=" + label +
                ", milestone=" + milestone +
                ", assignee=" + assignee +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Issue)) return false;
        if (!super.equals(o)) return false;
        Issue issue = (Issue) o;
        return deleted == issue.deleted &&
                Objects.equals(writer, issue.writer) &&
                Objects.equals(title, issue.title) &&
                Objects.equals(content, issue.content) &&
                status == issue.status;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), writer, title, content, status, deleted);
    }

    @Override
    public String generateUri() {
        return ROOT_PATH + getId();
    }

    public Issue update(User loginUser, Issue issue) {
        if (!writer.equals(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.content = issue.content;
        return this;
    }

    public void delete(User loginUser) {
        if (!loginUser.equals(writer)) {
            throw new UnAuthorizedException();
        }
        deleted = true;
    }

    public boolean isOpen() {
        return status.isOpen();
    }

    public User getAssignee() {
        return assignee;
    }

    public Issue setAssignee(User loginUser, User assignee) {
        if (!loginUser.equals(writer)) {
            throw new UnAuthorizedException();
        }
        this.assignee = assignee;
        return this;
    }

    public boolean isAssignee(User assignee) {
        return this.assignee.equals(assignee);
    }

    public Issue setLabel(User loginUser, long labelId) {
        if (!loginUser.equals(writer)) {
            throw new UnAuthorizedException();
        }
        this.label = Label.getLabel(labelId);
        return this;
    }

    public Label getLabel() {
        return label;
    }

    public boolean isLabel(Label label) {
        return this.label == label;
    }

    public Comment addComment(User loginUser, Comment comment) {
        comment.setWriter(loginUser);
        comments.add(comment);
        return comment;
    }
}
