package codesquad.domain;

import codesquad.exception.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity {

    private static final Logger log = getLogger(Issue.class);

    @Embedded
    private IssueBody issueBody;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    @ManyToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_labels"))
    private List<Label> lables;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE)
    @OrderBy("id ASC")
    private List<Attachment> attachments;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public Issue(User loginUser, IssueBody issueBody) {
        this.writer = loginUser;
        this.issueBody = issueBody;

    }

    public IssueBody getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(IssueBody issueBody) {
        this.issueBody = issueBody;
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<Label> getLables() {
        return lables;
    }

    public void setLables(List<Label> lables) {
        this.lables = lables;
    }

    public List<Attachment> getAttachments () {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public void update(User loginUser, IssueBody target) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.issueBody = target;
    }

    public void delete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.deleted = true;
    }

    public void addMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void addAssignee(User assignee) {
        this.assignee = assignee;
    }

    public void addLable(Label lable) {
        this.lables.add(lable);
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }
}
