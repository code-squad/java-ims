package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Answer extends AbstractEntity {
    private static final Logger log = getLogger(Answer.class);

    @Size(min = 2, max = 500)
    @Column(nullable = false, length = 500)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_issue"))
    private Issue issue;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(long id, @Size(min = 2, max = 500) String comment, User writer, Issue issue, boolean deleted) {
        super(id);
        this.comment = comment;
        this.writer = writer;
        this.issue = issue;
        this.deleted = deleted;
    }

    public Answer(User loginUser, Issue issue, String newComment) {
        this.writer = loginUser;
        this.issue = issue;
        this.comment = newComment;
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

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void update(String update, User loginUser) {
        if (!this.writer.equals(loginUser)) {
            throw new UnAuthorizedException();
        }
        log.debug("update domain : {}", update);
        this.comment = update;
    }

    public DeleteHistory delete(User loginUser) {
        if (!this.writer.equals(loginUser)) {
            throw new CannotDeleteException("다른사람의 답글을 지울수 없습니다.");
        }
        this.deleted = true;
        return new DeleteHistory(ContentType.ANSWER, getId(), loginUser);
    }

    public static List<DeleteHistory> deleteAnswers(List<Answer> answers, User loginUser) {
        List<DeleteHistory> answersInfo = new ArrayList<>();
        for (Answer answer : answers) {
            answersInfo.add(answer.delete(loginUser));
        }
        return answersInfo;
    }
}
