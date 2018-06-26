package codesquad.domain;

import codesquad.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonProperty;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Comment extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    @JsonProperty
    private User writer;

    @Size(min = 3)
    @JsonProperty
    private String content;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    @JsonProperty
    private Issue issue;

    private boolean deleted;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        if (!super.equals(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(writer, comment.writer) &&
                Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), writer, content);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "writer=" + writer +
                ", content='" + content + '\'' +
                '}';
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void update(User loginUser, Comment updated) {
        if (!loginUser.equals(writer)) {
            throw new UnAuthorizedException();
        }
        this.content = updated.content;
    }

    public void delete(User loginUser) {
        if (!loginUser.equals(writer)) {
            throw new UnAuthorizedException();
        }
        deleted = true;
    }
}
