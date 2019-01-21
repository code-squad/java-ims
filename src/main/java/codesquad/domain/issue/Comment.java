package codesquad.domain.issue;

import codesquad.CannotDeleteException;
import codesquad.domain.ContentType;
import codesquad.domain.DeleteHistory;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment extends AbstractEntity {
    private static final Logger log = LoggerFactory.getLogger(Comment.class);

    @Size(min = 1)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_issue"))
    private Issue issue;

    private boolean deleted = false;

    public Comment() {

    }

    public Comment(User loginUser, Issue issue, String comment) {
        this.writer = loginUser;
        this.issue = issue;
        this.comment = comment;
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!isMatchWriter(loginUser)) {
            throw new CannotDeleteException("작성자만 삭제 가능합니다.");
        }
        this.deleted = true;

        List<DeleteHistory> temp = new ArrayList<>();
        temp.add(new DeleteHistory(ContentType.COMMENT, getId(), writer));
        return temp;
    }

    public boolean isMatchWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + getId() +
                "comment='" + comment + '\'' +
                ", writer=" + writer +
                ", issue=" + issue +
                '}';
    }
}
