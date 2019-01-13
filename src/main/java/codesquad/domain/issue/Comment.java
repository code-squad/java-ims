package codesquad.domain.issue;

import codesquad.UnAuthorizedException;
import codesquad.domain.history.ContentType;
import codesquad.domain.history.DeleteHistory;
import codesquad.domain.user.User;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Comment extends AbstractEntity {

    @Lob
    @Size(min = 4, message = "{validation.size.min}")
    private String contents;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    private boolean deleted = false;

    public Comment() {
    }

    public Comment(String contents) {
        this.contents = contents;
    }

    public Comment(long id, String contents, Issue issue, User writer) {
        super(id);
        this.contents = contents;
        this.issue = issue;
        this.writer = writer;
    }

    public DeleteHistory delete(User loginUser, Issue issue) {
        if (!writer.equals(loginUser)) {
            new UnAuthorizedException();
        }
        this.deleted = true;
        issue.deleteComment(this);
        return new DeleteHistory(ContentType.COMMENT, getId(), loginUser);
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Issue getIssue() {
        return issue;
    }

    public void toIssue(Issue issue) {
        this.issue = issue;
    }

    public User getWriter() {
        return writer;
    }

    public boolean isOwner(User target) {
        return this.writer.equals(target);
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

    @Override
    public String toString() {
        return "Comment[contents=" + contents + ", issue=" + issue + ", writer=" + writer + "]";
    }

    public Comment update(User loginUser, Issue issue, Comment updateComment) {
        if(!writer.equals(loginUser)) throw new UnAuthorizedException();
        this.contents = updateComment.contents;
        issue.updateComment(this);
        return this;
    }
}
