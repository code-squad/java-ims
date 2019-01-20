package codesquad.domain.issue;

import codesquad.UnAuthorizedException;
import codesquad.domain.DeleteHistory;
import codesquad.domain.issue.answer.Answers;
import codesquad.domain.label.Label;
import codesquad.domain.milestone.Milestone;
import codesquad.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity {
    private static final Logger logger = getLogger(Issue.class);

    @Embedded
    private Contents contents = new Contents();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone"))
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_label"))
    private Label label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_assignee"))
    private User assignee;

    @Embedded
    @JsonIgnore
    private Answers answers = new Answers();

    private Boolean deleted = false;

    public Issue() {
    }

    public Issue(Contents contents) {
        this.contents = contents;
    }

    public Issue(long id, Contents contents, User writer) {
        super(id);
        this.contents = contents;
        this.writer = writer;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public void writenBy(User loginUser) {
        this.writer = loginUser;
    }

    public Issue update(User loginUser, Contents updateIssueContents) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.contents = updateIssueContents;
        return this;
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException("로그인 유저가 이슈 작성자와 달라 삭제할 수 없습니다");
        }

        long otherAnswers = answers.otherAnswerCount(writer);
        if (otherAnswers > 0) {
            throw new UnAuthorizedException("다른 댓글 작성자가 있어 삭제가 불가능 합니다.");
        }

        List<DeleteHistory> histories = answers.delete(loginUser);

        this.deleted = true;

        histories.add(new DeleteHistory(ContentType.ISSUE, getId(), writer, LocalDateTime.now()));
        return histories;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isSameComment(Issue issue) {
        return this.contents == issue.getContents();
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }
}
