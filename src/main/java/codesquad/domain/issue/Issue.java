package codesquad.domain.issue;

import codesquad.UnAuthorizedException;
import codesquad.domain.ContentType;
import codesquad.domain.DeleteHistory;
import codesquad.domain.User;
import codesquad.domain.label.Label;
import codesquad.domain.milestone.Milestone;
import org.slf4j.Logger;
import support.domain.AbstractEntity;
import support.domain.UrlGeneratable;

import javax.persistence.*;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity implements UrlGeneratable {
    private static final Logger log = getLogger(Issue.class);

    @Embedded
    private IssueBody issueBody;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
    private Label label;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public Issue(long id, IssueBody issueBody, User writer) {
        super(id);
        this.issueBody = issueBody;
        this.writer = writer;
    }

    public Issue update(User loginUser, IssueBody updateIssueBody) {
        if(!isOwner(loginUser)) throw new UnAuthorizedException();
        this.issueBody = updateIssueBody;
        return this;
    }

    public DeleteHistory delete(User loginUser) {
        if(!isOwner(loginUser)) throw new UnAuthorizedException();
        deleted = true;
        return new DeleteHistory(ContentType.ISSUE, getId(), loginUser);
    }

    public boolean hasSameBody(IssueBody target) {
        return this.issueBody.equals(target);
    }

    public boolean hasSameMilestone(Milestone target) {
        return this.milestone.equals(target);
    }

    public boolean isAssignee(User target) {
        return this.assignee.equals(target);
    }

    public boolean hasSameLabel(Label target) {
        return this.label.equals(target);
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void writtenBy(User loginUser) {
        this.writer = loginUser;
    }

    public boolean isOwner(User target) {
        return writer.equals(target);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public IssueBody getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public Issue setMilestone(User loginUser, Milestone milestone) {
        if(!isOwner(loginUser)) throw new UnAuthorizedException();
        this.milestone = milestone;
        return this;
    }

    public User getAssignee() {
        return assignee;
    }

    public Issue setAssignee(User loginUser, User assignee) {
        if(!isOwner(loginUser)) throw new UnAuthorizedException();
        this.assignee = assignee;
        return this;
    }

    public Label getLabel() {
        return label;
    }

    public Issue setLabel(User loginUser, Label label) {
        if(!isOwner(loginUser)) throw new UnAuthorizedException();
        this.label = label;
        return this;
    }

    @Override
    public String generateUrl() {
        return String.format("/issues/%d", getId());
    }

    @Override
    public String toString() {
        return "Issue [id=" + getId() + ", issueBody=" + issueBody + ", writer=" + writer + ']';
    }
}
