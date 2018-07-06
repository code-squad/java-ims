package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue extends AbstractEntity {
    private static final Logger log = LoggerFactory.getLogger(Issue.class);

    @Size(min = 3)
    @Column(nullable = false, length = 30)
    private String title;

    @Size(min = 3)
    @Column(nullable = false, length = 30)
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_asignee"))
    private User assignee;

    @Enumerated(value = EnumType.STRING)
    private Label label;

    @Embedded
    private Comments comments;

    @Embedded
    private Attachments  attachments;

    private boolean deleted = false;

    private boolean closed = false;

    public Issue() {
    }

    public Issue(String title, String contents) {
        super(0L);
        this.title = title;
        this.contents = contents;
    }

    public void update(User loginUser, Issue target) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.title = target.title;
        this.contents = target.contents;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public void writeBy(User writer) {
        this.writer = writer;
    }

    public void setAssignee(User assignee, User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.assignee = assignee;
    }

    public void setMilestone(User loginUser, Milestone milestone) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.milestone = milestone;
    }

    public void setLabel(User loginUser, Label label) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.label = label;
    }

    public void setClosed(User loginUser, boolean closed) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.closed = closed;
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        List<DeleteHistory> deleteHistories = comments.delete(loginUser);
        deleteHistories.add(new DeleteHistory(getId(), ContentType.ISSUE, loginUser));
        this.deleted = true;
        log.debug("Delete Issue : {}", deleteHistories.size());
        return deleteHistories;
    }

    public User getAssignee() {
        return assignee;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public User getWriter() {
        return writer;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public Label getLabel() {
        return label;
    }

    public List<Attachment> getAttachments() {
        return attachments.getAttachments();
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public IssueDto toIssueDto() {
        return new IssueDto(title, contents);
    }

    @JsonIgnore
    public List<Comment> getComments() {
        return comments.getComments();
    }

    @Override
    public String toString() {
        return "Issue{" +
                super.toString() +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writer=" + writer +
                ", assignee=" + assignee +
                ", milestone=" + milestone +
                '}';
    }
}
