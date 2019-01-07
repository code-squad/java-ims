package codesquad.domain;

import codesquad.UnAuthorizedException;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Issue extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @Embedded
    private IssueBody issueBody;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_milestone"))
    private Milestone milestone;

    private boolean deleted = false;

    private boolean closed = false;

    public Issue() {
    }

    public Issue(User writer, IssueBody issueBody) {
        this.writer = writer;
        this.issueBody = issueBody;
    }

    public static Issue ofBody(User loginUser, IssueBody issueBody) {
        return new Issue(loginUser, issueBody);
    }

    public Issue update(User loginUser, IssueBody issueBody) {
        if (!writer.matchUser(loginUser)) {
            throw new UnAuthorizedException("작성자가 아닙니다.");
        }
        this.issueBody.update(issueBody);
        return this;
    }

    public void toClose() {
        this.closed = true;
    }

    public void toMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public String getUserId() {
        return writer.getName();
    }

    public void setUserId(User writer) {
        this.writer = writer;
    }

    public String getSubject() {
        return issueBody.getSubject();
    }

    public void setSubject(String subject) {
        this.issueBody.setSubject(subject);
    }

    public String getComment() {
        return this.issueBody.getComment();
    }

    public void setComment(String comment) {
        this.issueBody.setComment(comment);
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public DeleteHistory deleted(User loginUser) {
        if (!writer.matchUser(loginUser)) {
            throw new UnAuthorizedException("작성자가 아닙니다.");
        }
        deleted = true;
        return new DeleteHistory(getId(), loginUser);
    }


    public boolean isDeleted() {
        return deleted;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "writer=" + writer +
                ", sbject=" + getSubject() +
                ", comment" + getComment() +
                ", deleted=" + deleted +
                '}';
    }
}