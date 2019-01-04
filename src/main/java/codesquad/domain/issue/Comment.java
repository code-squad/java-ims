package codesquad.domain.issue;

import codesquad.domain.User;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Comment extends AbstractEntity {

    @Lob
    @Size(min = 4)
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

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

    @Override
    public String toString() {
        return "Comment[contents=" + contents + ", issue=" + issue + ", writer=" + writer + "]";
    }
}
