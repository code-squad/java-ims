package codesquad.domain;

import codesquad.dto.MilestoneDto;
import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnAuthorizedException;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity {
    private static final Logger log = getLogger(Issue.class);

    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @ManyToMany(mappedBy = "issue")
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    @ManyToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_labels"))
    private List<Label> labels = new ArrayList<>();

    private boolean deleted = false;

    private boolean closed = false;

    public Issue(){
    }

    public Issue(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer._toUserDto(), this.deleted);
    }

    public IssueDto getIssueDto() {
        return this._toIssueDto();
    }

    public Issue toMilestone(Milestone milestone) {
        this.milestone = milestone;
        return this;
    }

    public boolean isLogin(User loginUser) {
        if (Objects.isNull(loginUser)) {
            throw new UnAuthenticationException();
        }
        return true;
    }

    public boolean isOwner(User loginUser) {
        isLogin(loginUser);
        if (!writer.equals(loginUser)) {
            throw new UnAuthorizedException();
        }
        return true;
    }

    public Issue modify(IssueDto updateIssue, User loginUser) {
        isOwner(loginUser);
        subject = updateIssue.getSubject();
        comment = updateIssue.getComment();
        return this;
    }

    public Issue delete(User loginUSer) {
        isOwner(loginUSer);
        deleted = true;
        return this;
    }

    public Issue close(User loginUser) {
        isOwner(loginUser);
        closed = true;
        return this;
    }

    public Issue open(User loginUser) {
        isOwner(loginUser);
        closed = false;
        return this;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean getClosed() {
        return closed;
    }

    public List<Label> addLabel(Label label, User loginUser) {
        isOwner(loginUser);
        labels.add(label);
        return labels;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                ", id=" + getId() +
                '}';
    }
}
