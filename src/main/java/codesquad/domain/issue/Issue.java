package codesquad.domain.issue;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity {
    private static final Logger log = LoggerFactory.getLogger(Issue.class);

    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    private List<Label> labels = new ArrayList<>();

    private boolean deleted = false;

    private boolean closed = false;

    public Issue() {

    }

    public Issue(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public boolean isMatchWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public void update(User loginUser, Issue target) {
        if (!isMatchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.subject = target.subject;
        this.comment = target.comment;
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!isMatchWriter(loginUser)) {
            throw new CannotDeleteException("작성자만 삭제 가능합니다.");
        }
        this.deleted = true;

        List<DeleteHistory> temp = new ArrayList<>();
        temp.add(new DeleteHistory(ContentType.ISSUE, getId(), writer));
        return temp;
    }

    //TODO : 아래 4개 메소드 중복제거 가능할까?
    public void toMilestone(User loginUser, Milestone milestone) {
        if(!isMatchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.milestone = milestone;
    }

    public void toAssignee(User loginUser, User assignee) {
        if (!isMatchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.assignee = assignee;
    }

    public void addLabel(User loginUser, Label label) {
        if(!isMatchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.labels.add(label);
    }

    public void changeOpeningAndClosingStatus(User loginUser) {
        if(!isMatchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.closed = !closed;
    }

    public void addComment(User loginUser, Comment comment) {
        if(!isMatchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.comments.add(comment);
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

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
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

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + getId() + '\'' +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }
}