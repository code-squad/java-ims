package codesquad.domain.issue;

import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Comment extends AbstractEntity {

    @Lob
    @Size(min = 4)
    private String contents;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    private boolean isDeleted = false;

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

    // todo issue 매개변수가 필요없을까요?
    public Comment delete(User loginUser, Issue issue) {
        if (!writer.equals(loginUser)) {
            new UnAuthorizedException();
        }
        this.issue = null;
        return this;
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

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Comment[contents=" + contents + ", issue=" + issue + ", writer=" + writer + "]";
    }
}
