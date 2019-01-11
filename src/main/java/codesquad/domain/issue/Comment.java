package codesquad.domain.issue;

import codesquad.domain.user.User;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Comment extends AbstractEntity {

    @Size(min = 3)
    @Lob
    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_writer"))
    private User writer;

    private boolean deleted = false;

    public Comment() {
    }

    public Comment(String contents, User writer) {
        this(0L, contents, writer);
    }

    public Comment(long id, String contents, User writer) {
        super(id);
        this.contents = contents;
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Comment comment = (Comment) o;
        return deleted == comment.deleted &&
                Objects.equals(contents, comment.contents) &&
                Objects.equals(writer, comment.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contents, writer, deleted);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "contents='" + contents + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }
}
