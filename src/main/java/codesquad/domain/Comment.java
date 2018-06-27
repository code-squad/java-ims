package codesquad.domain;

import support.domain.AbstractEntity;
import support.domain.UriGeneratable;

import javax.persistence.*;

@Entity
public class Comment extends AbstractEntity implements UriGeneratable {

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_user"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_issue"))
    private Issue issue;

    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
    }

    public Comment(String comment, User writer, Issue issue) {
        this.comment = comment;
        this.writer = writer;
        this.issue = issue;
    }

    public Comment writeBy(User loginUser) {
        if (writer == null) {
            writer = loginUser;
        }
        return this;
    }

    public Comment toIssue(Issue issue) {
        if (this.issue == null) {
            this.issue = issue;
        }
        return this;
    }

    public long getId() {
        return super.getId();
    }

    public String getComment() {
        return comment;
    }

    public User getWriter() {
        return writer;
    }

    public Issue getIssue() {
        return issue;
    }

    @Override
    public String generateUri() {
        return String.format("/api/issues/%d/comments/%d", issue.getId(), getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", writer=" + writer.getName() +
                ", issue=" + issue.getSubject() +
                '}';
    }
}
