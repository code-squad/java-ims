package codesquad.domain;

import codesquad.dto.AnswerDto;
import codesquad.dto.MilestoneDto;
import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnAuthorizedException;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

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

    @ManyToMany
    private Set<Label> labels = new HashSet<>();

    @ManyToMany
    private Set<User> assignees = new HashSet<>();


    @Embedded
    private Answers answers = new Answers();

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

    public Issue toMilestone(User loginUser, Milestone milestone) {
        isOwner(loginUser);
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

    public Set<Label> addLabel(User loginUser, Label label) {
        isOwner(loginUser);
        if (labels.contains(label)) {
            labels.remove(label);
            return labels;
        }
        labels.add(label);
        return labels;
    }

    public Set<User> addAssignee(User loginUser, User assignee) {
        isOwner(loginUser);
        if (assignees.contains(assignee)) {
            assignees.remove(assignee);
            return assignees;
        }
        assignees.add(assignee);
        return assignees;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean getClosed() {
        return closed;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public Set<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<User> assignees) {
        this.assignees = assignees;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
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

    public List<Answer> addAnswer(Answer answer) {
        answers.getAnswers().add(answer);
        return answers.getAnswers();
    }
}
