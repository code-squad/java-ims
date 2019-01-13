package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import org.hibernate.annotations.Where;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue extends AbstractEntity {

    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    private String subject;

    @Size(min = 3, max = 500)
    @Column(nullable = false, length = 500)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
    private Label label;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    @OneToMany
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(long id, String subject, String comment) {
        super(id);
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(String subject, String comment, User writer, boolean deleted) {
        this(0L, subject, comment, writer, deleted);
    }

    public Issue(long id, String subject, String content, User writer, boolean deleted) {
        super(id);
        this.subject = subject;
        this.comment = content;
        this.writer = writer;
        this.deleted = deleted;
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

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<Answer> getComments() {
        return answers;
    }

    public void setComments(List<Answer> comments) {
        this.answers = comments;
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

    public void update(User loginUser, Issue toIssue) {
        if (!matchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        if (!matchPassord(loginUser.getPassword())) {
            throw new UnAuthorizedException();
        }
        this.subject = toIssue.subject;
        this.comment = toIssue.comment;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    private boolean matchPassord(String password) {
        return this.writer.getPassword().equals(password);
    }

    private boolean matchWriter(User loginUser) {
        return this.writer.getUserId().equals(loginUser.getUserId());
    }

//    ApiIssueController에서 사용할 것이지만 일단은 만들어 보았다.
    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer);
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!matchWriter(loginUser)) {
            throw new CannotDeleteException("작성자가 아니면 지울수 없습니다.");
        }
        this.deleted = true;
        List<DeleteHistory> deletes = Answer.deleteAnswers(this.answers, loginUser);
        deletes.add(new DeleteHistory(ContentType.ISSUE, getId(), writer));
        return deletes;
    }


    public void addComment(Answer newComment) {
        answers.add(newComment);
    }

    public boolean equalsQuestion(Issue issue) {
        return this.subject.equals(issue.subject) && this.comment.equals(issue.comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Issue issue = (Issue) o;

        if (deleted != issue.deleted) return false;
        if (subject != null ? !subject.equals(issue.subject) : issue.subject != null) return false;
        if (comment != null ? !comment.equals(issue.comment) : issue.comment != null) return false;
        return writer != null ? writer.equals(issue.writer) : issue.writer == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (writer != null ? writer.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }
}
