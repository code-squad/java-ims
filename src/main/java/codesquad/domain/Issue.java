package codesquad.domain;

import codesquad.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @Embedded
    private ContentsBody contentsBody;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_milestone"))
    private Milestone milestone;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_label"))
    private List<Label> labels = new ArrayList<>();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_assignee"))
    private User assignee;

    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    private boolean deleted = false;

    private boolean closed = false;

    public Issue() {
    }

    public Issue(User writer, ContentsBody contentsBody) {
        this.writer = writer;
        this.contentsBody = contentsBody;
    }

    public static Issue ofBody(User loginUser, ContentsBody contentsBody) {
        return new Issue(loginUser, contentsBody);
    }

    public Issue update(User loginUser, ContentsBody contentsBody) {
        if (!writer.matchUser(loginUser)) {
            throw new UnAuthorizedException("작성자가 아닙니다.");
        }
        this.contentsBody.update(contentsBody);
        return this;
    }

    public void toClose() {
        this.closed = true;
    }

    public void toMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void toLabel(Label label) {
        if (this.labels.contains(label)) {
            this.labels.remove(label);
            return;
        }
        this.labels.add(label);
    }

    public void toAssignee(User user) {
        this.assignee = user;
    }

    public Answer addAnswer(Answer answer) {
        answer.toIssue(this);
        answers.add(answer);
        return answer;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getUserId() {
        return writer.getName();
    }

    public void setUserId(User writer) {
        this.writer = writer;
    }

    public String getSubject() {
        return contentsBody.getSubject();
    }

    public void setSubject(String subject) {
        this.contentsBody.setSubject(subject);
    }

    public String getComment() {
        return this.contentsBody.getComment();
    }

    public void setComment(String comment) {
        this.contentsBody.setComment(comment);
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public List getLabel() {

        return labels;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public DeleteHistory deleted(User loginUser) {
        if (!writer.matchUser(loginUser)) {
            throw new UnAuthorizedException("작성자가 아닙니다.");
        }
        deleted = true;
        return new DeleteHistory(ContentType.ISSUE, getId(), loginUser);
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

    public List<Answer> getAnswers() {
        return answers;
    }
}